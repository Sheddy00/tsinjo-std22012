package hei.school.exam.repository.operation;

import hei.school.exam.domaine.model.PaymentStatus;
import hei.school.exam.repository.PaymentRepository;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentOperation implements PaymentRepository {

  private final JdbcTemplate jdbcTemplate;

  public PaymentOperation(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<Long> findIdsByStatus(PaymentStatus status) {
    return jdbcTemplate.queryForList(
        "SELECT id FROM payment WHERE status = ?", Long.class, status.name());
  }

  @Override
  public void updateStatus(Long paymentId, PaymentStatus newStatus) {
    jdbcTemplate.update("UPDATE payment SET status = ? WHERE id = ?", newStatus.name(), paymentId);
  }
}
