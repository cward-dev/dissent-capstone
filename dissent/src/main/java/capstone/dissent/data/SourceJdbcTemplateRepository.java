package capstone.dissent.data;

import capstone.dissent.models.Source;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        return null;
    }

    @Override
    public List<Source> findAll() {
        return null;
    }

    @Override
    public Source findById(int sourceId) {
        return null;
    }

    @Override
    public boolean edit(Source source) {
        return false;
    }

    @Override
    public boolean deleteById(int sourceId) {
        return false;
    }

    @Override
    public List<Source> getSourceFromLogin(Source userLogin) {
        return null;
    }
}
