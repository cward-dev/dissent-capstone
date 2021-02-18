package capstone.dissent.data;

import capstone.dissent.data.mappers.SourceMapper;
import capstone.dissent.models.Source;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class SourceJdbcTemplateRepository implements SourceRepository {

    private final JdbcTemplate jdbcTemplate;

    public SourceJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // methods
    @Override
    public Source add(Source source) {
        final String sql = "insert into source (source_id, source_name, website_url, `description`) "
                            + " values(?,?,?,?)";
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.NO_GENERATED_KEYS);
            ps.setString(1,source.getSourceId());
            ps.setString(2,source.getSourceName());
            ps.setString(3, source.getWebsiteUrl());
            ps.setString(4,source.getDescription());
            return ps;
        });
        if(rowsAffected<=0){
            return null;
        }
        return source;
    }

    @Override
    public List<Source> findAll() {
        String sql = "Select * from source;";

        return jdbcTemplate.query(sql, new SourceMapper());
    }

    @Override
    public Source findById(String sourceId) {
        final String sql = "select * from source where source_id =?; ";

        Source source = jdbcTemplate.query(sql, new SourceMapper(), sourceId)
                .stream().findFirst().orElse(null);
        return source;
    }

    @Override
    public boolean edit(Source source) {
        final String sql = "update source set "
                            + "source_name = ?, "
                            + "website_url = ?, "
                            + "description = ? "
                            + "where source_id = ?;";
        return jdbcTemplate.update(sql,
                source.getSourceName(),
                source.getWebsiteUrl(),
                source.getDescription(),
                source.getSourceId())>0;

    }

    @Override
    public boolean deleteById(String sourceId) {
        return jdbcTemplate.update("delete from source where source_id = ?;",sourceId)>0;
    }

}
