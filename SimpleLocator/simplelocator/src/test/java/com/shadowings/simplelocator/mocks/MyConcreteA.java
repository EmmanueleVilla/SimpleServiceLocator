package com.shadowings.simplelocator.mocks;

public class MyConcreteA implements MyInterface {
    public MyOtherInterface getMyOtherInterface() {
        return myOtherInterface;
    }

    public void setMyOtherInterface(MyOtherInterface myOtherInterface) {
        this.myOtherInterface = myOtherInterface;
    }

    MyOtherInterface myOtherInterface;
}
