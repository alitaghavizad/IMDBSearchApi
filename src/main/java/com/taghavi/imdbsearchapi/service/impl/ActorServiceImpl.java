package com.taghavi.imdbsearchapi.service.impl;

import com.taghavi.imdbsearchapi.da.model.Actor;
import com.taghavi.imdbsearchapi.da.repository.ActorRepository;
import com.taghavi.imdbsearchapi.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {
    private final ActorRepository repository;

    @Override
    public List<Actor> getAllActors() {
        return repository.findAll();
    }

    @Override
    public Actor getActorByName(String name) {
        return repository.findByActorName(name).get(0);
    }

    @Override
    public List<Actor> getActorsByIds(List<String> ids) {
        List<Actor> result = new ArrayList<>();
        for (int i = 0; i < ids.size(); i += 100) {
            int end = Math.min(i + 100, ids.size());
            result.addAll(repository.findByIdIn(ids.subList(i, end)));
        }
        return result;
    }
}
