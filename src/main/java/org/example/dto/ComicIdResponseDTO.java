package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ComicIdResponseDTO {
    private Comic comic;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Comic {
        private long id;
        private String title;
        private int issueNumber;
        private String[] writer;
        private String published;
        private String description;
        private String image;
    }
}
