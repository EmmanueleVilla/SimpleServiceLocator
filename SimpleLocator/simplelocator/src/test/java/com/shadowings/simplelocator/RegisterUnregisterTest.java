package com.shadowings.simplelocator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegisterUnregisterTest {

    @Test
    public void class_unregistered()
    {
        SimpleLocator.getInstance().clear();
        assertFalse(SimpleLocator.getInstance().isRegistered(Object.class));
    }

    @Test
    public void base_isRegistered() {
        SimpleLocator.getInstance().clear();
        SimpleLocator.getInstance().register(Object.class, Object::new);
        assertTrue(SimpleLocator.getInstance().isRegistered(Object.class));
    }

    @Test
    public void singleton_isRegistered() {
        SimpleLocator.getInstance().clear();
        SimpleLocator.getInstance().registerSingleton(Object.class, Object::new);
        assertTrue(SimpleLocator.getInstance().isRegistered(Object.class));
    }

    @Test
    public void simpleLocator_baseUnregister() {
        SimpleLocator.getInstance().clear();
        SimpleLocator.getInstance().register(Object.class, Object::new);
        SimpleLocator.getInstance().unregister(Object.class);
        assertFalse(SimpleLocator.getInstance().isRegistered(Object.class));
    }

    @Test
    public void simpleLocator_singletonUnregister() {
        SimpleLocator.getInstance().clear();
        SimpleLocator.getInstance().registerSingleton(Object.class, Object::new);
        SimpleLocator.getInstance().unregister(Object.class);
        assertFalse(SimpleLocator.getInstance().isRegistered(Object.class));
    }

    @Test
    public void simpleLocator_clearMethod() {
        SimpleLocator.getInstance().register(Object.class, Object::new);
        SimpleLocator.getInstance().registerSingleton(String.class, () -> "");
        SimpleLocator.getInstance().clear();
        assertFalse(SimpleLocator.getInstance().isRegistered(Object.class));
        assertFalse(SimpleLocator.getInstance().isRegistered(String.class));
    }
}
