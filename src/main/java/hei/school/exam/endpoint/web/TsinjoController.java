package hei.school.exam.endpoint.web;

import hei.school.exam.domaine.model.Donation;
import hei.school.exam.domaine.model.Help;
import hei.school.exam.service.DonationService;
import hei.school.exam.service.HelpService;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TsinjoController {
  private final DonationService donationService;
  private final HelpService helpService;

  public TsinjoController(DonationService donationService, HelpService helpService) {
    this.donationService = donationService;
    this.helpService = helpService;
  }

  @GetMapping("/")
  public String index(Model model) {
    List<Donation> donations = donationService.listDonations();
    List<Help> helps = helpService.listHelps();

    model.addAttribute("donations", donations);
    model.addAttribute("helps", helps);

    return "index";
  }

  @PostMapping("/donate")
  public String donate(
      @RequestParam String fullName,
      @RequestParam String email,
      @RequestParam String method,
      @RequestParam Long amount) {
    donationService.createDonation(fullName, email, method, amount);
    return "redirect:/";
  }
}
