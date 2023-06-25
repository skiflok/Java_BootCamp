package ex02.edu.school21.sockets.models;

public enum MessageType {

  SUCCESS,
  NAME_REQUEST, // запрос имени
  PASSWORD_REQUEST, // запрос пароля
  USER_NAME, // имя пользователя
  PASSWORD, // пароль
  SIGN_UP_SUCCESS, // пользователь зарегистрирован
  SIGN_IN_SUCCESS, // пользователь залогинился
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