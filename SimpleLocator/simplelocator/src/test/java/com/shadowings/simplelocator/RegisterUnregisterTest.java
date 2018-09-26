package com.shadowings.simplelocator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegisterUnregisterTest {

    @Test
    public void class_unregistered()
    {
        SimpleLocator.clear();
        assertFalse(SimpleLocator.isRegistered(Object.class));
    }

    @Test
    public void base_isRegistered() {
        SimpleLocator.clear();
        SimpleLocator.register(Object.class, Object::new);
        assertTrue(SimpleLocator.isRegistered(Object.class));
    }

    @Test
    public void singleton_isRegistered() {
        SimpleLocator.clear();
        SimpleLocator.registerSingleton(Object.class, Object::new);
        assertTrue(SimpleLocator.isRegistered(Object.class));
    }

    @Test
    public void simpleLocator_baseUnregister() {
        SimpleLocator.clear();
        SimpleLocator.register(Object.class, Object::new);
        SimpleLocator.unregister(Object.class);
        assertFalse(SimpleLocator.isRegistered(Object.class));
    }

    @Test
    public void simpleLocator_singletonUnregister() {
        SimpleLocator.clear();
        SimpleLocator.registerSingleton(Object.class, Object::new);
        SimpleLocator.unregister(Object.class);
        assertFalse(SimpleLocator.isRegistered(Object.class));
    }

    @Test
    public void simpleLocator_clearMethod() {
        SimpleLocator.register(Object.class, Object::new);
        SimpleLocator.registerSingleton(String.class, () -> "");
        SimpleLocator.clear();
        assertFalse(SimpleLocator.isRegistered(Object.class));
        assertFalse(SimpleLocator.isRegistered(String.class));
    }
}
