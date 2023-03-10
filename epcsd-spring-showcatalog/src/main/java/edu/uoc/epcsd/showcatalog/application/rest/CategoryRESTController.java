package edu.uoc.epcsd.showcatalog.application.rest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import edu.uoc.epcsd.showcatalog.application.request.CreateCategoryRequest;
import edu.uoc.epcsd.showcatalog.application.request.UpdateCategoryRequest;
import edu.uoc.epcsd.showcatalog.domain.Category;
import edu.uoc.epcsd.showcatalog.domain.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@CrossOrigin(origins = {"${app.front.client}"})
@RequestMapping("/api")
public class CategoryRESTController {
    private final CategoryService categoryService;

    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    public List<Category> findCategories() {
        log.trace("findCategories");

        return categoryService.findAllCategories();
    }

    @GetMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Category> findCategoryById(@PathVariable Long id) {
        log.trace("findCategoryById");

        return categoryService.findCategoryById(id).map(category -> ResponseEntity.ok().body(category)).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/categories/name")
    public ResponseEntity<List<Category>> findCategoryLikeName(@RequestParam(value = "name", required = true) String name) {
        log.trace("findCategoryLikeName");

        return ResponseEntity.ok().body(categoryService.findCategoryLikeName(name));
    }

    @PostMapping("/categories")
    public ResponseEntity<Long> createCategory(@RequestBody CreateCategoryRequest createCategoryRequest) {
        log.trace("createCategory");

        log.trace("Creating category " + createCategoryRequest);
        Long categoryId = categoryService.createCategory(createCategoryRequest.getCategory());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoryId).toUri();

        return ResponseEntity.created(uri).body(categoryId);
    }

    @PutMapping("/categories")
    public ResponseEntity<Boolean> updateCategory(@RequestBody UpdateCategoryRequest updateCategoryRequest) {
        log.trace("updateCategory");

		final Category category = Category.builder().id(updateCategoryRequest.getId()).name(updateCategoryRequest.getName()).description(updateCategoryRequest.getDescription()).build();
        boolean success = categoryService.updateCategory(category);

        return new ResponseEntity<>(success, HttpStatus.OK);
    }
    
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Long id) {
        log.trace("deleteCategory");

        categoryService.deleteCategory(id);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
