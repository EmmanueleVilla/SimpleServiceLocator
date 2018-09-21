package com.shadowings.simplelocator;

import com.shadowings.simplelocator.mocks.MyConcreteA;
import com.shadowings.simplelocator.mocks.MyConcreteB;
import com.shadowings.simplelocator.mocks.MyInterface;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MixedRegistrationTypesTest {

    @Test
    public void base_then_singleton()
    {
        SimpleLocator.getInstance().clear();

        SimpleLocator.getInstance().register(MyInterface.class, new IObjectFactory<MyInterface>() {
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

        MyInterface one = SimpleLocator.getInstance().get(MyInterface.class);
        MyInterface two = SimpleLocator.getInstance().get(MyInterface.class);

        assertTrue(one == two);
    }

    @Test
    public void singleton_then_base()
    {
        SimpleLocator.getInstance().clear();

        SimpleLocator.getInstance().registerSingleton(MyInterface.class, new IObjectFactory<MyInterface>() {
            @Override
            public MyInterface build() {
                return new MyConcreteA();
            }
        });

        SimpleLocator.getInstance().register(MyInterface.class, new IObjectFactory<MyInterface>() {
            @Override
            public MyInterface build() {
                return new MyConcreteB();
            }
        });

        MyInterface one = SimpleLocator.getInstance().get(MyInterface.class);
        MyInterface two = SimpleLocator.getInstance().get(MyInterface.class);

        assertTrue(one != two);
    }
}
