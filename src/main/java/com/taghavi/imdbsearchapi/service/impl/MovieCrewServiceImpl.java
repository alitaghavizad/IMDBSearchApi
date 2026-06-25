package com.taghavi.imdbsearchapi.service.impl;

import com.taghavi.imdbsearchapi.da.model.Actor;
import com.taghavi.imdbsearchapi.da.model.MovieCrew;
import com.taghavi.imdbsearchapi.da.repository.MovieCrewRepository;
import com.taghavi.imdbsearchapi.service.ActorService;
import com.taghavi.imdbsearchapi.service.MovieCrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
        return repository.findAll();
    }

    @Override
    public List<MovieCrew> getAllBySameWriterDirector() {
        List<MovieCrew> crews = repository.findAll().stream()
                .filter(this::hasCommonDirectorAndWriter)
                .toList();

        List<String> directorIds = crews.stream()
                .flatMap(crew -> Arrays.stream(crew.getDirectors().split(",")))
                .map(String::trim)
                .distinct()
                .collect(Collectors.toList());

        List<Actor> livingDirectors = actorService.getActorsByIds(directorIds).stream()
                .filter(actor -> actor.getDeathYear() == null || actor.getDeathYear().isEmpty())
                .toList();

        Set<String> livingDirectorIds = livingDirectors.stream()
                .map(Actor::getId)
                .collect(Collectors.toSet());

        return crews.stream()
                .filter(crew -> Arrays.stream(crew.getDirectors().split(","))
                        .map(String::trim)
                        .anyMatch(livingDirectorIds::contains))
                .collect(Collectors.toList());
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
