package ex02.edu.school21.sockets.repositories.messageRepositories;

import ex02.edu.school21.sockets.models.Message;
import ex02.edu.school21.sockets.repositories.CrudRepository;
import java.util.List;

public interface MessageRepository extends CrudRepository<Message> {

  List<Message> findLast30(Long chatId);

}
