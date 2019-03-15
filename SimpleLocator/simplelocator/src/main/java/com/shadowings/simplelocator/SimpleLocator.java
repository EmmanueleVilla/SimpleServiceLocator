package com.shadowings.simplelocator;

import java.util.HashMap;
import java.util.Objects;

public class SimpleLocator {

    private static HashMap<String, ObjectFactory<?>> factoriesMap = new HashMap<>();
    private static HashMap<String, Object> singletonsBuiltMap = new HashMap<>();
    private static HashMap<String, ObjectFactory<?>> singletonsMap = new HashMap<>();

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
        return get(type, null);
    }

    /**
     * Retrieve an istance of the class associated with the given type
     * @param type
     * The type of the class needed
     * @param <T>
     * The type of the class needed
     * @param name
     * The name of the class needed
     * @return
     * The instance of the class registered in the simple locator associated with the requested type
     */
    public static <T> T get(Class<T> type, String name) throws IllegalArgumentException {
        if(isBaseRegistered(type, name))
        {
            return baseGet(type, name);
        }

        if(isSingletonRegistered(type, name))
        {
            return singletonGet(type, name);
        }

        throw new IllegalArgumentException("Don't know how to create an instance of " + buildName(type, name));
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
        register(type, null, factory);
    }

    /**
     * Register the factory to create an instance when the given type and name is requested
     * @param type
     * The type to be registered
     * @param type
     * The name of the type to be registered
     * @param factory
     * The method to create an istance of the type needed
     * @param <T>
     * The type to be registered
     */
    public static <T> void register(Class<T> type, String name, ObjectFactory<T> factory) {
        unregister(type, name);
        factoriesMap.put(buildName(type, name), factory);
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
        unregister(type, null);
        singletonsMap.put(buildName(type, null), factory);
    }

    /**
     * Removes the registration for the given class
     * @param type
     * The type to be unregistered
     * @param <T>
     * The type to be unregistered
     */
    static <T> void unregister(Class<T> type) {
        unregister(type, null);
    }

    private static <T> void unregister(Class<T> type, String name) {
        String key = buildName(type, name);
        factoriesMap.remove(key);
        singletonsMap.remove(key);
        singletonsBuiltMap.remove(key);
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
    static <T> boolean isRegistered(Class<T> type) {
        return isBaseRegistered(type) || isSingletonRegistered(type);
    }

    /**
     * Removes all the registration from the locator
     */
    static void clear() {
        factoriesMap.clear();
        singletonsMap.clear();
    }

    /**
     * Count the number of the registered classes in the locator
     * @return
     * An integer representing the number of the registered classes
     */
    static int getRegistrationCount() {
        return factoriesMap.size() + singletonsMap.size();
    }

    @SuppressWarnings("unchecked")
    private static <T> T baseGet(Class<T> type, String name) {
        ObjectFactory<T> factory = (ObjectFactory<T>) factoriesMap.get(buildName(type, name));
        return Objects.requireNonNull(factory).build();
    }

    @SuppressWarnings("unchecked")
    private static <T> T singletonGet(Class<T> type) {
        return singletonGet(type, null);
    }

    @SuppressWarnings("unchecked")
    private static <T> T singletonGet(Class<T> type, String name) {
        String key = buildName(type, name);
        if(!singletonsBuiltMap.containsKey(key)) {
            ObjectFactory<T> factory = (ObjectFactory<T>)singletonsMap.get(key);
            Object built = Objects.requireNonNull(factory).build();
            singletonsBuiltMap.put(key, built);
        }
        return (T)singletonsBuiltMap.get(key);
    }

    private static <T> boolean isBaseRegistered(Class<T> type) {
        return isBaseRegistered(type, null);
    }

    private static <T> boolean isBaseRegistered(Class<T> type, String name) {
        return factoriesMap.containsKey(buildName(type, name));
    }

    private static <T> boolean isSingletonRegistered(Class<T> type) {
        return isSingletonRegistered(type, null);
    }

    private static <T> boolean isSingletonRegistered(Class<T> type, String name) {
        return singletonsMap.containsKey(buildName(type, name));
    }

    private static <T> String buildName(Class<T> type, String name)
    {
        if(name == null)
        {
            name = "";
        }
        return type.getName() + "_" + name;
    }
}
