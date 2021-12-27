package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.manager.CharacterManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class CharactersController {
    private final CharacterManager manager;

    @GetMapping("/characters")
    public CharactersResponseDTO characters(@RequestParam int page) {
        return manager.characters(page);
    }

    @GetMapping("/characters/{id}")
    public CharacterIdResponseDTO characterFromPath(@PathVariable long id) {
        return manager.characterId(id);
    }

    @GetMapping("/characters/{id}/comics")
    public ComicsResponseDTO comicsCharacterId(@PathVariable long id) {
        return manager.comicsCharacterId(id);
    }

    @PostMapping("/characters/save")
    public CharacterSaveResponseDTO save(@RequestBody CharacterSaveRequestDTO requestDTO) {
        return manager.save(requestDTO);
    }
}
