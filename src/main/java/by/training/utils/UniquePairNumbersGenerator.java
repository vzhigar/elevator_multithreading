package by.training.utils;

import by.training.constants.Constants;

import java.util.Random;

public class UniquePairNumbersGenerator {
    private final int minNumber;
    private final int maxNumber;

    public UniquePairNumbersGenerator(final int maxNumber) {
        minNumber = Constants.MIN_FLOOR_NUMBER;
        this.maxNumber = maxNumber;
    }

    public int[] getPair() {
        int[] pair = new int[2];
        Random random = new Random();
        int a = random.nextInt(maxNumber) + minNumber;
        int b;
        do {
            b = random.nextInt(maxNumber) + minNumber;
        } while (a == b);
        pair[0] = a;
        pair[1] = b;
        return pair;
    }
}
