package com.github.felipovski.pokeservice.ancillary.enums;

import com.github.felipovski.pokeservice.service.exception.IllegalSortingTypeException;

import java.util.Arrays;

public enum SortType {

    ALPHABETICAL("alphabetical"),
    LENGTH("length");

    private final String strType;

    SortType(String strType) {
        this.strType = strType;
    }

    public String getStrType() {
        return strType;
    }
    public static SortType findByType(String strType) {
        return Arrays.stream(SortType.values())
                .filter(t -> t.getStrType().equals(strType))
                .findFirst()
                .orElseThrow(IllegalSortingTypeException::new);
    }
}
