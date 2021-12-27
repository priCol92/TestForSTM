package org.example.manager;

import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.exception.CharacterNotFoundException;
import org.example.exception.PageNotFoundException;
import org.example.model.CharacterBasicModel;
import org.example.model.CharacterFullModel;
import org.example.model.ComicBasicModel;
import org.example.rowmapper.CharacterBasicRowMapper;
import org.example.rowmapper.CharacterFullRowMapper;
import org.example.rowmapper.ComicBasicRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CharacterManager {
    private final int itemsByPage = 3;
    private final NamedParameterJdbcTemplate template;
    private final CharacterBasicRowMapper characterBasicRowMapper;
    private final CharacterFullRowMapper characterFullRowMapper;
    private final ComicBasicRowMapper comicBasicRowMapper;

    public CharactersResponseDTO characters(int page) {
        try {
            final List<CharacterBasicModel> items = template.query(
                    // language=PostgreSQL
                    """
                            SELECT id, nickname, name, image FROM characters
                            ORDER BY id
                            """,
                    characterBasicRowMapper
            );

            int fromIndex = (page - 1) * itemsByPage;
            int toIndex = fromIndex + itemsByPage;

            final CharactersResponseDTO responseDTO = new CharactersResponseDTO(new ArrayList<>(items.size()));
            for (CharacterBasicModel item : items.subList(fromIndex, toIndex)) {
                responseDTO.getCharacters().add(new CharactersResponseDTO.Character(
                        item.getId(),
                        item.getNickname(),
                        item.getName(),
                        item.getImage()
                ));
            }
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new PageNotFoundException(e);
        }
    }

    public CharacterIdResponseDTO characterId(long id) {
        try {
            final CharacterFullModel item = template.queryForObject(
                    // language=PostgreSQL
                    """
                            SELECT id, nickname, name, description, image, id_comics FROM characters
                            WHERE id = :id
                            """,
                    Map.of("id", id),
                    characterFullRowMapper
            );
            final CharacterIdResponseDTO responseDTO = new CharacterIdResponseDTO(new CharacterIdResponseDTO.Character(
                    item.getId(),
                    item.getNickname(),
                    item.getName(),
                    item.getDescription(),
                    item.getImage()
            ));
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new CharacterNotFoundException(e);
        }
    }

    public ComicsResponseDTO comicsCharacterId(long id) {
        try {
            final List<ComicBasicModel> items = template.query(
                    // language=PostgreSQL
                    """
                            SELECT id, title, issue_number, writer, published, 
                            description, id_characters, image FROM comics
                            WHERE :id = any(id_characters)
                            """,
                    Map.of("id", id),
                    comicBasicRowMapper
            );
            final ComicsResponseDTO responseDTO = new ComicsResponseDTO(new ArrayList<>(items.size()));
            for (ComicBasicModel item : items) {
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
            throw new CharacterNotFoundException(e);
        }
    }

    public CharacterSaveResponseDTO save(CharacterSaveRequestDTO requestDTO) {
        return requestDTO.getId() == 0 ? create(requestDTO) : update(requestDTO);
    }

    private CharacterSaveResponseDTO create(CharacterSaveRequestDTO requestDTO) {
        final CharacterFullModel item = template.queryForObject(
                // language=PostgreSQL
                """
                        INSERT INTO characters (nickname, name, description, image, id_comics)
                        VALUES (:nickname, :name, :description, :image, :idComics)
                        RETURNING id, nickname, name, description, image, id_comics
                        """,
                Map.of(
                        "nickname", requestDTO.getNickname(),
                        "name", requestDTO.getName(),
                        "description", requestDTO.getDescription(),
                        "image", requestDTO.getImage(),
                        "idComics", requestDTO.getIdComics()
                ),
                characterFullRowMapper
        );
        final CharacterSaveResponseDTO responseDTO = new CharacterSaveResponseDTO(new CharacterSaveResponseDTO.Character(
                item.getId(),
                item.getNickname(),
                item.getName(),
                item.getDescription(),
                item.getImage(),
                item.getIdComics()
        ));
        return responseDTO;
    }

    private CharacterSaveResponseDTO update(CharacterSaveRequestDTO requestDTO) {
        try {
            final CharacterFullModel item = template.queryForObject(
                    // language=PostgreSQL
                    """
                            UPDATE characters SET nickname = :nickname, name = :name, description = :description,
                            image = :image, id_comics = :idComics
                            WHERE id = :id
                            RETURNING id, nickname, name, description, image, id_comics
                            """,
                    Map.of(
                            "id", requestDTO.getId(),
                            "nickname", requestDTO.getNickname(),
                            "name", requestDTO.getName(),
                            "description", requestDTO.getDescription(),
                            "image", requestDTO.getImage(),
                            "idComics", requestDTO.getIdComics()
                    ),
                    characterFullRowMapper
            );
            final CharacterSaveResponseDTO responseDTO = new CharacterSaveResponseDTO(new CharacterSaveResponseDTO.Character(
                    item.getId(),
                    item.getNickname(),
                    item.getName(),
                    item.getDescription(),
                    item.getImage(),
                    item.getIdComics()
            ));
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new CharacterNotFoundException(e);
        }
    }
}
