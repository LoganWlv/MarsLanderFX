package wlv.logan.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {
    private RandomUtils() {
    }

    public static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
