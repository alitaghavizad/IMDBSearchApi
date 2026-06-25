package com.taghavi.imdbsearchapi.utility;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.*;
import java.util.List;
import java.util.zip.GZIPInputStream;


@Component
public class ImdbDownloader {

    private static final String BASE_URL = "https://datasets.imdbws.com/";

    private static final List<String> FILES = List.of("title.basics.tsv.gz", "title.ratings.tsv.gz", "title.crew.tsv.gz", "name.basics.tsv.gz");

    public void downloadTsvFiles() throws Exception {

        Path imdbDir = Paths.get("src", "main", "resources", "imdb");
        if (Files.exists(imdbDir)) {
            deleteDirectory(imdbDir);
        }
        Files.createDirectories(imdbDir);

        HttpClient client = HttpClient.newHttpClient();

        for (String file : FILES) {

            Path gzPath = imdbDir.resolve(file);

            Path tsvPath = imdbDir.resolve(file.replace(".gz", ""));

            download(client, BASE_URL + file, gzPath);

            unzip(gzPath, tsvPath);

            Files.delete(gzPath);

            System.out.println("Created: " + tsvPath);
        }

        System.out.println("Done.");
    }
    private void deleteDirectory(Path path) throws IOException {

        Files.walk(path)
                .sorted((a, b) -> b.compareTo(a)) // delete children first
                .forEach(p -> {
                    try {
                        Files.delete(p);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private static void download(HttpClient client, String url, Path destination) throws Exception {

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        client.send(request, HttpResponse.BodyHandlers.ofFile(destination));
    }

    private static void unzip(Path gzFile, Path outputFile) throws IOException {

        try (GZIPInputStream input = new GZIPInputStream(Files.newInputStream(gzFile));

             OutputStream output = Files.newOutputStream(outputFile)) {

            byte[] buffer = new byte[8192];

            int read;

            while ((read = input.read(buffer)) > 0) {
                output.write(buffer, 0, read);
            }
        }
    }
}