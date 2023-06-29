package edu.school21.services.move_game_objects_service.auto_movable;

import edu.school21.models.Field;
import edu.school21.models.GameObject;
import edu.school21.models.Position;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import lombok.Data;

@Data
public class MoveToObjectBSFAlgorithm implements AutoMovableToTarget {

  private final Field field;

  @Override
  public MovementDirection moveToTarget(GameObject who, GameObject target) {
    return calculateMovementDirection(who, target);
  }

  private MovementDirection calculateMovementDirection(GameObject who, GameObject target) {
    int startY = who.getPosition().getY();
    int startX = who.getPosition().getX();
    int targetY = target.getPosition().getY();
    int targetX = target.getPosition().getX();
    int scale = field.getScale();
    boolean[][] visited = new boolean[scale][scale];
    Queue<Position> queue = new LinkedList<>();
    int[][] distances = new int[scale][scale];
    int[][] prevY = new int[scale][scale];
    int[][] prevX = new int[scale][scale];

    for (int i = 0; i < scale; i++) {
      Arrays.fill(distances[i], Integer.MAX_VALUE);
    }

    queue.add(new Position(startX, startY));
    visited[startY][startX] = true;
    distances[startY][startX] = 0;

    while (!queue.isEmpty()) {
      Position current = queue.poll();
      int y = current.getY();
      int x = current.getX();

      if (y == targetY && x == targetX) {
        // Построение пути от врага до игрока
        List<MovementDirection> path = buildPath(startY, startX, targetY, targetX, prevY, prevX);
        if (!path.isEmpty()) {
          // Возвращаем первое направление в пути
          return path.get(0);
        }
      }

      // Перебор всех соседних клеток (вверх, вниз, влево, вправо)
      for (MovementDirection direction : MovementDirection.values()) {
        int newY = y;
        int newX = x;
        switch (direction) {
          case UP:
            newY--;
            break;
          case DOWN:
            newY++;
            break;
          case LEFT:
            newX--;
            break;
          case RIGHT:
            newX++;
            break;
        }

        // Проверка границ поля и доступности клетки для перемещения
        if (isValidMove(newY, newX)
            && !visited[newY][newX]
            && (field.isEmptyField(newY, newX)
            || isTarget(newY, newX, target))) {
          visited[newY][newX] = true;
          queue.add(new Position(newX, newY));
          distances[newY][newX] = distances[y][x] + 1;
          prevY[newY][newX] = y;
          prevX[newY][newX] = x;
        }
      }
    }

    // Целевая клетка не достижима

    return null;
  }

  private List<MovementDirection> buildPath(int startY, int startX, int targetY, int targetX,
      int[][] prevY, int[][] prevX) {
    List<MovementDirection> path = new ArrayList<>();
    int currentY = targetY;
    int currentX = targetX;

    while (currentY != startY || currentX != startX) {
      int prevYVal = prevY[currentY][currentX];
      int prevXVal = prevX[currentY][currentX];
      int deltaY = currentY - prevYVal;
      int deltaX = currentX - prevXVal;

      if (deltaY == -1 && deltaX == 0) {
        path.add(0, MovementDirection.UP);
      } else if (deltaY == 1 && deltaX == 0) {
        path.add(0, MovementDirection.DOWN);
      } else if (deltaY == 0 && deltaX == -1) {
        path.add(0, MovementDirection.LEFT);
      } else if (deltaY == 0 && deltaX == 1) {
        path.add(0, MovementDirection.RIGHT);
      }

      currentY = prevYVal;
      currentX = prevXVal;
    }

    return path;
  }

  private boolean isValidMove(int y, int x) {
    int scale = field.getScale();
    return y >= 0 && y < scale && x >= 0 && x < scale;
  }

  private boolean isTarget(int y, int x, GameObject target) {
    return field.getGameObject(y, x).getGameObjectTypes() == target.getGameObjectTypes();
  }

}