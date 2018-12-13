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

[ ![Download](https://api.bintray.com/packages/willyrs/SimpleLocator/com.shadowings.simplelocator/images/download.svg) ](https://bintray.com/willyrs/SimpleLocator/com.shadowings.simplelocator/_latestVersion)

### How to import
Add the repository to the main project gradle file
```
repositories {
    maven {
        url  "https://dl.bintray.com/willyrs/SimpleLocator" 
    }
}
```
Add the library to the module gradle file (check latest version in the badge above)
```
dependencies {
    implementation 'com.shadowings.simplelocator:simplelocator:VERSION'
}
```

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
SimpleLocator.register(
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
SimpleLocator.register(
    MySampleInterface.class,
    MySampleConcreteClass::new
);
```
Retrieve the concrete class
```
MySampleInterface concrete = SimpleLocator.get(MySampleInterface.class);
```
Of course, you can also register a class to itself
```
SimpleLocator.register(
    MySampleConcreteClass.class,
    MySampleConcreteClass::new
);
```
### Singleton
In the same way, one can register a class to be a singleton:
```
SimpleLocator.registerSingleton(
    MySampleInterface.class,
    MySampleConcreteClass::new
);
```
In this way, SimpleLocator will always return the same instance of MySampleConcreteClass

### Register with names
You can also pass a name when registering to register constants:
```
SimpleLocator.register(
    String.class, 
    "FirstName", 
    () -> "This is the first name string"
);

SimpleLocator.register(
    String.class, 
    "SecondName", 
    () -> "This is the second name string"
);

SimpleLocator.get(String.class, "FirstName"); //contains "This is the first name string"
SimpleLocator.get(String.class, "SecondName"); //contains "This is the second name string"
```

### Multiple Registration
If you register the same class more times, SimpleLocator will consider only the last one
```
SimpleLocator.register(
    MySampleInterface.class,
    MySampleConcreteClass::new
);
SimpleLocator.register(
    MySampleInterface.class,
    MyOtherSampleConcreteClass::new
);
MySampleInterface concrete = SimpleLocator.get(MySampleInterface.class);
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
SimpleLocator.register(
    MyInterface.class,
    MyAppImpl::new
);

//Wear module
SimpleLocator.register(
    MyInterface.class,
    MyWearImpl::new
);

//Library module will have the correct implementation
MyInterface concrete = SimpleLocator.get(MyInterface.class);
```
### Best practices
Using SimpleLocator doesn't mean that it's hard to mock your dependencies: to have it working alongside unit tests, consider to write your classes something like this:
```
public class MainViewModel {

    private MySampleInterface mySampleInterface;

    public void setMySampleInterface(MySampleInterface mySampleInterface) {
        this.mySampleInterface = mySampleInterface;
    }

    public String getMessage()
    {
        //we are executing the concrete class from the other module without depend on it, yay!
        return this.mySampleInterface.getMessage();
    }
}
```
Then, register the rules like this:
```
SimpleLocator.register(MySampleInterface.class, MySampleConcreteClass::new);
SimpleLocator.register(MainViewModel.class, () -> {
    MainViewModel viewModel = new MainViewModel();
    viewModel.setMySampleInterface(SimpleLocator.get(MySampleInterface.class));
    return viewModel;
});
```
In this way, you can create the MainViewModel instance by simply calling:
```
MainViewModel mainViewModel = SimpleLocator.get(MainViewModel.class);
```
While in the unit tests, you will create it like this:
```
MainViewModel viewModel = new MainViewModel();
viewModel.setMySampleInterface(MySampleInterfaceMockForUnitTests);
```
The sample app within the repository gives an example of this project structure:

![SampleApp](https://scontent-mxp1-1.xx.fbcdn.net/v/t1.0-9/42394639_10217915313382636_4814971922968215552_n.jpg?_nc_cat=103&oh=8026cef86509d9ef7a7ef347247bd814&oe=5C284B0D)
