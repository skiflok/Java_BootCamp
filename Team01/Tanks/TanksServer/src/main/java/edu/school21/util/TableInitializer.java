package edu.school21.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@RequiredArgsConstructor
@Setter
public class TableInitializer {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private String pathSchema;
  private String pathData;

  public void initializeTablesWithData() throws SQLException, IOException {
    String schemaQuery = Files.lines(Paths.get(pathSchema)).collect(Collectors.joining("\n"));
    String dataQuery = Files.lines(Paths.get(pathData)).collect(Collectors.joining("\n"));

    namedParameterJdbcTemplate.update(schemaQuery, new EmptySqlParameterSource());
    namedParameterJdbcTemplate.update(dataQuery, new EmptySqlParameterSource());
  }
}
