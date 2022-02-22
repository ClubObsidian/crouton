package com.clubobsidian.crouton.test;

import com.clubobsidian.crouton.Crouton;
import com.clubobsidian.crouton.wrapper.JobWrapper;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCroutonJava {

    @Test
    public void testSleep() {
        AtomicInteger count = new AtomicInteger();
        JobWrapper job = Crouton.async(() -> {
            Crouton.sleep(1000);
            count.incrementAndGet();
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Crouton.async(job::stop);
        assertTrue(count.get() == 0);
    }
}
