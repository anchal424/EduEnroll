package com.eduenroll.util;

public class IdGenerator {

    public static String generate(String prefix) {
        long value = System.currentTimeMillis() % 1000000;
        return prefix + value;
    }
}