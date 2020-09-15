package io.ace.nordclient.guinew;

import java.util.Random;

public class Util {
	
    private static Random RANDOM = new Random();

    public static int random(int min, int max)
    {
        if (max <= min) {
            return min;
        }
        return RANDOM.nextInt(max - min) + min;
    }
}