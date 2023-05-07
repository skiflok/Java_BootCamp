package ex02;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class Calculator implements Callable<Integer> {

    private final int [] arr;

    public Calculator(int[] arr) {
        this.arr = arr;
    }

    @Override
    public Integer call() throws Exception {
        return Arrays.stream(arr).sum();
    }
}
