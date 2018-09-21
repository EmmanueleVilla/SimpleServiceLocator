# SimpleLocator
SimpleLocator is a lightweight and fast [Service Locator](https://en.wikipedia.org/wiki/Service_locator_pattern) library for android that took inspiration from [Splat](https://github.com/reactiveui/splat) for Xamarin.<br>
- No reflection
- No generated code
- No annotation
- Simple to setup, simple to use: just initialize its rules and you are ready to go!
- Ability to add, modify and remove rules at runtime
- No need to add work to have it working in a multiple modules project (see below 'Multi Module Project')
- Unit test friendly (see below 'Best practices')
- Basic and singleton registration

### Basic usage
Your interface
```
public interface MySampleInterface {
    String getMessage();
}
```
Your concrete class
```
public class MySampleConcreteClass implements MySampleInterface {
    @Override
    public String getMessage() {
        return "TEST!";
    }
}
```
Define the rule:
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
If you are using java 8, the rule is very simple:
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
Of course, you can also register a class to itself
```
SimpleLocator.getInstance().register(
    MySampleConcreteClass.class,
    MySampleConcreteClass::new
);
```
### Singleton
In the same way, one can register a class to be a singleton:
```
SimpleLocator.getInstance().registerSingleton(
    MySampleInterface.class,
    MySampleConcreteClass::new
);
```
In this way, SimpleLocator will always return the same instance of MySampleConcreteClass

### Multiple Registration
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
### Multi Module Project
SimpleLocator is particulary useful when you have a project with more than one module that aren't in a circular dependency (and that's the reason it was created!)
For example, suppose that you need to develop an application for mobile and wear. <br>You probably will have this module configuration:
- An App module
- A Wear module
- An android library module for the common code

SimpleLocator makes easy declaring an interface in the library module and use its methods while the concrete class is declared in the specific platform module!<br>
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
### Best practices
Using SimpleLocator doesn't mean that it's hard to mock your dependencies: to have it working alongside unit tests, consider to write your classes something like this:
```
public class MainViewModel {

    private MySampleInterface mySampleInterface;

    //use the empty constructor in the application to take the dependencies from SimpleLocator
    public MainViewModel()
    {
        InitDependencies(SimpleLocator.getInstance().get(MySampleInterface.class));
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
        //we are executing the concrete class from the other module without depend on it, yay!
        return this.mySampleInterface.getMessage();
    }
}

```
