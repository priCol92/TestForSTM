package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ComicBasicModel {
    private long id;
    private String title;
    private int issueNumber;
    private String[] writer;
    private String image;
}
