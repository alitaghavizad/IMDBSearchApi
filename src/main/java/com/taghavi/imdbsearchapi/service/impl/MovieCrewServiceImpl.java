package com.taghavi.imdbsearchapi.service.impl;

import com.taghavi.imdbsearchapi.da.model.Actor;
import com.taghavi.imdbsearchapi.da.model.MovieCrew;
import com.taghavi.imdbsearchapi.da.repository.MovieCrewRepository;
import com.taghavi.imdbsearchapi.service.ActorService;
import com.taghavi.imdbsearchapi.service.MovieCrewService;
import com.taghavi.imdbsearchapi.utility.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieCrewServiceImpl implements MovieCrewService {
    private final MovieCrewRepository repository;
    private final ActorService actorService;

    @Override
    public List<MovieCrew> getAllMovieCrews() {
        try {
            Logger.log("Getting all movie crews");
            return repository.findAll();
        } catch (Exception e) {
            Logger.log("Error getting all movie crews: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<MovieCrew> getAllBySameWriterDirector() {
        try {
            Logger.log("Getting all movies with the same writer and director");
            return repository.getSimilarDirectorAndWriter();
        } catch (Exception e) {
            Logger.log("Error getting all movies with the same writer and director: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private boolean hasCommonDirectorAndWriter(MovieCrew crew) {
        if (crew.getDirectors() == null || crew.getWriters() == null) {
            return false;
        }

        Set<String> directors = Arrays.stream(crew.getDirectors().split(","))
                .map(String::trim)
                .collect(Collectors.toSet());

        Set<String> writers = Arrays.stream(crew.getWriters().split(","))
                .map(String::trim)
                .collect(Collectors.toSet());

        directors.retainAll(writers);
        return !directors.isEmpty();
    }
}
