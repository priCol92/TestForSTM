package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CharacterIdResponseDTO {
    private Character character;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Character {
        private long id;
        private String nickname;
        private String name;
        private String description;
        private String image;
    }
}
