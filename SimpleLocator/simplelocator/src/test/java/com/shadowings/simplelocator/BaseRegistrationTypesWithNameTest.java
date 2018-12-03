package com.shadowings.simplelocator;

import com.shadowings.simplelocator.mocks.MyConcreteA;
import com.shadowings.simplelocator.mocks.MyConcreteB;
import com.shadowings.simplelocator.mocks.MyInterface;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

public class BaseRegistrationTypesWithNameTest {

    @Test
    public void selfRegistration()
    {
        SimpleLocator.clear();
        SimpleLocator.register(Object.class, "MyName", Object::new);
        assertNotNull(SimpleLocator.get(Object.class, "MyName"));
    }

    @Test
    public void interfaceRegistration()
    {
        SimpleLocator.clear();
        SimpleLocator.register(MyInterface.class, "MyName", MyConcreteA::new);
        assertTrue(SimpleLocator.get(MyInterface.class, "MyName") instanceof MyConcreteA);
    }

    @Test
    public void interfaceRegistration_lastOneCounts()
    {
        SimpleLocator.clear();
        SimpleLocator.register(MyInterface.class, "MyName", MyConcreteA::new);
        SimpleLocator.register(MyInterface.class, "MyName", MyConcreteB::new);
        assertTrue(SimpleLocator.get(MyInterface.class, "MyName") instanceof MyConcreteB);
    }

    @Test
    public void interfaceRegistration_differentName()
    {
        SimpleLocator.clear();
        SimpleLocator.register(MyInterface.class, "MyNameOne", MyConcreteA::new);
        SimpleLocator.register(MyInterface.class, "MyNameTwo", MyConcreteB::new);

        MyInterface one = SimpleLocator.get(MyInterface.class, "MyNameOne");
        MyInterface two = SimpleLocator.get(MyInterface.class, "MyNameTwo");

        assertTrue(one instanceof MyConcreteA);
        assertTrue(two instanceof MyConcreteB);
    }

    @Test
    public void newInstance()
    {
        SimpleLocator.clear();
        SimpleLocator.register(MyInterface.class, "MyName", MyConcreteA::new);
        MyInterface one = SimpleLocator.get(MyInterface.class, "MyName");
        MyInterface two = SimpleLocator.get(MyInterface.class, "MyName");
        assertNotSame(one, two);
    }

    @Test(expected = IllegalArgumentException.class)
    public void noRegistration()
    {
        SimpleLocator.clear();
        Object obj = SimpleLocator.get(Object.class, "MyName");
    }
}
