package com.taghavi.imdbsearchapi.da.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "name_basics")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Actor {

    @Id
    @Column(name = "nconst")
    private String id;

    @Column(name = "primary_name", length = 90000000)
    private String actorName;

    @Column(name = "birth_year")
    private String birthYear;

    @Column(name = "death_year")
    private String deathYear;

    @Column(name = "primary_profession", length = 90000000)
    private String primaryProfession;

    @Column(name = "known_for_titles", length = 90000000)
    private String titles;

}
