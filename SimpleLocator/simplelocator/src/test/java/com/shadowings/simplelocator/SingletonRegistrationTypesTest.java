package com.shadowings.simplelocator;

import com.shadowings.simplelocator.mocks.MyConcreteA;
import com.shadowings.simplelocator.mocks.MyConcreteB;
import com.shadowings.simplelocator.mocks.MyInterface;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SingletonRegistrationTypesTest {

    @Test
    public void selfRegistration()
    {
        SimpleLocator.clear();
        SimpleLocator.registerSingleton(Object.class, Object::new);
        assertTrue(SimpleLocator.get(Object.class) != null);
    }

    @Test
    public void interfaceRegistration()
    {
        SimpleLocator.clear();
        SimpleLocator.registerSingleton(MyInterface.class, MyConcreteA::new);
        assertTrue(SimpleLocator.get(MyInterface.class) instanceof MyConcreteA);
    }

    @Test
    public void interfaceRegistration_lastOneCounts()
    {
        SimpleLocator.clear();
        SimpleLocator.registerSingleton(MyInterface.class, MyConcreteA::new);
        SimpleLocator.registerSingleton(MyInterface.class, MyConcreteB::new);
        assertTrue(SimpleLocator.get(MyInterface.class) instanceof MyConcreteB);
    }

    @Test
    public void sameInstance()
    {
        SimpleLocator.clear();
        SimpleLocator.registerSingleton(MyInterface.class, MyConcreteA::new);
        MyInterface one = SimpleLocator.get(MyInterface.class);
        MyInterface two = SimpleLocator.get(MyInterface.class);
        assertTrue(one == two);
    }

    @Test(expected = IllegalArgumentException.class)
    public void noRegistration()
    {
        SimpleLocator.clear();
        SimpleLocator.get(Object.class);
    }
}
