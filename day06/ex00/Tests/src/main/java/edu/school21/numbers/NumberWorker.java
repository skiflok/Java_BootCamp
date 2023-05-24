package edu.school21.numbers;

public class NumberWorker {

    public boolean isPrime(int number) {
        if (number < 2) throw new IllegalNumberException("Illegal number");
        for (int i = 2; i <= Math.sqrt(number); ++i) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public int digitsSum(int number) {
        number = Math.abs(number);
        int digitsSum = 0;
        while (number > 0) {
            digitsSum += number % 10;
            number /= 10;
        }
        return digitsSum;
    }
}
