package hei.school.exam.service;

import hei.school.exam.domaine.model.PaymentStatus;
import hei.school.exam.repository.PaymentRepository;
import hei.school.exam.vola.VolaPaymentRequest;
import hei.school.exam.vola.VolaPaymentResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class PaymentService {
  private final RestTemplate restTemplate = new RestTemplate();
  private final PaymentRepository paymentRepository;

  @Value("${vola.api.url}")
  private String volaApiUrl;

  @Value("${vola.api.key}")
  private String apiKey;

  public PaymentService(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  public String createPaymentWithVola(String method, Long amount) {
    log.info("Creating payment with method={} and amount={}", method, amount);
    HttpHeaders headers = new HttpHeaders();
    headers.set("x-api-key", apiKey);
    headers.setContentType(MediaType.APPLICATION_JSON);

    VolaPaymentRequest request = new VolaPaymentRequest(method, amount);
    HttpEntity<VolaPaymentRequest> entity = new HttpEntity<>(request, headers);

    ResponseEntity<VolaPaymentResponse> response =
        restTemplate.exchange(
            volaApiUrl + "/payments", HttpMethod.POST, entity, VolaPaymentResponse.class);

    return response.getBody().getId();
  }

  @Scheduled(fixedDelay = 10000)
  public void checkPendingPayments() {
    log.info("Checking pending payments...");
    List<Long> pendingPayments = paymentRepository.findIdsByStatus(PaymentStatus.VERIFYING);

    for (Long paymentId : pendingPayments) {
      String volaStatus = getPaymentStatusFromVola(paymentId);
      if ("SUCCEEDED".equals(volaStatus) || "FAILED".equals(volaStatus)) {
        paymentRepository.updateStatus(paymentId, PaymentStatus.valueOf(volaStatus));
      }
      if ("FAILED".equals(volaStatus)) {
        log.warn("Payment {} failed at Vola", paymentId);
      }
    }
  }

  private String getPaymentStatusFromVola(Long paymentId) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("x-api-key", apiKey);

    HttpEntity<Void> entity = new HttpEntity<>(headers);

    ResponseEntity<VolaPaymentResponse> response =
        restTemplate.exchange(
            volaApiUrl + "/payments/" + paymentId,
            HttpMethod.GET,
            entity,
            VolaPaymentResponse.class);

    return response.getBody().getStatus();
  }
}
