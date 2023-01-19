package edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa;

import java.time.LocalDate;
import java.util.List;

import edu.uoc.epcsd.showcatalog.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataShowRepository extends JpaRepository<ShowEntity, Long> {
    List<ShowEntity> findShowByNameContainingIgnoreCase(String name);
    List<ShowEntity> findShowByCategoryNameContainingIgnoreCase(String name);
	 @Query("select \n"
	            + "		distinct show"
				+ "	from \n"
				+ "		ShowEntity show \n"
				+ "     	inner join show.category cate \n"
				+ "     	left join show.performances perf \n"
				+ "	where \n"
	            + " 	(:name is null or lower(show.name) like '%'||lower(cast(:name as text))||'%') \n"
	            + "		and (:categoryId is null or cate.id = :categoryId) \n"
	            + "		and (cast(:date as date) is null or perf.date = :date) \n"
	 			+ "		and (:status is null or show.status = :status)")
	List<ShowEntity> findShows(@Param("name") String name, @Param("categoryId") Long categoryId, @Param("date") LocalDate date, @Param("status") Status status);
}