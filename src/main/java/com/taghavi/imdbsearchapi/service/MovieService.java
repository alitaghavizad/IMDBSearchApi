package com.taghavi.imdbsearchapi.service;

import com.taghavi.imdbsearchapi.da.model.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getAllMovies();

    List<Movie> getAllMoviesByGenre(String genre);

    List<String> getMoviesByIds(List<String> ids);
}
