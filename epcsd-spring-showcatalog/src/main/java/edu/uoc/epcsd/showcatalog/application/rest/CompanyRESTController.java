package edu.uoc.epcsd.showcatalog.application.rest;

import edu.uoc.epcsd.showcatalog.application.request.CreateCompanyRequest;
import edu.uoc.epcsd.showcatalog.domain.Company;
import edu.uoc.epcsd.showcatalog.domain.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@CrossOrigin(origins = {"${app.front.client}"})
@RequestMapping("/api")
public class CompanyRESTController {
    private final CompanyService companyService;


    @GetMapping(value = "/companies/name")
    public ResponseEntity<List<Company>> findCompanyLikeName(@RequestParam(value = "name", required = true) String name) {
        log.trace("findCompanyLikeName");

        return ResponseEntity.ok().body(companyService.findCompanyLikeName(name));
    }

    @PostMapping("/companies")
    public ResponseEntity<Long> createCompany(@RequestBody CreateCompanyRequest createCompanyRequest) {
        log.trace("createCompany");

        log.trace("Creating company " + createCompanyRequest);
        final Long companyId = companyService.createCompany(createCompanyRequest.getCompany());
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(companyId).toUri();

        return ResponseEntity.created(uri).body(companyId);
    }
}
