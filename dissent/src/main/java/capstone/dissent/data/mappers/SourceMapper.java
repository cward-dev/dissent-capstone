package capstone.dissent.data.mappers;

import capstone.dissent.models.Source;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SourceMapper implements RowMapper<Source> {

    @Override
    public Source mapRow(ResultSet resultSet, int i) throws SQLException {

        Source source = new Source();

        source.setSourceId(resultSet.getString("source_id"));
        source.setSourceName(resultSet.getString("source_name"));
        source.setWebsiteUrl(resultSet.getString("website_url"));
        source.setDescription(resultSet.getString("description"));

        return source;
    }
}