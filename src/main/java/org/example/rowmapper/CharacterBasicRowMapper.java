package org.example.rowmapper;

import org.example.model.CharacterBasicModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CharacterBasicRowMapper implements RowMapper<CharacterBasicModel> {
    @Override
    public CharacterBasicModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CharacterBasicModel(
                rs.getLong("id"),
                rs.getString("nickname"),
                rs.getString("name"),
                rs.getString("image")
        );
    }
}
