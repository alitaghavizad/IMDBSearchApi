package com.taghavi.imdbsearchapi.da.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "title_crew")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MovieCrew {

    @Id
    @Column(name = "tconst")
    private String id;

    @Column(name = "directors",length = 90000000)
    private String directors;

    @Column(name = "writers",length = 90000000)
    private String writers;
}
