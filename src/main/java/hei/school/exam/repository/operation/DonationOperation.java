package hei.school.exam.repository.operation;

import hei.school.exam.domaine.model.Donation;
import hei.school.exam.domaine.model.Donor;
import hei.school.exam.domaine.model.Payment;
import hei.school.exam.domaine.model.PaymentStatus;
import hei.school.exam.repository.DonationRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DonationOperation implements DonationRepository {
  private final JdbcTemplate jdbcTemplate;

  public DonationOperation(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void save(Donation donation) {
    Long donorId =
        jdbcTemplate.queryForObject(
            "INSERT INTO donor(full_name,email) VALUES (?,?) RETURNING id",
            Long.class,
            donation.getDonor().getFullName(),
            donation.getDonor().getEmail());

    Long paymentId =
        jdbcTemplate.queryForObject(
            "INSERT INTO payment(method,amount,created_at,status) VALUES (?,?,?,?) RETURNING id",
            Long.class,
            donation.getPayment().getMethod(),
            donation.getPayment().getAmount(),
            donation.getPayment().getCreatedAt(),
            donation.getPayment().getStatus().name());

    jdbcTemplate.update(
        "INSERT INTO donation(donor_id,payment_id) VALUES (?,?)", donorId, paymentId);
  }

  @Override
  public List<Donation> findAllOrderByDateDesc() {
    return jdbcTemplate.query(
        "SELECT d.id AS donation_id, dn.id AS donor_id, dn.full_name, dn.email, "
            + "p.id AS payment_id, p.method, p.amount, p.created_at, p.status "
            + "FROM donation d "
            + "JOIN donor dn ON dn.id = d.donor_id "
            + "JOIN payment p ON p.id = d.payment_id "
            + "ORDER BY p.created_at DESC",
        (rs, rowNum) -> mapDonation(rs));
  }

  private Donation mapDonation(ResultSet rs) throws SQLException {
    Donor donor =
        new Donor(rs.getString("donor_id"), rs.getString("full_name"), rs.getString("email"));

    Payment payment =
        new Payment(
            rs.getString("payment_id"),
            rs.getString("method"),
            rs.getLong("amount"),
            rs.getTimestamp("created_at").toLocalDateTime().toLocalDate(),
            PaymentStatus.valueOf(rs.getString("status")));

    return new Donation(rs.getString("donation_id"), donor, payment);
  }
}
