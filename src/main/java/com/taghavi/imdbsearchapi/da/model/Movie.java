package com.taghavi.imdbsearchapi.da.model;

import com.taghavi.imdbsearchapi.da.converter.NullIntegerConverter;
import jakarta.persistence.*;
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
    @Column(name = "TCONST")
    private String id;

    @Column(name = "TITLETYPE", length = 90000000)
    private String titleType;

    @Column(name = "PRIMARYTITLE", length = 90000000)
    private String primaryTitle;

    @Column(name = "ORIGINALTITLE", length = 90000000)
    private String originalTitle;

    @Column(name = "ISADULT")
    private Integer isAdult;

    @Column(name = "STARTYEAR")
    @Convert(converter = NullIntegerConverter.class)
    private Integer  startYear;

    @Column(name = "ENDYEAR")
    @Convert(converter = NullIntegerConverter.class)
    private Integer endYear;

    @Column(name = "RUNTIMEMINUTES")
    private String runtimeMinutes;

    @Column(name = "GENRES", length = 90000000)
    private String genres;
}
