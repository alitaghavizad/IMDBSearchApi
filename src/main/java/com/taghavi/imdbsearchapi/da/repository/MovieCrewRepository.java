package com.taghavi.imdbsearchapi.da.repository;

import com.taghavi.imdbsearchapi.da.model.MovieCrew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieCrewRepository extends JpaRepository<MovieCrew, String> {

    @Query(value = "SELECT * FROM title_crew WHERE writers LIKE '%' || directors || '%' OR directors LIKE '%' || writers || '%';",nativeQuery = true)
    List<MovieCrew> getSimilarDirectorAndWriter();
}
