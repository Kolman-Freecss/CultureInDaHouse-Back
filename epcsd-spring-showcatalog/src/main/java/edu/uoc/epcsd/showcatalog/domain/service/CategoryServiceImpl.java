package edu.uoc.epcsd.showcatalog.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uoc.epcsd.showcatalog.domain.Category;
import edu.uoc.epcsd.showcatalog.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAllCategories();
    }

    @Override
    public Optional<Category> findCategoryById(Long id) {

        return categoryRepository.findCategoryById(id);

    }

    @Override
    public List<Category> findCategoryLikeName(String name) {
        return categoryRepository.findCategoryLikeName(name);
    }

    @Override
    public Long createCategory(Category category) {

        category.setId(null);
        return categoryRepository.createCategory(category);
    }

    @Override
	public boolean updateCategory(Category category) {
    	boolean success = false;
    	final Optional<Category> optionalCategory = categoryRepository.findCategoryById(category.getId());
        if(optionalCategory.isPresent()) {        	
            final Category categoryBBDD = optionalCategory.get();
            categoryBBDD.setName(category.getName());
            categoryBBDD.setDescription(category.getDescription());
            
            categoryRepository.updateCategory(categoryBBDD);
            success = true;
        } else {
            log.error("No se puede actualizar la categoria ya que no existe la categoria con id: " + category.getId());
        }
		return success;
	}
    
    @Override
    public void deleteCategory(Long id) {

        categoryRepository.deleteCategory(id);
    }
}
