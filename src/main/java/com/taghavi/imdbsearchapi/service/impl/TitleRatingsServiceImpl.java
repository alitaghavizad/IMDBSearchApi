package com.taghavi.imdbsearchapi.service.impl;

import com.taghavi.imdbsearchapi.da.model.Actor;
import com.taghavi.imdbsearchapi.da.model.TitleRatings;
import com.taghavi.imdbsearchapi.da.repository.TitleRatingsRepository;
import com.taghavi.imdbsearchapi.service.TitleRatingsService;
import com.taghavi.imdbsearchapi.utility.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TitleRatingsServiceImpl implements TitleRatingsService {
    private final TitleRatingsRepository repository;

    @Override
    public List<TitleRatings> getAllTitleRatings() {
        try {
            Logger.log("Getting all title ratings");
            return repository.findAll();
        } catch (Exception e) {
            Logger.log("Error getting all title ratings: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<TitleRatings> getAllTitleRatingsByMovies(List<String> movieIds) {
        try {
            Logger.log("Getting all title ratings by movies");
            List<TitleRatings> result = new ArrayList<>();
            for (int i = 0; i < movieIds.size(); i += 100) {
                int end = Math.min(i + 100, movieIds.size());
                result.addAll(repository.findByIdIn(movieIds.subList(i, end)));
            }
            return result;
        } catch (Exception e) {
            Logger.log("Error getting all title ratings by movies: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
