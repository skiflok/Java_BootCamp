package ex01.edu.school21.sockets.models;

public enum MessageType {
  NAME_REQUEST, // запрос имени
  USER_NAME, // имя пользователя
  NAME_ACCEPTED, // имя принято
  TEXT, // текстовое сообщение
  USER_ADDED, // пользователь добавлен
  USER_REMOVED, // пользователь удален
  SIGNUP, // регистрация
  LOGIN, // вход
  EXIT, // выход
  MENU, // сообщение меню
  MENU_REQUEST, // запрос действия пользователя
  MENU_RESPONSE // ответ пользователя
}