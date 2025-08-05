package hei.school.exam.repository.operation;

import hei.school.exam.domaine.model.Beneficiary;
import hei.school.exam.domaine.model.Help;
import hei.school.exam.domaine.model.Payment;
import hei.school.exam.domaine.model.PaymentStatus;
import hei.school.exam.repository.HelpRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HelpOperation implements HelpRepository {
  private final JdbcTemplate jdbcTemplate;

  public HelpOperation(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<Help> findAllOrderByDateDesc() {
    return jdbcTemplate.query(
        "SELECT h.id AS help_id, b.id AS beneficiary_id, b.full_name, b.email, "
            + "p.id AS payment_id, p.method, p.amount, p.created_at, p.status, h.description "
            + "FROM help h "
            + "JOIN beneficiary b ON b.id = h.beneficiary_id "
            + "JOIN payment p ON p.id = h.payment_id "
            + "ORDER BY p.created_at DESC",
        (rs, rowNum) -> mapHelp(rs));
  }

  private Help mapHelp(ResultSet rs) throws SQLException {
    Beneficiary beneficiary =
        new Beneficiary(
            rs.getString("beneficiary_id"), rs.getString("full_name"), rs.getString("email"));

    Payment payment =
        new Payment(
            rs.getString("payment_id"),
            rs.getString("method"),
            rs.getLong("amount"),
            rs.getTimestamp("created_at").toLocalDateTime().toLocalDate(),
            PaymentStatus.valueOf(rs.getString("status")));

    return new Help(rs.getString("help_id"), beneficiary, payment, rs.getString("description"));
  }
}
