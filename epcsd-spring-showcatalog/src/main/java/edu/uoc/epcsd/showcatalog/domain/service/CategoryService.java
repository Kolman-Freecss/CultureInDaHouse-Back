package edu.uoc.epcsd.showcatalog.domain.service;

import java.util.List;
import java.util.Optional;

import edu.uoc.epcsd.showcatalog.domain.Category;

public interface CategoryService {
    // categories
    List<Category> findAllCategories();

    Optional<Category> findCategoryById(Long id);

    List<Category> findCategoryLikeName(String name);

    Long createCategory(Category category);

    boolean updateCategory(Category category);
    
    void deleteCategory(Long id);
}
