package com.shadowings.simplelocator;

import java.util.HashMap;

public class SimpleLocator implements ISimpleLocator {

    private HashMap<String, ObjectFactory<?>> factoriesMap;
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

    /**
     * Retrieve an istance of the class associated with the given type
     * @param type
     * The type of the class needed
     * @param <T>
     * The type of the class needed
     * @return
     * The instance of the class registered in the simple locator associated with the requested type
     */
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

    /**
     * Register the factory to create an instance when the given type is requested
     * @param type
     * The type to be registered
     * @param factory
     * The method to create an istance of the type needed
     * @param <T>
     * The type to be registered
     */
    @Override
    public <T> void register(Class<T> type, ObjectFactory<T> factory) {
        unregister(type);
        factoriesMap.put(type.getName(), factory);
    }

    /**
     * Register the factory to create the singleton instance when the given type is requested
     * @param type
     * The type to be registered as singleton
     * @param factory
     * The method to create an istance of the type needed
     * @param <T>
     * The type to be registered
     */
    @Override
    public <T> void registerSingleton(Class<T> type, ObjectFactory<T> factory) {
        unregister(type);
        singletonsMap.put(type.getName(), factory.build());
    }

    /**
     * Removes the registration for the given class
     * @param type
     * The type to be unregistered
     * @param <T>
     * The type to be unregistered
     */
    @Override
    public <T> void unregister(Class<T> type) {
        String key = type.getName();
        factoriesMap.remove(key);
        singletonsMap.remove(key);
    }

    /**
     * Check if the given class is registered in the locator
     * @param type
     * The type to be checked
     * @param <T>
     * The type to be checked
     * @return
     * True if the given type is registered, false otherwise
     */
    @Override
    public <T> boolean isRegistered(Class<T> type) {
        return isBaseRegistered(type) || isSingletonRegistered(type);
    }

    /**
     * Removes all the registration from the locator
     */
    @Override
    public void clear() {
        factoriesMap.clear();
        singletonsMap.clear();
    }

    /**
     * Count the number of the registered classes in the locator
     * @return
     * An integer representing the number of the registered classes
     */
    @Override
    public int getRegistrationCount() {
        return factoriesMap.size() + singletonsMap.size();
    }

    @SuppressWarnings("unchecked")
    private <T> T baseGet(Class<T> type) {
        ObjectFactory<T> factory = (ObjectFactory<T>) factoriesMap.get(type.getName());
        return factory.build();
    }

    @SuppressWarnings("unchecked")
    private <T> T singletonGet(Class<T> type) {
        return (T)singletonsMap.get(type.getName());
    }

    private <T> boolean isBaseRegistered(Class<T> type) {
        String key = type.getName();
        return factoriesMap.containsKey(key);
    }

    private <T> boolean isSingletonRegistered(Class<T> type) {
        String key = type.getName();
        return singletonsMap.containsKey(key);
    }
}
