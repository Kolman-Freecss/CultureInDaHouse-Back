package edu.uoc.epcsd.showcatalog.domain.service;

import edu.uoc.epcsd.showcatalog.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ShowService {
    List<Show> findAllShows();

    Optional<Show> findShowById(Long id);

    List<Show> findShowsByName(String name);

    List<Show> findShowsByCategory(Long id);

    List<Show> findShows(String name, Long categoryId, LocalDate date, Status status);

    Long createShow(Show show);
    
    boolean updateShow(Show show);

    Set<Performance> getShowPerformances(Long id);

    void cancelShow(Long id);

    boolean updateStateShow(Long id, Status status);

    // performances
    void createShowPerformance(Long id, Performance performance);

    List<Show> findShowLikeCategory(String name);

    List<Show> findShowLikeName(String name);

    Optional<Category> findCategoryByShowId(Long showId);

    boolean addComment(Long showId, ShowComment comment);

    Set<ShowComment> getShowComments(Long id);
    Status getShowStatus(Long id);
    LocalDate getShowStartDate(Long id);
    LocalDate getShowFinalDate(Long id);
}
