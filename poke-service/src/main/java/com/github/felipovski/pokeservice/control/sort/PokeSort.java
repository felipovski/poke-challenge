package com.github.felipovski.pokeservice.control.sort;

import com.github.felipovski.pokeservice.ancillary.enums.SortType;
import com.github.felipovski.pokeservice.entity.dto.PokemonDto;

import java.util.List;

public interface PokeSort {

    List<PokemonDto> sort(List<PokemonDto> pokemons, SortType sortType);

    List<PokemonDto> merge(List<PokemonDto> left, List<PokemonDto> right, SortType sortType);
}
