package edu.uoc.epcsd.showcatalog.domain.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import edu.uoc.epcsd.showcatalog.domain.Category;
import edu.uoc.epcsd.showcatalog.domain.Performance;
import edu.uoc.epcsd.showcatalog.domain.Show;
import edu.uoc.epcsd.showcatalog.domain.ShowComment;
import edu.uoc.epcsd.showcatalog.domain.Status;
import edu.uoc.epcsd.showcatalog.domain.repository.CategoryRepository;
import edu.uoc.epcsd.showcatalog.domain.repository.ShowRepository;
import edu.uoc.epcsd.showcatalog.infrastructure.kafka.KafkaConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class ShowServiceImpl implements ShowService {
    private final ShowRepository showRepository;
    private final CategoryRepository categoryRepository;

    private final KafkaTemplate<String, Show> kafkaTemplate;

    @Override
    public List<Show> findAllShows() {
        return showRepository.findAllShows();
    }

    @Override
    public Optional<Show> findShowById(Long id) {
        return showRepository.findShowById(id);
    }

    @Override
    public List<Show> findShowsByName(String name) {
        return showRepository.findShowsByName(name);
    }

    @Override
    public List<Show> findShowsByCategory(Long id) {
        return showRepository.findShowsByCategory(id);
    }

    @Override
    public List<Show> findShows(String name, Long categoryId, LocalDate date, Status status) {
        return showRepository.findShows(name, categoryId, date, status);
    }

    @Override
    public Set<Performance> getShowPerformances(Long id) {
        Set<Performance> performance = null;
        final Optional<Show> show = showRepository.findShowById(id);
        if(show.isPresent()) {
            performance = show.get().getPerformances();
        }
        return performance;
    }

    @Override
    public Long createShow(Show show) {
        Long id = null;
        final Category category = getCategoryById(show.getCategory());
        if(category != null) {
            show.setCategory(category);

            id = showRepository.createShow(show);

            final Optional<Show> actualShow = showRepository.findShowById(id);

            if(actualShow.isPresent()) {
                log.trace("Sending " + show + " to " + KafkaConstants.SHOW_TOPIC + KafkaConstants.SEPARATOR + KafkaConstants.COMMAND_ADD + " topic.");
                kafkaTemplate.send(KafkaConstants.SHOW_TOPIC + KafkaConstants.SEPARATOR + KafkaConstants.COMMAND_ADD, actualShow.get());
            } else {
                log.error("No se ha podido enviar el acto a la cola kafka ya que no existe el acto con id: " + id);
            }
        } else {
            log.error("No se puede crear el acto ya que no existe la categoria con id: " + show.getCategory().getId());
        }

        return id;
    }

    @Override
	public boolean updateShow(Show show) {
    	boolean success = false;
    	final Optional<Show> optionalShow = showRepository.findShowById(show.getId());
        if(optionalShow.isPresent()) {
        	final Category categoryBBDD = getCategoryById(show.getCategory());
        	
            final Show showBBDD = optionalShow.get();
        	showBBDD.setName(show.getName());
        	showBBDD.setDescription(show.getDescription());
        	showBBDD.setImage(show.getImage());
        	showBBDD.setPrice(show.getPrice());
        	showBBDD.setDuration(show.getDuration());
        	showBBDD.setCapacity(show.getCapacity());
        	showBBDD.setOnSaleDate(show.getOnSaleDate());
        	showBBDD.setCategory(categoryBBDD);
            
            showRepository.updateShow(showBBDD);
            success = true;
        } else {
            log.error("No se puede actualizar el acto ya que no existe el acto con id: " + show.getId());
        }
		return success;
	}
    
    @Override
    public void cancelShow(Long id) {
        final Optional<Show> optionalShow = showRepository.findShowById(id);
        if(optionalShow.isPresent()) {
            Show show = optionalShow.get();
            show.cancel();
            showRepository.updateShow(show);
        } else {
            log.error("No se puede cancelar el acto ya que no existe el acto con id: " + id);
        }
    }

    @Override
    public void createShowPerformance(Long id, Performance performance) {

        showRepository.addShowPerformance(id, performance);
    }
    
    @Override
    public boolean updateStateShow(Long id, Status status) {
        final Optional<Show> optionalShow = showRepository.findShowById(id);
        log.debug("stateShow : "+status);
        if(optionalShow.isPresent()) {
            Show show = optionalShow.get();
            log.debug("hasta dentro : "+show.getId()+" "+show.getDescription()+" - "+status );
            show.setStatus(status);
            showRepository.updateShow(show);
            return true;
        } else {
            log.error("No se puede cancelar el acto ya que no existe el acto con id: " + id);
            return false;
        }
    }

    @Override
    public List<Show> findShowLikeCategory(String name) {
        return showRepository.findShowLikeCategory(name);
    }

    @Override
    public List<Show> findShowLikeName(String name) {
        return showRepository.findShowLikeName(name);
    }

    @Override
    public Optional<Category> findCategoryByShowId(Long showId) {
        Optional<Show> show = showRepository.findShowById(showId);

        if (show.isEmpty()) {
            log.error("No se puede coger la categoría de un acto que no existe con ID: " + showId);
            return Optional.empty();
        }

        return Optional.of(show.get().getCategory());
    }

    @Override
    public boolean addComment(Long showId, ShowComment comment) {
    	boolean success = false;
    	final Optional<Show> optionalShow = showRepository.findShowById(showId);
        if(optionalShow.isPresent()) {
        	showRepository.addShowComment(showId, comment);
            success = true;
        } else {
            log.error("No se puede añadir el comentando al acto ya que no existe el acto con id: " + showId);
        }
		return success;
        
    }

    @Override
    public Set<ShowComment> getShowComments(Long id) {
        Set<ShowComment> showComments = null;
        final Optional<Show> show = showRepository.findShowById(id);
        if(show.isPresent()) {
            showComments = show.get().getComments();
        }
        return showComments;
    }

    @Override
    public Status getShowStatus(Long id) {
        Status status = null;
        final Optional<Show> show = showRepository.findShowById(id);
        if(show.isPresent()) {
            status = show.get().getStatus();
        }
        return status;
    }

    @Override
    public LocalDate getShowStartDate(Long id) {
        LocalDate date = null;
        final Optional<Show> show = showRepository.findShowById(id);
        if (show.isPresent()) {
            Set<Performance> performances = show.get().getPerformances();
            if (!performances.isEmpty()) {
                for (Performance performance: performances) {
                    if (date == null || performance.getDate().isBefore(date)) {
                        date = performance.getDate();
                    }
                }
            }
        }

        return date;
    }

    @Override
    public LocalDate getShowFinalDate(Long id) {
        LocalDate date = null;
        final Optional<Show> show = showRepository.findShowById(id);
        if (show.isPresent()) {
            Set<Performance> performances = show.get().getPerformances();
            if (!performances.isEmpty()) {
                for (Performance performance: performances) {
                    if (date == null || performance.getDate().isAfter(date)) {
                        date = performance.getDate();
                    }
                }
            }
        }

        return date;
    }

    /**
     * Obtiene la categoria por el identificador.
     * 
     * @param category Categoría.
     * @return Categoría.
     */
    private Category getCategoryById(final Category category) {
    	Category categoryBBDD = null;
    	if(category != null && category.getId() != null) {
    		final Optional<Category> optionalCategory = categoryRepository.findCategoryById(category.getId());
            if(optionalCategory.isPresent()) {
            	categoryBBDD = optionalCategory.get();
            }
    	}
    	return categoryBBDD;
    }
}
