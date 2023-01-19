package edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataCategoryRepository extends JpaRepository<CategoryEntity, Long> {
	List<CategoryEntity> findCategoryByNameContainingIgnoreCase(String name);
}
