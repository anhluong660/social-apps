package com.swordfish.utils.common;

import java.util.List;
import java.util.Random;

public class RandomUtil {

    private static final Random rand = new Random();

    public static int randomBetween(int min, int max) {
        return min + rand.nextInt(max - min + 1);
    }

    public static <T> T randomInList(List<T> list) {
        if (list.isEmpty()) {
            return null;
        }

        return list.get(rand.nextInt(list.size()));
    }
}
