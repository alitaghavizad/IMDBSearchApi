package com.taghavi.imdbsearchapi.da.repository;

import com.taghavi.imdbsearchapi.da.model.TitleRatings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TitleRatingsRepository extends JpaRepository<TitleRatings, String> {
    List<TitleRatings> findByIdIn(Collection<String> ids);
}
