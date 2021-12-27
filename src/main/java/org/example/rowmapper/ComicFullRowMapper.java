package org.example.rowmapper;

import org.example.model.ComicFullModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ComicFullRowMapper implements RowMapper<ComicFullModel> {
    @Override
    public ComicFullModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ComicFullModel(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getInt("issue_number"),
                (String[]) rs.getArray("writer").getArray(),
                rs.getString("published"),
                rs.getString("description"),
                rs.getString("image"),
                ((Integer[]) rs.getArray("id_characters").getArray())
        );
    }
}
