package com.shadowings.simplelocator;

import java.util.HashMap;

public class SimpleLocator {

    private static HashMap<String, ObjectFactory<?>> factoriesMap = new HashMap<>();
    private static HashMap<String, Object> singletonsMap = new HashMap<>();

    /**
     * Retrieve an istance of the class associated with the given type
     * @param type
     * The type of the class needed
     * @param <T>
     * The type of the class needed
     * @return
     * The instance of the class registered in the simple locator associated with the requested type
     */
    public static <T> T get(Class<T> type) throws IllegalArgumentException {
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
    public static <T> void register(Class<T> type, ObjectFactory<T> factory) {
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
    public static <T> void registerSingleton(Class<T> type, ObjectFactory<T> factory) {
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
    public static <T> void unregister(Class<T> type) {
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
    public static <T> boolean isRegistered(Class<T> type) {
        return isBaseRegistered(type) || isSingletonRegistered(type);
    }

    /**
     * Removes all the registration from the locator
     */
    public static void clear() {
        factoriesMap.clear();
        singletonsMap.clear();
    }

    /**
     * Count the number of the registered classes in the locator
     * @return
     * An integer representing the number of the registered classes
     */
    public static int getRegistrationCount() {
        return factoriesMap.size() + singletonsMap.size();
    }

    @SuppressWarnings("unchecked")
    private static <T> T baseGet(Class<T> type) {
        ObjectFactory<T> factory = (ObjectFactory<T>) factoriesMap.get(type.getName());
        return factory.build();
    }

    @SuppressWarnings("unchecked")
    private static <T> T singletonGet(Class<T> type) {
        return (T)singletonsMap.get(type.getName());
    }

    private static <T> boolean isBaseRegistered(Class<T> type) {
        String key = type.getName();
        return factoriesMap.containsKey(key);
    }

    private static <T> boolean isSingletonRegistered(Class<T> type) {
        String key = type.getName();
        return singletonsMap.containsKey(key);
    }
}
