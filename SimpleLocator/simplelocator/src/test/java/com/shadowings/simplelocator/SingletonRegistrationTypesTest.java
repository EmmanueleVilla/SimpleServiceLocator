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
        SimpleLocator.getInstance().clear();
        SimpleLocator.getInstance().registerSingleton(Object.class, new IObjectFactory<Object>() {
            @Override
            public Object build() {
                return new Object();
            }
        });
        assertTrue(SimpleLocator.getInstance().get(Object.class) != null);
    }

    @Test
    public void interfaceRegistration()
    {
        SimpleLocator.getInstance().clear();
        SimpleLocator.getInstance().registerSingleton(MyInterface.class, new IObjectFactory<MyInterface>() {
            @Override
            public MyInterface build() {
                return new MyConcreteA();
            }
        });
        assertTrue(SimpleLocator.getInstance().get(MyInterface.class) instanceof MyConcreteA);
    }

    @Test
    public void interfaceRegistration_lastOneCounts()
    {
        SimpleLocator.getInstance().clear();
        SimpleLocator.getInstance().registerSingleton(MyInterface.class, new IObjectFactory<MyInterface>() {
            @Override
            public MyInterface build() {
                return new MyConcreteA();
            }
        });
        SimpleLocator.getInstance().registerSingleton(MyInterface.class, new IObjectFactory<MyInterface>() {
            @Override
            public MyInterface build() {
                return new MyConcreteB();
            }
        });
        assertTrue(SimpleLocator.getInstance().get(MyInterface.class) instanceof MyConcreteB);
    }

    @Test
    public void sameInstance()
    {
        SimpleLocator.getInstance().clear();
        SimpleLocator.getInstance().registerSingleton(MyInterface.class, new IObjectFactory<MyInterface>() {
            @Override
            public MyInterface build() {
                return new MyConcreteA();
            }
        });
        MyInterface one = SimpleLocator.getInstance().get(MyInterface.class);
        MyInterface two = SimpleLocator.getInstance().get(MyInterface.class);
        assertTrue(one == two);
    }

    @Test(expected = IllegalArgumentException.class)
    public void noRegistration()
    {
        SimpleLocator.getInstance().clear();
        Object obj = SimpleLocator.getInstance().get(Object.class);
    }
}
