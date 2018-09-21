package com.shadowings.simplelocator;

interface ISimpleLocator {
    <T extends Object> T get(Class<T> type);
    <T extends Object> void register(Class<T> type, ObjectFactory<T> factory);
    <T extends Object> void registerSingleton(Class<T> type, ObjectFactory<T> factory);
    <T extends Object> void unregister(Class<T> type);
    <T extends Object> boolean isRegistered(Class<T> type);
    void clear();
    int getRegistrationCount();
}
