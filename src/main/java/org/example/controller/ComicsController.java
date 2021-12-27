package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.manager.ComicsManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class ComicsController {
    private final ComicsManager manager;

    @GetMapping("/comics")
    public ComicsResponseDTO characters(@RequestParam int page) {
        return manager.comics(page);
    }

    @GetMapping("/comics/{id}")
    public ComicIdResponseDTO characterFromPath(@PathVariable long id) {
        return manager.comicId(id);
    }

    @GetMapping("/comics/{id}/characters")
    public CharactersResponseDTO charactersComicsIdr(@PathVariable long id) {
        return manager.charactersComicId(id);
    }

    @PostMapping("/comics/save")
    public ComicSaveResponseDTO save(@RequestBody ComicSaveRequestDTO requestDTO) {
        return manager.save(requestDTO);
    }
}
