package com.taghavi.imdbsearchapi.service.impl;

import com.taghavi.imdbsearchapi.da.model.Actor;
import com.taghavi.imdbsearchapi.da.repository.ActorRepository;
import com.taghavi.imdbsearchapi.service.ActorService;
import com.taghavi.imdbsearchapi.utility.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {
    private final ActorRepository repository;

    @Override
    public List<Actor> getAllActors() {
        try {
            Logger.log("Getting all actors");
            return repository.findAll();
        } catch (Exception e) {
            Logger.log("Error getting all actors: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Actor getActorByName(String name) {
        try {
            Logger.log("Getting actor by name: " + name);
            return repository.findByActorName(name).get(0);
        } catch (Exception e) {
            Logger.log("Error getting actor by name: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Actor> getActorsByIds(List<String> ids) {
        try {
            Logger.log("Getting actors by ids");
            List<Actor> result = new ArrayList<>();
            for (int i = 0; i < ids.size(); i += 100) {
                int end = Math.min(i + 100, ids.size());
                result.addAll(repository.findByIdIn(ids.subList(i, end)));
            }
            return result;
        } catch (Exception e) {
            Logger.log("Error getting actors by ids: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<String> getLivingActorsIds() {
        try {
            Logger.log("Getting living actors ids");
            return repository.findAllLivingActors();
        } catch (Exception e) {
            Logger.log("Error getting living actors ids: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
