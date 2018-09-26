package com.shadowings.core;

import com.shadowings.simplelocator.SimpleLocator;

public class MainViewModel {

    private MySampleInterface mySampleInterface;

    //use the empty constructor in the application to take the dependencies from SimpleLocator
    public MainViewModel()
    {
        InitDependencies(SimpleLocator.get(MySampleInterface.class));
    }

    //use the explicit constructor in the application tests to mock the class dependencies
    public MainViewModel(MySampleInterface mySampleInterface)
    {
        InitDependencies(mySampleInterface);
    }

    private void InitDependencies(MySampleInterface mySampleInterface)
    {
        this.mySampleInterface = mySampleInterface;
    }

    public String getMessage()
    {
        //we are executing the concrete class from the other module without depend on it!
        return this.mySampleInterface.getMessage();
    }
}
