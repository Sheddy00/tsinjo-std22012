package hei.school.exam.service;

import hei.school.exam.domaine.model.Help;
import hei.school.exam.repository.HelpRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HelpService {
  private final HelpRepository helpRepository;

  public HelpService(HelpRepository helpRepository) {
    this.helpRepository = helpRepository;
  }

  public List<Help> listHelps() {
    return helpRepository.findAllOrderByDateDesc();
  }
}
