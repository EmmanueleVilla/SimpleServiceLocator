package com.shadowings.simplelocatorsampleapp;

import android.app.Application;

import com.shadowings.core.MySampleInterface;
import com.shadowings.simplelocator.IObjectFactory;
import com.shadowings.simplelocator.SimpleLocator;

public class SimpleLocatorApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //initialize service locator rules here
        SimpleLocator.getInstance().register(MySampleInterface.class, MySampleConcreteClass::new);

        SimpleLocator.getInstance().register(MySampleInterface.class, new IObjectFactory<MySampleInterface>() {
            @Override
            public MySampleInterface build() {
                return new MySampleConcreteClass();
            }
        });
    }
}
