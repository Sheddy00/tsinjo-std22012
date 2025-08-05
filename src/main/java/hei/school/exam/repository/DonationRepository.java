package hei.school.exam.repository;

import hei.school.exam.domaine.model.Donation;
import java.util.List;

public interface DonationRepository {
  void save(Donation donation);

  List<Donation> findAllOrderByDateDesc();
}
