package com.taghavi.imdbsearchapi.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Component
@RequiredArgsConstructor
public class ImdbImporter {
    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String DB_USER;
    @Value("${spring.datasource.password}")
    private String DB_PASSWORD;

    private final ImdbDownloader imdbDownloader;

    public void populateDb() throws Exception {
        imdbDownloader.downloadTsvFiles();
        Thread.sleep(5000);
        populateDbData("title.basics.tsv", "title_basics");
        populateDbData("title.crew.tsv", "title_crew");
        populateDbData("title.ratings.tsv", "title_ratings");
        populateDbData("name.basics.tsv", "name_basics");

        System.out.println("IMDb import completed.");
    }

    public void populateDbData(String fileName, String tableName) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {

            System.out.println("Connected to H2 database.");

            stmt.execute("SET CACHE_SIZE 65536");
            stmt.execute("SET LOCK_MODE 0");
            URI uri = ImdbImporter.class
                    .getClassLoader()
                    .getResource("imdb/" + fileName)
                    .toURI();

            String path = Paths.get(uri).toString().replace("\\", "/");
            System.out.println("Loading file from: " + path);
            stmt.execute("DROP TABLE " + tableName);
            String sql = "CREATE TABLE " + tableName + " AS SELECT * FROM CSVREAD('" + path + "',null,'fieldSeparator=\t')";
            long start = System.currentTimeMillis();
            stmt.execute(sql);

            long elapsed = System.currentTimeMillis() - start;
            System.out.printf("Data loaded successfully in %.1f seconds.%n", elapsed / 1000.0);
            stmt.execute("SET LOCK_MODE 3");

        } catch (Exception e) {
            System.err.println("Failed to load data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
