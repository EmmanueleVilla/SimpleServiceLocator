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
        SimpleLocator.getInstance().register(Object.class, new ObjectFactory<Object>() {
            @Override
            public Object build() {
                return new Object();
            }
        });
        assertTrue(SimpleLocator.getInstance().isRegistered(Object.class));
    }

    @Test
    public void singleton_isRegistered() {
        SimpleLocator.getInstance().clear();
        SimpleLocator.getInstance().registerSingleton(Object.class, new ObjectFactory<Object>() {
            @Override
            public Object build() {
                return new Object();
            }
        });
        assertTrue(SimpleLocator.getInstance().isRegistered(Object.class));
    }

    @Test
    public void simpleLocator_baseUnregister() {
        SimpleLocator.getInstance().clear();
        SimpleLocator.getInstance().register(Object.class, new ObjectFactory<Object>() {
            @Override
            public Object build() {
                return new Object();
            }
        });
        SimpleLocator.getInstance().unregister(Object.class);
        assertFalse(SimpleLocator.getInstance().isRegistered(Object.class));
    }

    @Test
    public void simpleLocator_singletonUnregister() {
        SimpleLocator.getInstance().clear();
        SimpleLocator.getInstance().registerSingleton(Object.class, new ObjectFactory<Object>() {
            @Override
            public Object build() {
                return new Object();
            }
        });
        SimpleLocator.getInstance().unregister(Object.class);
        assertFalse(SimpleLocator.getInstance().isRegistered(Object.class));
    }

    @Test
    public void simpleLocator_clearMethod() {
        SimpleLocator.getInstance().register(Object.class, new ObjectFactory<Object>() {
            @Override
            public Object build() {
                return new Object();
            }
        });
        SimpleLocator.getInstance().registerSingleton(String.class, new ObjectFactory<String>() {
            @Override
            public String build() {
                return "";
            }
        });
        SimpleLocator.getInstance().clear();
        assertFalse(SimpleLocator.getInstance().isRegistered(Object.class));
        assertFalse(SimpleLocator.getInstance().isRegistered(String.class));
    }
}
