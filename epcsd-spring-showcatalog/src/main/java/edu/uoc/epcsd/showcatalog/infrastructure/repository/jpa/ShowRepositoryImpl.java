package edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import edu.uoc.epcsd.showcatalog.domain.Performance;
import edu.uoc.epcsd.showcatalog.domain.Show;
import edu.uoc.epcsd.showcatalog.domain.ShowComment;
import edu.uoc.epcsd.showcatalog.domain.Status;
import edu.uoc.epcsd.showcatalog.domain.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShowRepositoryImpl implements ShowRepository {

	private final SpringDataShowRepository jpaRepository;

	private final SpringDataCategoryRepository categoryJpaRepository;

	@Override
	public List<Show> findAllShows() {
		return jpaRepository.findAll().stream().map(ShowEntity::toDomain).collect(Collectors.toList());
	}

	@Override
	public Optional<Show> findShowById(Long id) {
		return jpaRepository.findById(id).map(ShowEntity::toDomain);
	}

	@Override
	public List<Show> findShowsByCategory(Long id) {
		CategoryEntity categoryEntity = categoryJpaRepository.getById(id);
		return jpaRepository.findAll(Example.of(ShowEntity.builder().category(categoryEntity).build())).stream().map(ShowEntity::toDomain).collect(Collectors.toList());
	}

    @Override
    public List<Show> findShowLikeName(String name) {
        return jpaRepository.findShowByNameContainingIgnoreCase(name).stream().map(ShowEntity::toDomain).collect(Collectors.toList());
    }

	@Override
	public List<Show> findShowsByName(String name) {
		Show show = Show.builder().name(name).build();
		return jpaRepository.findAll(Example.of(ShowEntity.fromDomain(show))).stream().map(ShowEntity::toDomain).collect(Collectors.toList());
	}

	@Override
	public List<Show> findShows(String name, Long categoryId, LocalDate date, Status status) {
		return jpaRepository.findShows(name, categoryId, date, status).stream().map(ShowEntity::toDomain).collect(Collectors.toList());
	}

	@Override
	public Long createShow(Show show) {

		ShowEntity showEntity = ShowEntity.fromDomain(show);
		showEntity.setCategory(categoryJpaRepository.getById(show.getCategory().getId()));
		return jpaRepository.save(showEntity).getId();
	}

	@Override
	public void updateShow(Show show) {

		ShowEntity showEntity = ShowEntity.fromDomain(show);
		showEntity.setCategory(categoryJpaRepository.getById(show.getCategory().getId()));
		jpaRepository.save(showEntity);
	}

    @Override
    public void addShowPerformance(Long id, Performance performance) {
    	final Optional<ShowEntity> optionalShow = jpaRepository.findById(id);
    	if(optionalShow.isPresent()) {
    		ShowEntity showEntity = optionalShow.get();
            showEntity.getPerformances().add(PerformanceEntity.fromDomain(performance));
            jpaRepository.save(showEntity);
    	} else {
    		log.error("No se puede añadir la actuacion al acto ya que no existe el acto con id: " + id);
    	}
    }

    @Override
    public List<Show> findShowLikeCategory(String name) {
        return jpaRepository.findShowByCategoryNameContainingIgnoreCase(name).stream().map(ShowEntity::toDomain).collect(Collectors.toList());
    }

	@Override
	public void addShowComment(Long id, ShowComment comment) {
		final Optional<ShowEntity> optionalShow = jpaRepository.findById(id);
		if(optionalShow.isPresent()) {
			ShowEntity showEntity = optionalShow.get();
			showEntity.getComments().add(ShowCommentEntity.fromDomain(comment));
			jpaRepository.save(showEntity);
		} else {
			log.error("No se puede añadir el comentario ya que no existe el acto con id: " + id);
		}
	}
}
