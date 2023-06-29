module edu.school.view {
  requires javafx.controls;
  requires javafx.fxml;
  requires lombok;
  requires javafx.graphics;
  requires javafx.base;
  requires org.slf4j;
  requires com.fasterxml.jackson.databind;

  opens edu.school21.view to javafx.fxml;
  exports edu.school21.view;
  exports edu.school21.controllers;
  opens edu.school21.controllers to javafx.fxml;
  opens edu.school21.models;
}