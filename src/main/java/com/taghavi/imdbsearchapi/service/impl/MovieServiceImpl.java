package com.taghavi.imdbsearchapi.service.impl;

import com.taghavi.imdbsearchapi.da.model.Movie;
import com.taghavi.imdbsearchapi.da.model.TitleRatings;
import com.taghavi.imdbsearchapi.da.repository.MovieRepository;
import com.taghavi.imdbsearchapi.service.MovieService;
import com.taghavi.imdbsearchapi.utility.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository repository;

    @Override
    public List<Movie> getAllMovies() {
        try {
            Logger.log("Getting all movies");
            return repository.findAll();
        } catch (Exception e) {
            Logger.log("Error getting all movies: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<Movie> getAllMoviesByGenre(String genre) {
        try {
            Logger.log("Getting all movies by genre: " + genre);
            return repository.findAllByGenre(genre);
        } catch (Exception e) {
            Logger.log("Error getting all movies by genre: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<String> getMoviesByIds(List<String> ids) {
        try {
            Logger.log("Getting movies by ids");
            List<Movie> result = new ArrayList<>();
            for (int i = 0; i < ids.size(); i += 100) {
                int end = Math.min(i + 100, ids.size());
                result.addAll(repository.findByIdIn(ids.subList(i, end)));
            }
            return result.stream().map(Movie::getPrimaryTitle).toList();
        } catch (Exception e) {
            Logger.log("Error getting movies by ids: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
