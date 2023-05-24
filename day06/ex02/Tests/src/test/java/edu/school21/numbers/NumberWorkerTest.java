package edu.school21.numbers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class NumberWorkerTest {
    NumberWorker numberWorker;

    @BeforeEach
    public void init() {
        numberWorker = new NumberWorker();
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43})
    void isPrimeForPrimes(int value) {
        assertTrue(numberWorker.isPrime(value));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 22, 24, 25})
    void isPrimeForNotPrimes(int value) {
        assertFalse(numberWorker.isPrime(value));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 0, -1, -5, -100})
    void isPrimeForIncorrectNumbers(int value) {
        assertThrows(IllegalNumberException.class, () -> numberWorker.isPrime(value));
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    void digitsSum(int input, int expected) {
        assertEquals(numberWorker.digitsSum(input), expected);
    }

}