package hei.school.exam.repository;

import hei.school.exam.domaine.model.Help;
import java.util.List;

public interface HelpRepository {
  List<Help> findAllOrderByDateDesc();
}
