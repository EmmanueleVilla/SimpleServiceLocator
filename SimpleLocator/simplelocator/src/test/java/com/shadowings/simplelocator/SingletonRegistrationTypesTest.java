package com.shadowings.simplelocator;

import com.shadowings.simplelocator.mocks.MyConcreteA;
import com.shadowings.simplelocator.mocks.MyConcreteB;
import com.shadowings.simplelocator.mocks.MyInterface;
import com.shadowings.simplelocator.mocks.MyOtherConcreteA;
import com.shadowings.simplelocator.mocks.MyOtherInterface;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class SingletonRegistrationTypesTest {

    @Test
    public void selfRegistration()
    {
        SimpleLocator.clear();
        SimpleLocator.registerSingleton(Object.class, Object::new);
        Object obj = SimpleLocator.get(Object.class);
        assertNotNull(obj);
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
        MyInterface obj = SimpleLocator.get(MyInterface.class);
        assertNotNull(obj);
        assertFalse(obj instanceof MyConcreteA);
        assertTrue(obj instanceof MyConcreteB);
    }

    @Test
    public void sameInstance()
    {
        SimpleLocator.clear();
        SimpleLocator.registerSingleton(MyInterface.class, MyConcreteA::new);
        MyInterface one = SimpleLocator.get(MyInterface.class);
        MyInterface two = SimpleLocator.get(MyInterface.class);
        assertSame(one, two);
    }

    @Test
    public void registerSingletonIsLazy()
    {
        SimpleLocator.clear();
        SimpleLocator.registerSingleton(MyInterface.class, () -> {
            MyConcreteA a = new MyConcreteA();
            a.setMyOtherInterface(SimpleLocator.get(MyOtherInterface.class));
            return a;
        });
        SimpleLocator.register(MyOtherInterface.class, MyOtherConcreteA::new);
    }

    @Test(expected = IllegalArgumentException.class)
    public void noRegistration()
    {
        SimpleLocator.clear();
        SimpleLocator.get(Object.class);
    }
}
