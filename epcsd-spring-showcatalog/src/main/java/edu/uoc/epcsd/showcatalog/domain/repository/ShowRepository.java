package edu.uoc.epcsd.showcatalog.domain.repository;

import edu.uoc.epcsd.showcatalog.domain.Performance;
import edu.uoc.epcsd.showcatalog.domain.Show;
import edu.uoc.epcsd.showcatalog.domain.ShowComment;
import edu.uoc.epcsd.showcatalog.domain.Status;
import edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa.CategoryEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ShowRepository {

    List<Show> findAllShows();

    Optional<Show> findShowById(Long id);

    List<Show> findShowsByName(String name);

    List<Show> findShowsByCategory(Long id);

    List<Show> findShows(String name, Long categoryId, LocalDate date, Status status);

    Long createShow(Show show);

    void updateShow(Show show);

    void addShowPerformance(Long id, Performance performance);

    List<Show> findShowLikeName(String name);

    List<Show> findShowLikeCategory(String name);

    void addShowComment(Long id, ShowComment comment);
}
