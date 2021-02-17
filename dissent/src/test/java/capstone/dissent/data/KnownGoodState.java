package capstone.dissent.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
public class KnownGoodState {

    @Autowired
    JdbcTemplate jdbcTemplate;

    static boolean hasRun = true;

    void set() {
        if (!hasRun) {
            hasRun = true;
            jdbcTemplate.update("call set_known_good_state();");
        }
    }

}
