package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CharacterBasicModel {
    private long id;
    private String nickname;
    private String name;
    private String image;
}
