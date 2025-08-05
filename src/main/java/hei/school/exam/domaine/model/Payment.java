package hei.school.exam.domaine.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Payment {
  private String id;
  private String method;
  private Long amount;
  private LocalDate createdAt;
  private PaymentStatus status;
}
