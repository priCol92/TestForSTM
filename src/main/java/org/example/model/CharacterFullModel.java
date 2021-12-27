package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CharacterFullModel {
    private long id;
    private String nickname;
    private String name;
    private String description;
    private String image;
    private Integer[] idComics;
}
