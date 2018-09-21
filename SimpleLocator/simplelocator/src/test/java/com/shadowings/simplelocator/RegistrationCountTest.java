package com.shadowings.simplelocator;

import com.shadowings.simplelocator.mocks.MyConcreteA;
import com.shadowings.simplelocator.mocks.MyConcreteB;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegistrationCountTest {

    @Test
    public void baseRegistration_count_increase() {
        SimpleLocator.getInstance().clear();
        SimpleLocator.getInstance().register(Object.class, new IObjectFactory<Object>() {
            @Override
            public Object build() {
                return new Object();
            }
        });
        assertEquals(1, SimpleLocator.getInstance().getRegistrationCount());
    }

    @Test
    public void singletonRegistration_count_increase() {
        SimpleLocator.getInstance().clear();
        SimpleLocator.getInstance().registerSingleton(Object.class, new IObjectFactory<Object>() {
            @Override
            public Object build() {
                return new Object();
            }
        });
        assertEquals(1, SimpleLocator.getInstance().getRegistrationCount());
    }

    @Test
    public void combinedRegistration_count_increase() {
        SimpleLocator.getInstance().clear();
        SimpleLocator.getInstance().register(Object.class, new IObjectFactory<Object>() {
            @Override
            public Object build() {
                return new Object();
            }
        });
        SimpleLocator.getInstance().registerSingleton(String.class, new IObjectFactory<String>() {
            @Override
            public String build() {
                return "";
            }
        });
        assertEquals(2, SimpleLocator.getInstance().getRegistrationCount());
    }

    @Test
    public void sameRegistration_doesnt_increase() {
        SimpleLocator.getInstance().clear();
        SimpleLocator.getInstance().register(Object.class, new IObjectFactory<Object>() {
            @Override
            public Object build() {
                return new MyConcreteA();
            }
        });
        SimpleLocator.getInstance().register(Object.class, new IObjectFactory<Object>() {
            @Override
            public Object build() {
                return new MyConcreteB();
            }
        });
        assertEquals(1, SimpleLocator.getInstance().getRegistrationCount());
    }

    @Test
    public void mixedRegistration_doesnt_increase() {
        SimpleLocator.getInstance().clear();
        SimpleLocator.getInstance().register(Object.class, new IObjectFactory<Object>() {
            @Override
            public Object build() {
                return new MyConcreteA();
            }
        });
        SimpleLocator.getInstance().registerSingleton(Object.class, new IObjectFactory<Object>() {
            @Override
            public Object build() {
                return new MyConcreteB();
            }
        });
        assertEquals(1, SimpleLocator.getInstance().getRegistrationCount());
    }

    @Test
    public void mixedRegistration_doesnt_increase_reverse() {
        SimpleLocator.getInstance().clear();

        SimpleLocator.getInstance().registerSingleton(Object.class, new IObjectFactory<Object>() {
            @Override
            public Object build() {
                return new MyConcreteB();
            }
        });

        SimpleLocator.getInstance().register(Object.class, new IObjectFactory<Object>() {
            @Override
            public Object build() {
                return new MyConcreteA();
            }
        });

        assertEquals(1, SimpleLocator.getInstance().getRegistrationCount());
    }
}
