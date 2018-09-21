package com.shadowings.simplelocator;

import java.util.HashMap;

public class SimpleLocator implements ISimpleLocator {

    private HashMap<String, IObjectFactory<?>> factoriesMap;
    private HashMap<String, Object> singletonsMap;

    private static SimpleLocator instance;
    public static SimpleLocator getInstance()
    {
        if(instance==null)
        {
            instance = new SimpleLocator();
        }
        return instance;
    }

    private SimpleLocator()
    {
        factoriesMap = new HashMap<>();
        singletonsMap = new HashMap<>();
    }

    @Override
    public <T> T get(Class<T> type) {
        if(isBaseRegistered(type))
        {
            return baseGet(type);
        }

        if(isSingletonRegistered(type))
        {
            return singletonGet(type);
        }

        throw new IllegalArgumentException("Don't know how to create an instance of " + type.getName());
    }

    @SuppressWarnings("unchecked")
    private <T> T baseGet(Class<T> type) {
        IObjectFactory<T> factory = (IObjectFactory<T>) factoriesMap.get(type.getName());
        return factory.build();
    }

    @SuppressWarnings("unchecked")
    private <T> T singletonGet(Class<T> type) {
        return (T)singletonsMap.get(type.getName());
    }

    @Override
    public <T> void register(Class<T> type, IObjectFactory<T> factory) {
        unregister(type);
        factoriesMap.put(type.getName(), factory);
    }

    @Override
    public <T> void registerSingleton(Class<T> type, IObjectFactory<T> factory) {
        unregister(type);
        singletonsMap.put(type.getName(), factory.build());
    }

    @Override
    public <T> void unregister(Class<T> type) {
        String key = type.getName();
        factoriesMap.remove(key);
        singletonsMap.remove(key);
    }

    @Override
    public <T> boolean isRegistered(Class<T> type) {
        return isBaseRegistered(type) || isSingletonRegistered(type);
    }

    private <T> boolean isBaseRegistered(Class<T> type) {
        String key = type.getName();
        return factoriesMap.containsKey(key);
    }

    private <T> boolean isSingletonRegistered(Class<T> type) {
        String key = type.getName();
        return singletonsMap.containsKey(key);
    }

    @Override
    public void clear() {
        factoriesMap.clear();
        singletonsMap.clear();
    }

    @Override
    public int getRegistrationCount() {
        return factoriesMap.size() + singletonsMap.size();
    }
}
