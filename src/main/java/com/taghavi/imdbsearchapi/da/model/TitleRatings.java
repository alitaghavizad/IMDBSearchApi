package com.taghavi.imdbsearchapi.da.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "tconst")
    private String id;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "num_votes")
    private Integer numVotes;
}
