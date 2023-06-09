package edu.school21.ex01.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
  private long id;
  private String email;
}
