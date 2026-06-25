package com.taghavi.imdbsearchapi.da.model;

import com.taghavi.imdbsearchapi.da.converter.NullDoubleConverter;
import com.taghavi.imdbsearchapi.da.converter.NullIntegerConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "title_ratings")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TitleRatings {

    @Id
    @Column(name = "TCONST")
    private String id;

    @Column(name = "AVERAGERATING")
    @Convert(converter = NullDoubleConverter.class)
    private Double averageRating;

    @Column(name = "NUMVOTES")
    @Convert(converter = NullIntegerConverter.class)
    private Integer numVotes;
}
