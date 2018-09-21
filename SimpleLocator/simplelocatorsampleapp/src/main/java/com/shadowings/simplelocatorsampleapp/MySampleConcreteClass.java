package com.shadowings.simplelocatorsampleapp;

import com.shadowings.core.MySampleInterface;

public class MySampleConcreteClass implements MySampleInterface {
    @Override
    public String getMessage() {
        return "Successfully used an instance of MySampleConcreteClass in the core module!";
    }
}
