package com.taghavi.imdbsearchapi.utility;

import com.taghavi.imdbsearchapi.da.model.Actor;
import com.taghavi.imdbsearchapi.da.model.Movie;
import com.taghavi.imdbsearchapi.da.model.MovieCrew;
import com.taghavi.imdbsearchapi.da.model.TitleRatings;
import com.taghavi.imdbsearchapi.da.repository.ActorRepository;
import com.taghavi.imdbsearchapi.da.repository.MovieCrewRepository;
import com.taghavi.imdbsearchapi.da.repository.MovieRepository;
import com.taghavi.imdbsearchapi.da.repository.TitleRatingsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ImdbImporter {

    private static final int BATCH_SIZE = 1000; // Reduced batch size for lower memory footprint

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private final MovieCrewRepository movieCrewRepository;
    private final TitleRatingsRepository titleRatingsRepository;


    public void populateDb() throws Exception {
        importTitleBasics();
        importTitleCrew();
        importTitleRatings();
        importNameBasics();
        System.out.println("IMDb import completed.");
    }

    private void importTitleBasics() throws Exception {
        load("imdb/title.basics.tsv", movieRepository, row -> {
            Movie movie = new Movie();
            movie.setId(value(row, "tconst"));
            movie.setTitleType(value(row, "titleType"));
            movie.setPrimaryTitle(value(row, "primaryTitle"));
            movie.setOriginalTitle(value(row, "originalTitle"));
            movie.setIsAdult("1".equals(value(row, "isAdult")));
            movie.setStartYear(intValue(row, "startYear"));
            movie.setEndYear(intValue(row, "endYear"));
            movie.setRuntimeMinutes(value(row, "runtimeMinutes"));
            movie.setGenres(value(row, "genres"));
            return movie;
        });
    }

    private void importTitleCrew() throws Exception {
        load("imdb/title.crew.tsv", movieCrewRepository, row -> {
            MovieCrew crew = new MovieCrew();
            crew.setId(value(row, "tconst"));
            crew.setDirectors(value(row, "directors"));
            crew.setWriters(value(row, "writers"));
            return crew;
        });
    }

    private void importTitleRatings() throws Exception {
        load("imdb/title.ratings.tsv", titleRatingsRepository, row -> {
            TitleRatings ratings = new TitleRatings();
            ratings.setId(value(row, "tconst"));
            ratings.setAverageRating(doubleValue(row, "averageRating"));
            ratings.setNumVotes(intValue(row, "numVotes"));
            return ratings;
        });
    }

    private void importNameBasics() throws Exception {
        load("imdb/name.basics.tsv", actorRepository, row -> {
            Actor actor = new Actor();
            actor.setId(value(row, "nconst"));
            actor.setActorName(value(row, "primaryName"));
            actor.setBirthYear(value(row, "birthYear"));
            actor.setDeathYear(value(row, "deathYear"));
            actor.setPrimaryProfession(value(row, "primaryProfession"));
            actor.setTitles(value(row, "knownForTitles"));
            return actor;
        });
    }

    private <T> void load(String file, JpaRepository<T, String> repository, RowMapper<T> mapper) throws Exception {
        try (Reader reader = new InputStreamReader(new ClassPathResource(file).getInputStream())) {
            CSVParser parser = CSVFormat.Builder.create().setDelimiter('\t').setHeader().setSkipHeaderRecord(true).setQuote(null).setEscape(null).setIgnoreEmptyLines(true).build().parse(reader);

            List<T> batch = new ArrayList<>();
            int counter = 0;

            for (CSVRecord row : parser) {
                batch.add(mapper.map(row));
                if (batch.size() >= BATCH_SIZE) {
                    repository.saveAll(batch);
                    counter += batch.size();
                    System.out.println("Imported " + counter + " rows from " + file);
                    batch.clear();
                }
            }

            if (!batch.isEmpty()) {
                repository.saveAll(batch);
                counter += batch.size();
            }

            System.out.println("Finished " + file + " → " + counter);
        }
    }

    private String value(CSVRecord row, String field) {
        String v = row.get(field);
        return "\\N".equals(v) ? null : v;
    }

    private Integer intValue(CSVRecord row, String field) {
        String v = value(row, field);
        return v == null ? null : Integer.parseInt(v);
    }

    private Double doubleValue(CSVRecord row, String field) {
        String v = value(row, field);
        return v == null ? null : Double.parseDouble(v);
    }

    @FunctionalInterface
    interface RowMapper<T> {
        T map(CSVRecord row);
    }
}
