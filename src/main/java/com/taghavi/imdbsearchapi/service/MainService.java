package com.taghavi.imdbsearchapi.service;

import com.taghavi.imdbsearchapi.da.model.Movie;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface MainService {
    List<String> findAllByActors(String actor1,String actor2);
    List<String> findAllByDirectorAndWriterSame(int page, int size);
    Map<Integer, String> findBestTitlesByGenre(String genre);

    void truncateAllTables();
}
