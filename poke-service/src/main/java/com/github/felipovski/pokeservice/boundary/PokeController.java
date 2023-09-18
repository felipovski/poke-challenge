package com.github.felipovski.pokeservice.boundary;

import com.github.felipovski.pokeservice.entity.dto.PokeResponseDto;
import com.github.felipovski.pokeservice.service.PokeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/pokemons")
public class PokeController {

    private final PokeService service;

    public PokeController(PokeService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<PokeResponseDto> pokemons(@RequestParam Optional<String> query,
                                                    @RequestParam(defaultValue = "alphabetical") Optional<String> sort) {
        return ResponseEntity
                .ok()
                .body(service.getSortedListByQuery(query, sort));
    }

    @GetMapping("/highlight")
    public ResponseEntity<PokeResponseDto> highlightedPokemons(@RequestParam Optional<String> query,
                                                               @RequestParam(defaultValue = "alphabetical") Optional<String> sort) {
        return ResponseEntity
                .ok()
                .body(service.getHighlighted(query, sort));
    }
}
