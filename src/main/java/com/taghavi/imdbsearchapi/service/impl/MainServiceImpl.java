package com.taghavi.imdbsearchapi.service.impl;

import com.taghavi.imdbsearchapi.da.model.Actor;
import com.taghavi.imdbsearchapi.da.model.Movie;
import com.taghavi.imdbsearchapi.da.model.MovieCrew;
import com.taghavi.imdbsearchapi.da.model.TitleRatings;
import com.taghavi.imdbsearchapi.service.MainService;
import com.taghavi.imdbsearchapi.service.MovieCrewService;
import com.taghavi.imdbsearchapi.service.TitleRatingsService;
import com.taghavi.imdbsearchapi.utility.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {
    private final ActorServiceImpl actorService;
    private final MovieServiceImpl movieService;
    private final MovieCrewService movieCrewService;
    private final TitleRatingsService titleRatingsService;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<String> findAllByActors(String actor1, String actor2) {
        try {
            Logger.log("Finding all movies by actors: " + actor1 + ", " + actor2);
            Actor actor1Entity = actorService.getActorByName(actor1);
            Set<String> actor1Movies = new HashSet<>(Arrays.asList(actor1Entity.getTitles().split(",")));

            Actor actor2Entity = actorService.getActorByName(actor2);
            List<String> actor2Movies = Arrays.asList(actor2Entity.getTitles().split(","));

            List<String> commonMovieIds = actor2Movies.stream()
                    .filter(actor1Movies::contains)
                    .collect(Collectors.toList());

            return movieService.getMoviesByIds(commonMovieIds);
        } catch (Exception e) {
            Logger.log("Error finding movies by actors: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<String> findAllByDirectorAndWriterSame() {
        try {
            Logger.log("Finding all movies by director and writer same");
            List<MovieCrew> movieCrews = movieCrewService.getAllBySameWriterDirector();
            List<String> moviesIds = movieCrews.stream().map(MovieCrew::getId).toList();
            return movieService.getMoviesByIds(moviesIds);
        } catch (Exception e) {
            Logger.log("Error finding movies by director and writer same: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Map<Integer, String> findBestTitlesByGenre(String genre) {
        try {
            Logger.log("Finding best titles by genre: " + genre);
            List<Movie> movies = movieService.getAllMoviesByGenre(genre);

            List<String> movieIds = movies.stream().map(Movie::getId).toList();
            List<TitleRatings> ratings = titleRatingsService.getAllTitleRatingsByMovies(movieIds);

            Map<String, TitleRatings> ratingsMap = ratings.stream()
                    .collect(Collectors.toMap(TitleRatings::getId, rating -> rating));

            Map<Integer, List<Movie>> moviesByYear = movies.stream()
                    .filter(movie -> movie.getStartYear() != null)
                    .collect(Collectors.groupingBy(Movie::getStartYear));

            Map<Integer, String> bestTitles = new HashMap<>();
            for (Map.Entry<Integer, List<Movie>> entry : moviesByYear.entrySet()) {
                int year = entry.getKey();
                List<Movie> yearMovies = entry.getValue();

                Optional<Movie> bestMovie = yearMovies.stream()
                        .max(Comparator.comparingDouble(movie -> {
                            TitleRatings rating = ratingsMap.get(movie.getId());
                            return rating != null ? rating.getAverageRating() * rating.getNumVotes() : 0;
                        }));

                bestMovie.ifPresent(movie -> bestTitles.put(year, movie.getPrimaryTitle()));
            }

            return bestTitles;
        } catch (Exception e) {
            Logger.log("Error finding best titles by genre: " + e.getMessage());
            return Collections.emptyMap();
        }
    }

    @Override
    public void truncateAllTables() {
        try {
            Logger.log("Truncating all tables");
            jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
            jdbcTemplate.execute("TRUNCATE TABLE title_basics");
            jdbcTemplate.execute("TRUNCATE TABLE title_crew");
            jdbcTemplate.execute("TRUNCATE TABLE title_ratings");
            jdbcTemplate.execute("TRUNCATE TABLE name_basics");
            jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
        } catch (Exception e) {
            Logger.log("Error truncating tables: " + e.getMessage());
        }
    }
}
