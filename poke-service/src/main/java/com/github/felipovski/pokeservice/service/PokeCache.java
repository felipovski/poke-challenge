package com.github.felipovski.pokeservice.service;

import com.github.felipovski.pokeservice.entity.dto.PokeApiDto;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

@Service
@Profile("!test")
public class PokeCache {

    public static final PokeApiDto cache = new PokeApiDto();
    private final Timer timer = new Timer();
    private final PokeService pokeService;

    public PokeCache(PokeService pokeService) {
        this.pokeService = pokeService;
    }

    public void clearCache() {
        if (Objects.nonNull(cache.getResults())) {
            cache.getResults().clear();
        }
    }

    @PostConstruct
    public void initialize() {
        scheduleApiRequest(this::loadData);
    }

    public void loadData() {
        clearCache();

        int offset = 0;
        int limit = 100;

        PokeApiDto response;
        do {
            response = pokeService.fetchPokemons(offset, limit);
            cache.addPokeList(response.getResults());
            offset += limit;
        } while (!response.getResults().isEmpty());
    }

    private void scheduleApiRequest(Runnable task) {
        TimerTask fetchDataTask = new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        };
        long delay = 0;
        long period = 60L * 60L * 1000L;
        timer.schedule(fetchDataTask, delay, period);
    }
}