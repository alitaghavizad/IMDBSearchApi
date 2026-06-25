package com.taghavi.imdbsearchapi.da.repository;

import com.taghavi.imdbsearchapi.da.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, String> {
    List<Movie> findByIdIn(List<String> ids);

    @Query(value = "select * from title_basics where genres like %:genre%", nativeQuery = true)
    List<Movie> findAllByGenre(String genre);
}
