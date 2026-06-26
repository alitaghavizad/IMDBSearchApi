package com.taghavi.imdbsearchapi.controller;

import com.taghavi.imdbsearchapi.service.MainService;
import com.taghavi.imdbsearchapi.utility.ImdbImporter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final AtomicLong apiCallCount = new AtomicLong(0);

    private final MainService mainService;
    private final ImdbImporter imdbImporter;

    //takes under 10 seconds to respond
    @GetMapping("/sameMovies")
    public List<String> getSameMovies(@RequestParam String actor1, @RequestParam String actor2) {
        incrementApiCount();
        return mainService.findAllByActors(actor1, actor2);
    }
    // takes nearly a minute to respond
    @GetMapping("/sameDirectorWriter")
    public List<String> getSameDirectorAndWriter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        incrementApiCount();
        return mainService.findAllByDirectorAndWriterSame(page, size);
    }
    // takes around 1 and a half minutes to respond
    @GetMapping("/bestTitlesByGenre")
    public Map<Integer, String> getBestTitlesByGenre(@RequestParam String genre) {
        incrementApiCount();
        return mainService.findBestTitlesByGenre(genre);
    }
    // the operation of insertion of data takes 5 to 6 minutes and indexing the tables takes around the same time for a total of near 10 minutes
    @GetMapping("/initializeDataBase")
    public void initializeDataBase() throws Exception {
        incrementApiCount();
        mainService.truncateAllTables();
        imdbImporter.populateDb();
    }
    @GetMapping("/apiCount")
    public long getApiCount() {
        return getApiCounter();
    }

    public void incrementApiCount() {
        apiCallCount.incrementAndGet();
    }

    public long getApiCounter() {
        return apiCallCount.get();
    }
}
