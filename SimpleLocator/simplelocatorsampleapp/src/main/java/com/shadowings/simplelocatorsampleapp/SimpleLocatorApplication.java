package com.shadowings.simplelocatorsampleapp;

import android.app.Application;

import com.shadowings.core.MainViewModel;
import com.shadowings.core.MySampleInterface;
import com.shadowings.simplelocator.ObjectFactory;
import com.shadowings.simplelocator.SimpleLocator;

public class SimpleLocatorApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //initialize service locator rules
        SimpleLocator.register(MySampleInterface.class, MySampleConcreteClass::new);
        SimpleLocator.register(String.class, "FirstName", () -> "This is the first name string");
        SimpleLocator.register(String.class, "SecondName", () -> "This is the second name string");

        SimpleLocator.register(MainViewModel.class, () -> {
            MainViewModel viewModel = new MainViewModel();
            viewModel.setMySampleInterface(SimpleLocator.get(MySampleInterface.class));
            return viewModel;
        });
    }
}
