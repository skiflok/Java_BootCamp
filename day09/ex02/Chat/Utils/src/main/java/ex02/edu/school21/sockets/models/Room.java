package ex02.edu.school21.sockets.models;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room implements Serializable {

  private Long id;
  private String name;
  private User creator;

}
