package com.github.felipovski.pokeservice.entity.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PokeResponseDto {

    private List<PokemonDto> result;

    public List<PokemonDto> getResult() {
        return result;
    }

    public void setResult(List<PokemonDto> result) {
        this.result = result;
    }

    public void addPokeList(List<PokemonDto> pokemons) {
        if (Objects.isNull(result)) {
            result = new ArrayList<>();
        }
        this.result.addAll(pokemons);
    }
}