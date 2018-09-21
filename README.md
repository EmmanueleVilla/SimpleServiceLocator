# SimpleLocator
Simple locator is a lightweight and fast [Service Locator](https://en.wikipedia.org/wiki/Service_locator_pattern) library for android that took inspiration from [Splat](https://github.com/reactiveui/splat) for Xamarin.

#### Basic usage
Create the interface
```
public interface MySampleInterface {
    String getMessage();
}
```
Create concrete class
```
public class MySampleConcreteClass implements MySampleInterface {
    @Override
    public String getMessage() {
        return "TEST!";
    }
}
```
Define the association:
```
SimpleLocator.getInstance().register(
    MySampleInterface.class,
    new ObjectFactory<MySampleInterface>() {
        @Override
        public MySampleInterface build() {
            return new MySampleConcreteClass();
        }
    }
);
```
If you are using java 8, the registration is very simple:
```
SimpleLocator.getInstance().register(
    MySampleInterface.class,
    MySampleConcreteClass::new
);
```
Retrieve the concrete class
```
MySampleInterface concrete = SimpleLocator.getInstance().get(MySampleInterface.class);
```
#### Singleton
In the same way, one can register a class to be a singleton:
```
SimpleLocator.getInstance().registerSingleton(
    MySampleInterface.class,
    MySampleConcreteClass::new
);
```
In this way, SimpleLocator will always return the same instance of MySampleConcreteClass

#### Multiple Registration
If you register the same class more times, SimpleLocator will consider only the last one
```
SimpleLocator.getInstance().register(
    MySampleInterface.class,
    MySampleConcreteClass::new
);
SimpleLocator.getInstance().register(
    MySampleInterface.class,
    MyOtherSampleConcreteClass::new
);
MySampleInterface concrete = SimpleLocator.getInstance().get(MySampleInterface.class);
assertTrue(concrete instanceof MyOtherSampleConcreteClass)
```
#### Multi Module Project
SimpleLocator is particulary useful when you have a project with more than one module that aren't in a circular dependency (and that's the reason it was created!)
For example, suppose that you need to develop an application for mobile and wear. <br>You probably will have this module configuration:
- An App module
- A Wear module
- An android library module for the common code

SimpleLocator makes a joke declaring an interface in the library module and use its methods while the concrete class is declared in the specific platform module!<br>
You just need to create `MyInterface` in the library module and create `MyWearImpl` and `MyAppImpl` in the respective modules. Then, in the application startup, simply register them as seen previously!<br>
```
//App module
SimpleLocator.getInstance().register(
    MyInterface.class,
    MyAppImpl::new
);

//Wear module
SimpleLocator.getInstance().register(
    MyInterface.class,
    MyWearImpl::new
);

//Library module will have the correct implementation
MyInterface concrete = SimpleLocator.getInstance().get(MyInterface.class);
```
