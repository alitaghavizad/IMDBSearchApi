package com.taghavi.imdbsearchapi.service.impl;

import com.taghavi.imdbsearchapi.da.model.Movie;
import com.taghavi.imdbsearchapi.da.model.TitleRatings;
import com.taghavi.imdbsearchapi.da.repository.MovieRepository;
import com.taghavi.imdbsearchapi.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository repository;

    @Override
    public List<Movie> getAllMovies() {
        return repository.findAll();
    }

    @Override
    public List<Movie> getAllMoviesByGenre(String genre) {
        return repository.findAllByGenre(genre);
    }

    @Override
    public List<String> getMoviesByIds(List<String> ids) {
        List<Movie> result = new ArrayList<>();
        for (int i = 0; i < ids.size(); i += 100) {
            int end = Math.min(i + 100, ids.size());
            result.addAll(repository.findByIdIn(ids.subList(i, end)));
        }
        return result.stream().map(Movie::getPrimaryTitle).toList();
    }
}
