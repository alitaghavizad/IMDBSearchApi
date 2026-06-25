package com.taghavi.imdbsearchapi.da.repository;

import com.taghavi.imdbsearchapi.da.model.MovieCrew;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCrewRepository extends JpaRepository<MovieCrew, String> {
}
