package com.github.felipovski.pokeservice.entity.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PokeApiDto {

    private List<PokemonDto> results;

    public List<PokemonDto> getResults() {
        return results;
    }

    public void setResults(List<PokemonDto> results) {
        this.results = results;
    }

    public void addPokeList(List<PokemonDto> pokemons) {
        if (Objects.isNull(results)) {
            results = new ArrayList<>();
        }
        this.results.addAll(pokemons);
    }
}