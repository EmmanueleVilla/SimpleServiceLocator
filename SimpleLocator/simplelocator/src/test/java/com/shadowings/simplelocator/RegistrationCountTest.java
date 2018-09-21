package com.shadowings.simplelocator;

import com.shadowings.simplelocator.mocks.MyConcreteA;
import com.shadowings.simplelocator.mocks.MyConcreteB;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegistrationCountTest {

    @Test
    public void baseRegistration_count_increase() {
        SimpleLocator.getInstance().clear();
        SimpleLocator.getInstance().register(Object.class, Object::new);
        assertEquals(1, SimpleLocator.getInstance().getRegistrationCount());
    }

    @Test
    public void singletonRegistration_count_increase() {
        SimpleLocator.getInstance().clear();
        SimpleLocator.getInstance().registerSingleton(Object.class, Object::new);
        assertEquals(1, SimpleLocator.getInstance().getRegistrationCount());
    }

    @Test
    public void combinedRegistration_count_increase() {
        SimpleLocator.getInstance().clear();
        SimpleLocator.getInstance().register(Object.class, Object::new);
        SimpleLocator.getInstance().registerSingleton(String.class, () -> "");
        assertEquals(2, SimpleLocator.getInstance().getRegistrationCount());
    }

    @Test
    public void sameRegistration_doesnt_increase() {
        SimpleLocator.getInstance().clear();
        SimpleLocator.getInstance().register(Object.class, MyConcreteA::new);
        SimpleLocator.getInstance().register(Object.class, MyConcreteB::new);
        assertEquals(1, SimpleLocator.getInstance().getRegistrationCount());
    }

    @Test
    public void mixedRegistration_doesnt_increase() {
        SimpleLocator.getInstance().clear();
        SimpleLocator.getInstance().register(Object.class, MyConcreteA::new);
        SimpleLocator.getInstance().registerSingleton(Object.class, MyConcreteB::new);
        assertEquals(1, SimpleLocator.getInstance().getRegistrationCount());
    }

    @Test
    public void mixedRegistration_doesnt_increase_reverse() {
        SimpleLocator.getInstance().clear();

        SimpleLocator.getInstance().registerSingleton(Object.class, MyConcreteB::new);

        SimpleLocator.getInstance().register(Object.class, MyConcreteA::new);

        assertEquals(1, SimpleLocator.getInstance().getRegistrationCount());
    }
}
