package org.example.manager;

import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.exception.ComicNotFoundException;
import org.example.exception.PageNotFoundException;
import org.example.model.CharacterBasicModel;
import org.example.model.ComicBasicModel;
import org.example.model.ComicFullModel;
import org.example.rowmapper.CharacterBasicRowMapper;
import org.example.rowmapper.ComicBasicRowMapper;
import org.example.rowmapper.ComicFullRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ComicsManager {
    private final int itemsByPage = 3;
    private final NamedParameterJdbcTemplate template;
    private final CharacterBasicRowMapper characterBasicRowMapper;
    private final ComicBasicRowMapper comicBasicRowMapper;
    private final ComicFullRowMapper comicFullRowMapper;

    public ComicsResponseDTO comics(int page) {
        try {
            final List<ComicBasicModel> items = template.query(
                    // language=PostgreSQL
                    """
                            SELECT id, title, issue_number,writer, image FROM comics
                            ORDER BY id
                            """,
                    comicBasicRowMapper
            );

            int fromIndex = (page - 1) * itemsByPage;
            int toIndex = fromIndex + itemsByPage;

            final ComicsResponseDTO responseDTO = new ComicsResponseDTO(new ArrayList<>(items.size()));
            for (ComicBasicModel item : items.subList(fromIndex, toIndex)) {
                responseDTO.getComics().add(new ComicsResponseDTO.Comic(
                        item.getId(),
                        item.getTitle(),
                        item.getIssueNumber(),
                        item.getWriter(),
                        item.getImage()
                ));
            }
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new PageNotFoundException(e);
        }
    }

    public ComicIdResponseDTO comicId(long id) {
        try {
            final ComicFullModel item = template.queryForObject(
                    // language=PostgreSQL
                    """
                            SELECT id, title, issue_number, writer, published,
                             description, image, id_characters FROM comics
                            WHERE id = :id
                            """,
                    Map.of("id", id),
                    comicFullRowMapper
            );
            final ComicIdResponseDTO responseDTO = new ComicIdResponseDTO(new ComicIdResponseDTO.Comic(
                    item.getId(),
                    item.getTitle(),
                    item.getIssueNumber(),
                    item.getWriter(),
                    item.getPublished(),
                    item.getDescription(),
                    item.getImage()
            ));
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new ComicNotFoundException(e);
        }
    }

    public CharactersResponseDTO charactersComicId(long id) {
        try {
            final List<CharacterBasicModel> items = template.query(
                    // language=PostgreSQL
                    """
                            SELECT id, nickname, name, image FROM characters
                            WHERE :id = any(id_comics)
                            """,
                    Map.of("id", id),
                    characterBasicRowMapper
            );
            final CharactersResponseDTO responseDTO = new CharactersResponseDTO(new ArrayList<>(items.size()));
            for (CharacterBasicModel item : items) {
                responseDTO.getCharacters().add(new CharactersResponseDTO.Character(
                        item.getId(),
                        item.getNickname(),
                        item.getName(),
                        item.getImage()
                ));
            }
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new ComicNotFoundException(e);
        }
    }

    public ComicSaveResponseDTO save(ComicSaveRequestDTO requestDTO) {
        return requestDTO.getId() == 0 ? create(requestDTO) : update(requestDTO);
    }

    private ComicSaveResponseDTO update(ComicSaveRequestDTO requestDTO) {
        try {
            final ComicFullModel item = template.queryForObject(
                    // language=PostgreSQL
                    """
                            UPDATE comics SET title = :title, issue_number = :issueNumber, writer = :writer,
                            published = :published, description = :description, image = :image, id_characters = :idCharacters
                            WHERE id = :id
                            RETURNING id, title, issue_number, writer, published, description, image, id_characters
                            """,
                    Map.of(
                            "id", requestDTO.getId(),
                            "title", requestDTO.getTitle(),
                            "issueNumber", requestDTO.getIssueNumber(),
                            "writer", requestDTO.getWriter(),
                            "published", requestDTO.getPublished(),
                            "description", requestDTO.getDescription(),
                            "image", requestDTO.getImage(),
                            "idCharacters", requestDTO.getIdCharacters()
                    ),
                    comicFullRowMapper
            );
            final ComicSaveResponseDTO responseDTO = new ComicSaveResponseDTO(new ComicSaveResponseDTO.Comic(
                    item.getId(),
                    item.getTitle(),
                    item.getIssueNumber(),
                    item.getWriter(),
                    item.getPublished(),
                    item.getDescription(),
                    item.getImage(),
                    item.getIdCharacters()
            ));
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new ComicNotFoundException(e);
        }
    }

    private ComicSaveResponseDTO create(ComicSaveRequestDTO requestDTO) {
        final ComicFullModel item = template.queryForObject(
                // language=PostgreSQL
                """
                        INSERT INTO comics (title, issue_number, writer, published, description, image, id_characters)
                        VALUES (:title, :issueNumber, :writer, :published, :description, :image, :idCharacters)
                        RETURNING id, title, issue_number, writer, published, description, image, id_characters
                        """,
                Map.of(
                        "title", requestDTO.getTitle(),
                        "issueNumber", requestDTO.getIssueNumber(),
                        "writer", requestDTO.getWriter(),
                        "published", requestDTO.getPublished(),
                        "description", requestDTO.getDescription(),
                        "image", requestDTO.getImage(),
                        "idCharacters", requestDTO.getIdCharacters()
                ),
                comicFullRowMapper
        );
        final ComicSaveResponseDTO responseDTO = new ComicSaveResponseDTO(new ComicSaveResponseDTO.Comic(
                item.getId(),
                item.getTitle(),
                item.getIssueNumber(),
                item.getWriter(),
                item.getPublished(),
                item.getDescription(),
                item.getImage(),
                item.getIdCharacters()
        ));
        return responseDTO;
    }
}
