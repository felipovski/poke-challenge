package com.github.felipovski.pokeservice.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class PokemonDto {

    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String highlight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }
}
