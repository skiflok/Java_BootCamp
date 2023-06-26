package ex02.edu.school21.sockets.server;

import static ex02.edu.school21.sockets.models.MessageType.*;

import ex02.edu.school21.sockets.models.Connection;
import ex02.edu.school21.sockets.models.Message;
import ex02.edu.school21.sockets.models.Room;
import ex02.edu.school21.sockets.models.User;
import ex02.edu.school21.sockets.repositories.messageRepositories.MessageRepository;
import ex02.edu.school21.sockets.repositories.roomRepositories.RoomRepository;
import ex02.edu.school21.sockets.repositories.userRepositories.UsersRepository;
import ex02.edu.school21.sockets.services.ActiveConnectionStorage;
import ex02.edu.school21.sockets.services.UsersService;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ServerHandler implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(Server.class);

  private final Socket socket;
  private Connection connection;
  private final UsersService usersService;
  private final UsersRepository usersRepository;

  private final MessageRepository messageRepository;

  private final RoomRepository roomRepository;
  private final PasswordEncoder passwordEncoder;
  private final ActiveConnectionStorage activeConnectionStorage;

  private User user;
  private Room room;

  public ServerHandler(
      Socket socket,
      UsersService usersService,
      UsersRepository usersRepository,
      MessageRepository messageRepository,
      RoomRepository roomRepository, PasswordEncoder passwordEncoder,
      ActiveConnectionStorage activeConnectionStorage) {
    this.socket = socket;
    this.usersService = usersService;
    this.usersRepository = usersRepository;
    this.messageRepository = messageRepository;
    this.roomRepository = roomRepository;
    this.passwordEncoder = passwordEncoder;
    this.activeConnectionStorage = activeConnectionStorage;
  }


  @SneakyThrows
  @Override
  public void run() {
    connection = new Connection(socket);
    logger.info("Подключение клиента с удаленного адреса {}", connection.getRemoteSocketAddress());
    menu();
    exit();
  }


  private void menu() throws IOException, ClassNotFoundException {
    logger.info("");
    Message msg;
    while (true) {
      connection.send(new Message(MENU, "Hello from Server!"
          + "\nAvailable commands:"
          + "\n1. signIn"
          + "\n2. signUp"
          + "\n3. exit"));
      msg = connection.receive();

      switch (msg.getMessage()) {
        case "1":
          if (newSignIn()) {
            roomMenu();
          } else {
            exit();
          }
          break;
        case "2":
          newSignUp();
          break;
        case "3":
          exit();
          return;
        default:
          break;
      }
    }
  }

  private void exit() throws IOException {
    logger.info("");
    if (Objects.nonNull(user)) {
      activeConnectionStorage.removeUser(user.getName());
    }
    connection.close();
  }

  private boolean newSignIn() throws IOException, ClassNotFoundException {
    logger.info("");
    String userName;
    String password;
    Message incomeMsg;
    User user;
    boolean passCorrect;

    connection.send(new Message(NAME_REQUEST, "Enter username:"));
    incomeMsg = connection.receive();
    userName = incomeMsg.getMessage();
    logger.info("userName {}", userName);

    connection.send(new Message(PASSWORD_REQUEST, "Enter password:"));
    incomeMsg = connection.receive();
    password = incomeMsg.getMessage();
    logger.info("password {}", password);

    if (userName == null || userName.isEmpty() || password == null || password.isEmpty()) {
      connection.send(new Message(ERROR, "Incorrect input"));
      logger.info("null or empty");
      exit();
      return false;
    }

    user = new User(null, userName, password);
    Optional<User> optionalUserFromDB = usersRepository.findByName(userName);

    if (!optionalUserFromDB.isPresent()) {
      logger.info("Неккоректный ввод userName");
      connection.send(new Message(EXIT, "Юзер не существует"));
      exit();
      return false;
    }

    passCorrect = passwordEncoder.matches(password, optionalUserFromDB.get().getPassword());
    logger.info("Password match = " + passCorrect);
    logger.info("User income {}", user);
    logger.info("User DB {}", optionalUserFromDB.get());

    if (passCorrect) {
      connection.send(new Message(SIGN_IN_SUCCESS, "Log in Successful!"));
      this.user = user;
      this.user.setId(optionalUserFromDB.get().getId());
      activeConnectionStorage.addUser(userName, connection);
      logger.info("пользователь {} подключился к чату с IP {}", userName,
          connection.getRemoteSocketAddress());
      return true;
    }
    logger.info("Неверный пароль");
    connection.send(new Message(EXIT, "Неверный пароль"));
    exit();
    return false;
  }

  private void newSignUp() throws IOException, ClassNotFoundException {
    logger.info("");
    String userName;
    String password;
    Message incomeMsg;
    User user;

    connection.send(new Message(NAME_REQUEST, "Enter username:"));
    incomeMsg = connection.receive();
    userName = incomeMsg.getMessage();
    logger.info("userName {}", userName);

    connection.send(new Message(PASSWORD_REQUEST, "Enter password:"));
    incomeMsg = connection.receive();
    password = incomeMsg.getMessage();
    logger.info("password {}", password);

    if (userName == null || userName.isEmpty() || password == null || password.isEmpty()) {
      logger.info("null or empty");
      connection.send(new Message(ERROR, "Incorrect input"));
      return;
    }

    user = new User(null, userName, password);

    try {
      logger.info(user.toString());
      logger.info(usersService.toString());
      usersService.signUp(user);
    } catch (IllegalArgumentException e) {
      logger.info(e.getMessage());
      connection.send(new Message(ERROR, "is already register!"));
      return;
    }

    connection.send(new Message(SIGN_UP_SUCCESS, "Successful!"));
    logger.info("пользователь {} зарегистрировался с IP {}", userName,
        connection.getRemoteSocketAddress());
  }

  private void roomMenu() throws IOException, ClassNotFoundException {
    logger.info("");
    Message msg;
    while (true) {
      connection.send(new Message(TEXT, "Room menu"
          + "\n 1. Create room"
          + "\n 2. Choose room"
          + "\n 3. Exit"));
      msg = connection.receive();

      switch (msg.getMessage()) {
        case "1":
          createRoom();
          break;
        case "2":
          chooseRoom();
          break;
        case "3":
          exit();
          return;
        default:
          break;
      }
    }
  }

  private void createRoom() throws IOException, ClassNotFoundException {
    Message msg;
    logger.info("");
    connection.send(new Message(TEXT, "введите название комнаты"));
    msg = connection.receive();
    roomRepository.save(new Room(null, msg.getMessage(), user));
    // todo проверки
  }

  private void chooseRoom() throws IOException, ClassNotFoundException {
    logger.info("");
    Message msg;
    logger.info("");
    List<Room> rooms = roomRepository.findAll();
    connection.send(new Message(TEXT, rooms.stream()
        .map(room -> room.getId() + ". " + room.getName())
        .collect(Collectors.joining("\n"))));
    msg = connection.receive();
    Optional<Room> room = rooms.stream()
        .filter(r -> r.getId().toString().equals(msg.getMessage()))
        .findFirst();
    if (room.isPresent()) {
      this.room = room.get();
      connection.setRoom(this.room);
      connection.send(new Message(SUCCESS));
      startChatting();
    }

    // todo проверки
  }

  public void sendBroadcastMessage(Message message) {
    for (Connection connection : activeConnectionStorage.getConnectionList()) {
      try {
        logger.info("this.room {} name {}", room, room.getName());
        logger.info("connection.room {} name {}", connection.getRoom(), connection.getRoom().getName());
        if (this.room.getName().equals(connection.getRoom().getName())) {
          connection.send(message);
        }
      } catch (IOException e) {
        logger.info("Не смогли отправить сообщение {}", e.getMessage());
      }
    }
  }

  private void startChatting() throws IOException, ClassNotFoundException {
    Message msg;
    while (true) {
      logger.info("");

      msg = connection.receive();
      if (msg.getMessageType() == EXIT) {
        logger.info("Пользователь {} c IP {} отключился", user.getName(),
            connection.getRemoteSocketAddress());
        break;
      }
      if (msg.getMessageType() == TEXT) {
        msg.setUser(this.user);
        msg.setRoom(this.room);
        msg.setLocalDateTime(LocalDateTime.now());
        sendBroadcastMessage(msg);
        logger.info("this.user = {}", user);
        logger.info("LocalDateTime.now() = {}", LocalDateTime.now());
        logger.info("msg.getUser.getId = {}", msg.getUser().getId());
        messageRepository.save(msg);
      }

    }

  }
}
