package edu.school21.models;

import lombok.Data;

@Data
public class Statistics {
  private Long id;
  private final Integer playerId;
  private final Integer playerShotsCount;
  private final Integer playerHitCount;
  private final Integer playerBoboCount;

  private final Integer enemyId;
  private final Integer enemyShotsCount;
  private final Integer enemyHitCount;
  private final Integer enemyBoboCount;
}
