package edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.uoc.epcsd.showcatalog.domain.Category;
import edu.uoc.epcsd.showcatalog.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryRepositoryImpl implements CategoryRepository {

	private final SpringDataCategoryRepository jpaRepository;

	@Override
	public List<Category> findAllCategories() {
		return jpaRepository.findAll().stream().map(CategoryEntity::toDomain).collect(Collectors.toList());
	}

	@Override
	public Optional<Category> findCategoryById(Long id) {
		return jpaRepository.findById(id).map(CategoryEntity::toDomain);
	}

	@Override
	public List<Category> findCategoryLikeName(String name) {
		return jpaRepository.findCategoryByNameContainingIgnoreCase(name).stream().map(CategoryEntity::toDomain).collect(Collectors.toList());
	}

	@Override
	public Long createCategory(Category category) {
		return jpaRepository.save(CategoryEntity.fromDomain(category)).getId();
	}

	@Override
	public void deleteCategory(Long id) {
		jpaRepository.deleteById(id);
	}
}
