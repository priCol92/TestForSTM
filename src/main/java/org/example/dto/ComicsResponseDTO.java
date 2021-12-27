package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ComicsResponseDTO {
    private List<Comic> comics;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Comic {
        private long id;
        private String title;
        private int issueNumber;
        private String[] writer;
        private String image;
    }
}
