package hei.school.exam.service;

import hei.school.exam.domaine.model.Donation;
import hei.school.exam.domaine.model.Donor;
import hei.school.exam.domaine.model.Payment;
import hei.school.exam.domaine.model.PaymentStatus;
import hei.school.exam.repository.DonationRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DonationService {
  private final DonationRepository donR;

  public DonationService(DonationRepository donR) {
    this.donR = donR;
  }

  public void createDonation(String fullName, String email, String method, Long amount) {
    Donor donor = new Donor(null, fullName, email);
    Payment payment = new Payment(null, method, amount, LocalDate.now(), PaymentStatus.VERIFYING);

    Donation donation = new Donation(null, donor, payment);
    donR.save(donation);

    // Ici on pourra appeler Vola pour vérifier le paiement en async
  }

  public List<Donation> listDonations() {
    return donR.findAllOrderByDateDesc();
  }
}
