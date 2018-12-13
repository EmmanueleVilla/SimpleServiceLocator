package com.shadowings.core;

import com.shadowings.simplelocator.SimpleLocator;

public class MainViewModel {

    private MySampleInterface mySampleInterface;

    public String getMessage()
    {
        //we are executing the concrete class from the other module without depend on it!
        return this.mySampleInterface.getMessage();
    }

    public String getNamedOne() {
        return SimpleLocator.get(String.class, "FirstName");
    }

    public String getNamedTwo() {
        return SimpleLocator.get(String.class, "SecondName");
    }

    public void setMySampleInterface(MySampleInterface mySampleInterface) {
        this.mySampleInterface = mySampleInterface;
    }
}
