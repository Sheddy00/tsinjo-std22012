package hei.school.exam.vola;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VolaPaymentRequest {
  private String method;
  private Long amount;
}
