package hei.school.exam.domaine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Donation {
  private String id;
  private Donor donor;
  private Payment payment;
}
