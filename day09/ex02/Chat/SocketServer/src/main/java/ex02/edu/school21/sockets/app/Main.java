package ex02.edu.school21.sockets.app;


import ex02.edu.school21.sockets.server.Server;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {

    if (args.length != 1) {
      logger.error("Неверное количество параметров для запуска сервера");
      System.exit(-1);
    }

    String[] params = args[0].split("=");

    logger.debug("String[] args = {}", Arrays.toString(params));

    if (!"--server-port".equals(params[0])) {
      logger.error("Неверные параметры запуска");
      System.exit(-1);
    }

    try {
      int port = Integer.parseInt(params[1]);
      Server server = new Server(port);
      server.start();

    } catch (NumberFormatException e) {
      logger.error("Введите корректный порт");
    }

  }
}

//  После того, как вы реализовали скелет приложения, следует обеспечить многопользовательский обмен сообщениями.
//    Необходимо модифицировать приложение таким образом, чтобы оно поддерживало следующий жизненный цикл пользователя в чате:
//    1. Регистрация
//    2. Вход (в случае, если пользователь не обнаружен - обрывать соединение).
//    3. Отправка сообщения (каждый пользователь, подключенный к серверу, должен
//    получить данное сообщение)
//    4. Выход
//    Пример работы приложения со стороны клиента:
//    Hello from Server!
//    > signIn
//    Enter username:
//    > Marsel
//    Enter password:
//    > qwerty007
//    Start messaging
//    > Hello!
//    Marsel: Hello!
//    NotMarsel: Bye!
//    > Exit
//    You have left the chat.
//    Также каждое сообщение должно быть сохранено в базе данных и иметь следующую информацию:
//    ● Отправитель
//    ● Текст сообщения
//    ● Время отправки
//    Примечание:
//    ● Для полноценного тестирования необходимо запустить несколько jar-файлов клиентского приложения.