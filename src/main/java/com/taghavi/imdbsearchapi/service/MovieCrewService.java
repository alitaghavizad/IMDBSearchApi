package com.taghavi.imdbsearchapi.service;

import com.taghavi.imdbsearchapi.da.model.MovieCrew;

import java.util.List;

public interface MovieCrewService {
    List<MovieCrew> getAllMovieCrews();

    List<MovieCrew> getAllBySameWriterDirector(int page, int size);
}
