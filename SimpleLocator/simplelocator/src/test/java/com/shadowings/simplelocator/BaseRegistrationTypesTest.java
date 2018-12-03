package com.shadowings.simplelocator;

import com.shadowings.simplelocator.mocks.MyConcreteA;
import com.shadowings.simplelocator.mocks.MyConcreteB;
import com.shadowings.simplelocator.mocks.MyInterface;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

public class BaseRegistrationTypesTest {

    @Test
    public void selfRegistration()
    {
        SimpleLocator.clear();
        SimpleLocator.register(Object.class, Object::new);
        assertNotNull(SimpleLocator.get(Object.class));
    }

    @Test
    public void interfaceRegistration()
    {
        SimpleLocator.clear();
        SimpleLocator.register(MyInterface.class, MyConcreteA::new);
        assertTrue(SimpleLocator.get(MyInterface.class) instanceof MyConcreteA);
    }

    @Test
    public void interfaceRegistration_lastOneCounts()
    {
        SimpleLocator.clear();
        SimpleLocator.register(MyInterface.class, MyConcreteA::new);
        SimpleLocator.register(MyInterface.class, MyConcreteB::new);
        assertTrue(SimpleLocator.get(MyInterface.class) instanceof MyConcreteB);
    }

    @Test
    public void newInstance()
    {
        SimpleLocator.clear();
        SimpleLocator.register(MyInterface.class, MyConcreteA::new);
        MyInterface one = SimpleLocator.get(MyInterface.class);
        MyInterface two = SimpleLocator.get(MyInterface.class);
        assertNotSame(one, two);
    }

    @Test(expected = IllegalArgumentException.class)
    public void noRegistration()
    {
        SimpleLocator.clear();
        Object obj = SimpleLocator.get(Object.class);
    }
}
