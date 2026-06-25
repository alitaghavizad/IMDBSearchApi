package com.taghavi.imdbsearchapi.service;

import com.taghavi.imdbsearchapi.da.model.Actor;

import java.util.List;

public interface ActorService {
    List<Actor> getAllActors();
    Actor getActorByName(String name);
    List<Actor> getActorsByIds(List<String> ids);
}
