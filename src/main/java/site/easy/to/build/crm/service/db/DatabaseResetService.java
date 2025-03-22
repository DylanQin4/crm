package site.easy.to.build.crm.service.db;

import jakarta.transaction.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseResetService {
    private final JdbcTemplate jdbcTemplate;

    public DatabaseResetService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void resetDatabase() {
        String sql = """
            SET FOREIGN_KEY_CHECKS = 0;
            TRUNCATE TABLE file;
            TRUNCATE TABLE lead_settings;
            TRUNCATE TABLE lead_action;
            TRUNCATE TABLE google_drive_file;
            TRUNCATE TABLE email_template;
            TRUNCATE TABLE customer_login_info;
            TRUNCATE TABLE customer;
            TRUNCATE TABLE ticket_settings;
            TRUNCATE TABLE trigger_lead;
            TRUNCATE TABLE trigger_ticket;
            TRUNCATE TABLE trigger_contract;
            TRUNCATE TABLE contract_settings;
            TRUNCATE TABLE employee;
            SET FOREIGN_KEY_CHECKS = 1;
        """;

        for (String query : sql.split(";")) {
            if (!query.trim().isEmpty()) {
                jdbcTemplate.execute(query);
            }
        }
    }
}
