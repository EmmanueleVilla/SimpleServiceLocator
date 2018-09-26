package com.shadowings.simplelocator;

import com.shadowings.simplelocator.mocks.MyConcreteA;
import com.shadowings.simplelocator.mocks.MyConcreteB;
import com.shadowings.simplelocator.mocks.MyInterface;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MixedRegistrationTypesTest {

    @Test
    public void base_then_singleton()
    {
        SimpleLocator.clear();

        SimpleLocator.register(MyInterface.class, MyConcreteA::new);

        SimpleLocator.registerSingleton(MyInterface.class, MyConcreteB::new);

        MyInterface one = SimpleLocator.get(MyInterface.class);
        MyInterface two = SimpleLocator.get(MyInterface.class);

        assertTrue(one == two);
    }

    @Test
    public void singleton_then_base()
    {
        SimpleLocator.clear();

        SimpleLocator.registerSingleton(MyInterface.class, MyConcreteA::new);

        SimpleLocator.register(MyInterface.class, MyConcreteB::new);

        MyInterface one = SimpleLocator.get(MyInterface.class);
        MyInterface two = SimpleLocator.get(MyInterface.class);

        assertTrue(one != two);
    }
}
