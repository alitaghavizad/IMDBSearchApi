package com.taghavi.imdbsearchapi.service;

import com.taghavi.imdbsearchapi.da.model.TitleRatings;

import java.util.List;

public interface TitleRatingsService {
    List<TitleRatings> getAllTitleRatings();

    List<TitleRatings> getAllTitleRatingsByMovies(List<String> movieIds);
}
