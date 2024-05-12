package com.banking.system.Outils;
import java.util.concurrent.atomic.AtomicLong;


public class AutoValueGenerator {

    private static final AtomicLong counter = new AtomicLong(1); // Initial value can be adjusted as needed

    public static long generatedAutoValue() {
        return counter.getAndIncrement();
    }


}
