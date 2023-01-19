package edu.uoc.epcsd.showcatalog.domain.repository;

import java.util.List;
import java.util.Optional;

import edu.uoc.epcsd.showcatalog.domain.Category;

public interface CategoryRepository {

	List<Category> findAllCategories();

	Optional<Category> findCategoryById(Long id);

	List<Category> findCategoryLikeName(String name);

	Long createCategory(Category category);

	void deleteCategory(Long id);
}
