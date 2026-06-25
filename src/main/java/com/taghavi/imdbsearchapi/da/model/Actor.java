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
    @Column(name = "NCONST")
    private String id;

    @Column(name = "PRIMARYNAME", length = 90000000)
    private String actorName;

    @Column(name = "BIRTHYEAR")
    private String birthYear;

    @Column(name = "DEATHYEAR")
    private String deathYear;

    @Column(name = "PRIMARYPROFESSION", length = 90000000)
    private String primaryProfession;

    @Column(name = "KNOWNFORTITLES", length = 90000000)
    private String titles;

}
