package edu.school21.sockets.server;

import edu.school21.sockets.application_utils.ConsoleHelper;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  public static void main(String[] args) {

    ConsoleHelper.writeMessage("Введите порт сервера:");
    int port = ConsoleHelper.readInt();

    try (ServerSocket serverSocket = new ServerSocket(port)) {
      ConsoleHelper.writeMessage("Чат сервер запущен.");
      while (true) {
        Socket socket = serverSocket.accept();
        new ServerHandler(socket).start();
      }
    } catch (Exception e) {
//      logger.log(Level.SEVERE, "Произошла ошибка при запуске или работе сервера {0}", e.getMessage());
      ConsoleHelper.writeMessage("Произошла ошибка при запуске или работе сервера.");
    }


  }

}
