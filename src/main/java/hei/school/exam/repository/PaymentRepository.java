package hei.school.exam.repository;

import hei.school.exam.domaine.model.PaymentStatus;
import java.util.List;

public interface PaymentRepository {
  List<Long> findIdsByStatus(PaymentStatus status);

  void updateStatus(Long paymentId, PaymentStatus newStatus);
}
