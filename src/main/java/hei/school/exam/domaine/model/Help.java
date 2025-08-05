package hei.school.exam.domaine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Help {
  private String id;
  private Beneficiary beneficiary;
  private Payment payment;
  private String description;
}
