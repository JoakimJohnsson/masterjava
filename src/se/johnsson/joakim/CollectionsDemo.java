package se.johnsson.joakim;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;

public class CollectionsDemo {
    private static final List<Integer> TEST_NUMBERS = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5, 8, 9, 7, 9, 3, 2, 3, 8, 4, 6, 2, 6, 4, 3, 3);

    IntUnaryOperator doubler = n -> n * 2;
    IntUnaryOperator doublerWithSleep100 = n -> {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return n * 2;
    };
    IntUnaryOperator doublerWithSleep1 = n -> {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return n * 2;
    };

    void printNums(IntUnaryOperator function) {
        System.out.println(TEST_NUMBERS.stream()
                .map(function::applyAsInt)
                .collect(Collectors.toList()));
    }

    void printNumsParallel(IntUnaryOperator function) {
        System.out.println(TEST_NUMBERS.parallelStream()
                .map(function::applyAsInt)
                .collect(Collectors.toList()));
    }
}
