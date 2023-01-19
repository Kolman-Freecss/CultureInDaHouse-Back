package edu.uoc.epcsd.showcatalog.application.rest;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.uoc.epcsd.showcatalog.application.request.CreatePerformanceRequest;
import edu.uoc.epcsd.showcatalog.application.request.CreateShowCommentRequest;
import edu.uoc.epcsd.showcatalog.application.request.CreateShowRequest;
import edu.uoc.epcsd.showcatalog.application.request.UpdateShowRequest;
import edu.uoc.epcsd.showcatalog.application.request.UpdateShowStatusRequest;
import edu.uoc.epcsd.showcatalog.domain.Category;
import edu.uoc.epcsd.showcatalog.domain.Performance;
import edu.uoc.epcsd.showcatalog.domain.Show;
import edu.uoc.epcsd.showcatalog.domain.ShowComment;
import edu.uoc.epcsd.showcatalog.domain.Status;
import edu.uoc.epcsd.showcatalog.domain.service.ShowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@CrossOrigin(origins = {"${app.front.client}"})
@RequestMapping("/api")
public class ShowRESTController {
	
	private static final String LITERAL_QUEDAN = "Quedan ";
	
    private final ShowService showService;

    @GetMapping(value = "/shows")
    public ResponseEntity<List<Show>> findShows(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "categoryId", required = false) Long categoryId,
                                                @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date,
                                                @RequestParam(value = "status", required = false) Status status) {
        log.trace("findShows");

        return ResponseEntity.ok().body(showService.findShows(name, categoryId, date, status));
    }

    @GetMapping("/shows/{id}")
    public ResponseEntity<Show> getShowDetails(@PathVariable Long id) {
        log.trace("getShowDetails");

        return showService.findShowById(id).map(show -> ResponseEntity.ok().body(show)).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/shows/{id}/performances")
    public ResponseEntity<Set<Performance>> getShowPerformances(@PathVariable Long id) {
        log.trace("getShowPerformances");

        return ResponseEntity.ok().body(showService.getShowPerformances(id));
    }

    @PostMapping("/shows")
    public ResponseEntity<Long> createShow(@RequestBody CreateShowRequest createShowRequest) {
        log.trace("createShow");

        Long id = showService.createShow(createShowRequest.getShow());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).body(id);
    }

    @PutMapping("/shows")
    public ResponseEntity<Boolean> updateShow(@RequestBody UpdateShowRequest updateShowRequest) {
        log.trace("updateShow");

		final Show show = Show.builder().id(updateShowRequest.getId()).name(updateShowRequest.getName()).description(updateShowRequest.getDescription()).image(updateShowRequest.getImage())
				.price(updateShowRequest.getPrice()).duration(updateShowRequest.getDuration()).capacity(updateShowRequest.getCapacity()).onSaleDate(updateShowRequest.getOnSaleDate())
				.category(Category.builder().id(updateShowRequest.getCategoryId()).build()).build();
        boolean success = showService.updateShow(show);

        return new ResponseEntity<>(success, HttpStatus.OK);
    }
    
    @PutMapping("/shows/{id}")
    public ResponseEntity<Boolean> createPerformance(@PathVariable Long id, @RequestBody CreatePerformanceRequest createPerformanceRequest) {
        log.trace("createPerformance");

        showService.createShowPerformance(id, createPerformanceRequest.getPerformance());

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @DeleteMapping("/shows/{id}")
    public ResponseEntity<Boolean> cancelShow(@PathVariable Long id) {

        showService.cancelShow(id);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("/shows/status/{id}")
    public ResponseEntity<Boolean> updateShowStatus(@PathVariable Long id, @RequestBody UpdateShowStatusRequest updateShowStatusRequest) {
        log.trace("updateShowStatus");

        boolean result = showService.updateStateShow(id, updateShowStatusRequest.getStatus());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/shows/category")
    public ResponseEntity<List<Show>> findShowLikeCategory(@RequestParam(value = "name", required = true) String name) {
        log.trace("findShowLikeCategory");

        return ResponseEntity.ok().body(showService.findShowLikeCategory(name));
    }

    @GetMapping("/shows/name")
    public ResponseEntity<List<Show>> findShowLikeName(@RequestParam(value = "name", required = true) String name) {
        log.trace("findShowLikeName");

        return ResponseEntity.ok().body(showService.findShowLikeName(name));
    }

    @GetMapping("/shows/{id}/category")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Category> getCategoryFromShow(@PathVariable Long id) {
        log.trace("findCategoryByShowId");

        return showService.findCategoryByShowId(id).map(category -> ResponseEntity.ok().body(category)).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/shows/{id}/comment")
    public ResponseEntity<Boolean> createShowComment(@PathVariable Long id, @RequestBody CreateShowCommentRequest createShowCommentRequest) {
        log.trace("createShowComment");

        int rating = createShowCommentRequest.getShowComment().getRating();

        if (rating > 5 || rating < 0) {
            return ResponseEntity.badRequest().body(false);
        }

        boolean success = showService.addComment(id, createShowCommentRequest.getShowComment());

        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @GetMapping("/shows/{id}/comments")
    public ResponseEntity<Set<ShowComment>> getShowComments(@PathVariable Long id) {
        log.trace("getShowComments");

        return ResponseEntity.ok().body(showService.getShowComments(id));
    }

    @GetMapping("/shows/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Status> getShowStatus(@PathVariable Long id) {
        log.trace("getShowStatus");

        return ResponseEntity.ok().body(showService.getShowStatus(id));
    }

    @GetMapping("/shows/{id}/startDate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LocalDate> getShowStartDate(@PathVariable Long id) {
        log.trace("getShowStartDate");

        return ResponseEntity.ok().body(showService.getShowStartDate(id));
    }

    @GetMapping("/shows/{id}/finalDate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LocalDate> getShowFinalDate(@PathVariable Long id) {
        log.trace("getShowStartDate");

        return ResponseEntity.ok().body(showService.getShowFinalDate(id));
    }
    
    @GetMapping("/shows/{id}/getTimeUtilReadyShow")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getTimeUtilReadyShow(@PathVariable Long id) {
        log.trace("getTimeUtilReadyShow");
        
        String response = "";
        LocalDate dateStart = showService.getShowStartDate(id);
        if(dateStart != null) {
        	Date dateActo = Date.from(dateStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date now = new Date();
			if (null != dateActo) {
				//Se valora si la fecha del acto ya ha pasado
				int result = now.compareTo(dateActo);
				if (result > 0) {
					log.trace("El acto ya ha sido realizado.");
					response = "El acto ya ha sido realizado.";
				} else {
					Instant instant1 = now.toInstant(); 
					Instant instant2 = dateActo.toInstant();
					
					Duration duration = Duration.between(instant1, instant2);
					log.trace("años: " + duration.toDays() / 365);
					log.trace("days: " + duration.toDays());
					log.trace("minutes: " + duration.toMinutes());
					
					if ((duration.toDays() / 365) > 0) {
						response = LITERAL_QUEDAN + (duration.toDays() / 365)+" años.";
					} else {
						if (duration.toHours() > 24) {
							response = LITERAL_QUEDAN + (duration.toDays())+" dias.";
						} else {
							response = LITERAL_QUEDAN
									+ (duration.toHours())+" horas y "
									+ (duration.toMinutes())+" minutos.";
						}
						
					}
				}
			} else {
				log.trace("No se puede recoger la fecha");
				response = "";
			}
			log.trace("Response : " + response);
        }
        
        return  ResponseEntity.ok().body(response);
    }
}
