package com.taghavi.imdbsearchapi.service.impl;

import com.taghavi.imdbsearchapi.da.model.Actor;
import com.taghavi.imdbsearchapi.da.model.TitleRatings;
import com.taghavi.imdbsearchapi.da.repository.TitleRatingsRepository;
import com.taghavi.imdbsearchapi.service.TitleRatingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TitleRatingsServiceImpl implements TitleRatingsService {
    private final TitleRatingsRepository repository;

    @Override
    public List<TitleRatings> getAllTitleRatings() {
        return repository.findAll();
    }

    @Override
    public List<TitleRatings> getAllTitleRatingsByMovies(List<String> movieIds) {
        List<TitleRatings> result = new ArrayList<>();
        for (int i = 0; i < movieIds.size(); i += 100) {
            int end = Math.min(i + 100, movieIds.size());
            result.addAll(repository.findByIdIn(movieIds.subList(i, end)));
        }
        return result;
    }
}
