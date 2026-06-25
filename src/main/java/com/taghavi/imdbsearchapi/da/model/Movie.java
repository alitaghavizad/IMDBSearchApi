package com.taghavi.imdbsearchapi.da.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "title_basics")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @Column(name = "tconst")
    private String id;

    @Column(name = "title_type", length = 90000000)
    private String titleType;

    @Column(name = "primary_title", length = 90000000)
    private String primaryTitle;

    @Column(name = "original_title", length = 90000000)
    private String originalTitle;

    @Column(name = "is_adult")
    private Boolean isAdult;

    @Column(name = "start_year")
    private Integer  startYear;

    @Column(name = "end_year")
    private Integer endYear;

    @Column(name = "runtime_minutes")
    private String runtimeMinutes;

    @Column(name = "genres", length = 90000000)
    private String genres;
}
