package edu.uoc.epcsd.showcatalog.domain.service;

import edu.uoc.epcsd.showcatalog.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    // categories
    List<Category> findAllCategories();

    Optional<Category> findCategoryById(Long id);

    List<Category> findCategoryLikeName(String name);

    Long createCategory(Category category);

    void deleteCategory(Long id);
}
