package hei.school.exam.service;

import static org.reflections.Reflections.log;

import hei.school.exam.domaine.model.Donation;
import hei.school.exam.domaine.model.Donor;
import hei.school.exam.domaine.model.Payment;
import hei.school.exam.domaine.model.PaymentStatus;
import hei.school.exam.repository.operation.DonationOperation;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DonationService {
  private final DonationOperation donR;

  public DonationService(DonationOperation donR) {
    this.donR = donR;
  }

  public void createDonation(String fullName, String email, String method, Long amount) {
    log.info("Creating donation for {} with email {}", fullName, email);
    Donor donor = new Donor(null, fullName, email);
    Payment payment = new Payment(null, method, amount, LocalDate.now(), PaymentStatus.VERIFYING);

    Donation donation = new Donation(null, donor, payment);
    donR.save(donation);
  }

  public List<Donation> listDonations() {
    if (donR == null) {
      log.error("DonationRepository is not initialized");
      throw new IllegalStateException("DonationRepository is not initialized");
    } else {
      log.info("Fetching all donations ordered by date descending");
      return donR.findAllOrderByDateDesc();
    }
  }
}
