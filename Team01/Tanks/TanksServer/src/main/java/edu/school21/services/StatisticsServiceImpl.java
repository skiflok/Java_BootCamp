package edu.school21.services;

import edu.school21.repositories.StatisticsRepository;
import edu.school21.models.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatisticsServiceImpl implements StatisticsService {

  @Autowired
  private StatisticsRepository repository;

  @Override
  public void saveStatistic(Statistics entity) {
    repository.save(entity);
  }

  @Override
  public void updateStatistic(Statistics entity) {
    repository.update(entity);
  }
}
