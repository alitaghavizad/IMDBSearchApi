package com.taghavi.imdbsearchapi.service.impl;

import com.taghavi.imdbsearchapi.da.model.Actor;
import com.taghavi.imdbsearchapi.da.repository.ActorRepository;
import com.taghavi.imdbsearchapi.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return repository.findByActorName(name);
    }

    @Override
    public List<Actor> getActorsByIds(List<String> ids) {
        return repository.findByIdIn(ids);
    }
}
