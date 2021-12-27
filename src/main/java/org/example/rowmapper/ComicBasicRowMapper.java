package org.example.rowmapper;

import org.example.model.ComicBasicModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ComicBasicRowMapper implements RowMapper<ComicBasicModel> {
    @Override
    public ComicBasicModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ComicBasicModel(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getInt("issue_number"),
                (String[]) rs.getArray("writer").getArray(),
                rs.getString("image")
        );
    }
}
