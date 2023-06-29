package edu.school21.services;

import java.util.SplittableRandom;

public class RandomGenerator {

  public static int getRandomArray(int range) {

    return new SplittableRandom().nextInt(range);
  }

}
