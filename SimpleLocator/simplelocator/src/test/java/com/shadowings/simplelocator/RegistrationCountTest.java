package com.shadowings.simplelocator;

import com.shadowings.simplelocator.mocks.MyConcreteA;
import com.shadowings.simplelocator.mocks.MyConcreteB;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegistrationCountTest {

    @Test
    public void baseRegistration_count_increase() {
        SimpleLocator.clear();
        SimpleLocator.register(Object.class, Object::new);
        assertEquals(1, SimpleLocator.getRegistrationCount());
    }

    @Test
    public void singletonRegistration_count_increase() {
        SimpleLocator.clear();
        SimpleLocator.registerSingleton(Object.class, Object::new);
        assertEquals(1, SimpleLocator.getRegistrationCount());
    }

    @Test
    public void combinedRegistration_count_increase() {
        SimpleLocator.clear();
        SimpleLocator.register(Object.class, Object::new);
        SimpleLocator.registerSingleton(String.class, () -> "");
        assertEquals(2, SimpleLocator.getRegistrationCount());
    }

    @Test
    public void sameRegistration_doesnt_increase() {
        SimpleLocator.clear();
        SimpleLocator.register(Object.class, MyConcreteA::new);
        SimpleLocator.register(Object.class, MyConcreteB::new);
        assertEquals(1, SimpleLocator.getRegistrationCount());
    }

    @Test
    public void mixedRegistration_doesnt_increase() {
        SimpleLocator.clear();
        SimpleLocator.register(Object.class, MyConcreteA::new);
        SimpleLocator.registerSingleton(Object.class, MyConcreteB::new);
        assertEquals(1, SimpleLocator.getRegistrationCount());
    }

    @Test
    public void mixedRegistration_doesnt_increase_reverse() {
        SimpleLocator.clear();

        SimpleLocator.registerSingleton(Object.class, MyConcreteB::new);

        SimpleLocator.register(Object.class, MyConcreteA::new);

        assertEquals(1, SimpleLocator.getRegistrationCount());
    }
}
