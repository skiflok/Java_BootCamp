package edu.school21.repositories;

import edu.school21.models.Statistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatisticsRepositoryJdbcTemplateImpl implements StatisticsRepository {

  @Autowired
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public Optional<Statistics> findById(Long id) {
    String query = "select * from game.statistics where id = :id;";
    MapSqlParameterSource paramMap = new MapSqlParameterSource();
    paramMap.addValue("id", id);
    return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query, paramMap,
        new BeanPropertyRowMapper<>(Statistics.class)));
  }

  @Override
  public List<Statistics> findAll() {
    return namedParameterJdbcTemplate.query("select * from game.statistics;",
        new BeanPropertyRowMapper<>(Statistics.class));
  }

  @Override
  public void save(Statistics entity) {
    String query = "insert into game.statistics "
        + "(playerId, playerShotsCount, playerHitCount, playerBoboCount, enemyId, enemyShotsCount, enemyHitCount, enemyBoboCount) "
        + "values\n"
        + "(:playerId, :playerShotsCount, :playerHitCount, :playerBoboCount, :enemyId, :enemyShotsCount, :enemyHitCount, :enemyBoboCount);";
    MapSqlParameterSource paramMap = new MapSqlParameterSource();
    paramMap.addValue("playerId", entity.getPlayerId());
    paramMap.addValue("playerShotsCount", entity.getPlayerShotsCount());
    paramMap.addValue("playerHitCount", entity.getPlayerHitCount());
    paramMap.addValue("playerBoboCount", entity.getPlayerBoboCount());

    paramMap.addValue("enemyId", entity.getEnemyId());
    paramMap.addValue("enemyShotsCount", entity.getEnemyShotsCount());
    paramMap.addValue("enemyHitCount", entity.getEnemyHitCount());
    paramMap.addValue("enemyBoboCount", entity.getEnemyBoboCount());

    KeyHolder keyHolder = new GeneratedKeyHolder();
    namedParameterJdbcTemplate.update(query, paramMap, keyHolder, new String[]{"id"});
    entity.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
  }

  @Override
  public void update(Statistics entity) {
//    String query = "update game.statistics set user_id = :user_id, hit_count = :hit_count, miss_count = :miss_count where id = :id";
//    MapSqlParameterSource paramMap = new MapSqlParameterSource();
//    paramMap.addValue("user_id", entity.getUserId());
//    paramMap.addValue("hit_count", entity.getHitCount());
//    paramMap.addValue("miss_count", entity.getMissCount());
//    paramMap.addValue("id", entity.getId());
//    namedParameterJdbcTemplate.update(query, paramMap);
  }

  @Override
  public void delete(Long id) {
    String query = "delete from game.statistics where id = :id;";
    Map<String, Long> map = new HashMap<>();
    map.put("id", id);
    namedParameterJdbcTemplate.update(query, map);
  }
}
