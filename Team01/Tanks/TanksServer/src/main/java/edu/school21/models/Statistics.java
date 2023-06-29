package edu.school21.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class Statistics {

  private Long id;
  private Integer playerId;
  private Integer playerShotsCount;
  private Integer playerHitCount;
  private Integer playerBoboCount;

  private Integer enemyId;
  private Integer enemyShotsCount;
  private Integer enemyHitCount;
  private Integer enemyBoboCount;
}
