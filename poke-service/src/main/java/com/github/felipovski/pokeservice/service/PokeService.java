package com.github.felipovski.pokeservice.service;

import com.github.felipovski.pokeservice.ancillary.enums.SortType;
import com.github.felipovski.pokeservice.entity.dto.PokeApiDto;
import com.github.felipovski.pokeservice.entity.dto.PokeResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.function.*;

import static com.github.felipovski.pokeservice.ancillary.SortUtils.sort;
import static com.github.felipovski.pokeservice.service.PokeCache.cache;

@Service
public class PokeService {

    private final RestTemplate restTemplate;

    @Value("${API_URL}")
    private String apiUrl;

    public PokeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PokeResponseDto getSortedListByQuery(Optional<String> query,
                                                Optional<String> sort) {

        var cachedPokemons = cache.getResults();

        var filtered = query.map(str -> cachedPokemons.parallelStream()
                        .filter(pokemon -> pokemon.getName().contains(str.toLowerCase())).toList())
                .orElse(cachedPokemons);

        var sortType = sort.map(str -> SortType.findByType(str.toLowerCase()))
                .orElse(null);

        var sorted = sort(filtered, sortType);

        var response = new PokeResponseDto();
        response.setResult(sorted);

        return response;
    }

    public PokeResponseDto getHighlighted(Optional<String> query, Optional<String> sort) {

        var sortedAndQueried = getSortedListByQuery(query, sort);

        if (query.isEmpty()) {
            return sortedAndQueried;
        }

        BinaryOperator<String> highlight = (p, q) -> p.replace(p, "<pre>" + q + "</pre>");

        var responseDto = new PokeResponseDto();
        responseDto.setResult(sortedAndQueried.getResult().parallelStream()
                .map(pokemon -> {
                    pokemon.setHighlight(pokemon.getName());
                    pokemon.setHighlight(highlight.apply(pokemon.getHighlight(), query.get()));
                    return pokemon;
                }).toList());

        return responseDto;
    }

    public PokeApiDto fetchPokemons(int offset, int limit) {
        try {
            return restTemplate.getForObject(apiUrl, PokeApiDto.class, offset, limit);
        } catch (Exception e) {
            return new PokeApiDto();
        }
    }

}