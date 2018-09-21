package com.shadowings.simplelocator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SingletonTest {
    @Test
    public void simpleLocator_isSingleton() {
        SimpleLocator one = SimpleLocator.getInstance();
        SimpleLocator two = SimpleLocator.getInstance();
        assertEquals(one, two);
    }
}
