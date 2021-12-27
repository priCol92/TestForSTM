package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CharactersResponseDTO {
    private List<Character> characters;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Character {
        private long id;
        private String nickname;
        private String name;
        private String image;
    }
}
