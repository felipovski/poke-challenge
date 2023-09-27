package com.github.felipovski.pokeservice.control.sort;

import com.github.felipovski.pokeservice.ancillary.enums.SortType;
import com.github.felipovski.pokeservice.entity.dto.PokemonDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public interface PokeSort {

    /**
     * Sorts the list of PokemonDto based on the provided SortType.
     * Considering the complexity in terms of BigO notation, the chosen algorithms is MergeSort.
     * Its complexity being O(n log n).
     * Although MergeSort faces some problems with memory usage,
     * since the number of existing Pokémons is relatively low, we decided to keep it.
     * Note: As an alternative, we could have chosen QuickSort, which has a similar
     * time complexity (O(n log n)), but it also has a worst-case scenario of O(n^2).
     *
     * @param pokemons List of PokemonDto.
     * @param sortType The sort type.
     * @return Sorted PokemonDto list.
     */
    default List<PokemonDto> sort(List<PokemonDto> pokemons, SortType sortType) {
        // Check whether Pokémon list is null or has size 0 or 1
        // if that is the case, returns the list
        if (pokemons == null || pokemons.size() <= 1) {
            return pokemons;
        }

        // divide and conquer approach
        // divide the list by 2 to be split by left and right
        int mid = pokemons.size() / 2;
        // Recursive calls
        List<PokemonDto> left = sort(pokemons.subList(0, mid), sortType);
        List<PokemonDto> right = sort(pokemons.subList(mid, pokemons.size()), sortType);

        return merge(left, right, sortType);
    }

    default List<PokemonDto> merge(List<PokemonDto> left, List<PokemonDto> right, SortType sortType) {
        List<PokemonDto> result = new ArrayList<>();
        int a = 0;
        int b = 0;

        while (a < left.size() && b < right.size()) {
            boolean addLeft;
            // If sortType is ALPHABETICAL, then compare the strings
            if (sortType == SortType.ALPHABETICAL) {
                // if left is smaller than right, then addLeft is set to true
                addLeft = left.get(a).getName().compareToIgnoreCase(right.get(b).getName()) < 0;
            }
            else {
                // if sortType == SortType.LENGTH, then compare the string lengths
                // if left is smaller than right, then addLeft is set to true
                addLeft = left.get(a).getName().length() < right.get(b).getName().length();
            }

            // based on the value of addLeft, add the smaller element
            if (addLeft) {
                result.add(left.get(a++));
            } else {
                result.add(right.get(b++));
            }
        }

        // Add remainder
        while (a < left.size()) {
            result.add(left.get(a++));
        }
        while (b < right.size()) {
            result.add(right.get(b++));
        }
        return result;
    }
}
