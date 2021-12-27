package org.example.rowmapper;

import org.example.model.CharacterFullModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CharacterFullRowMapper implements RowMapper<CharacterFullModel> {
    @Override
    public CharacterFullModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CharacterFullModel(
                rs.getLong("id"),
                rs.getString("nickname"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("image"),
                ((Integer[]) rs.getArray("id_comics").getArray())
        );
    }
}
