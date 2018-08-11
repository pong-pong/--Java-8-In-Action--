# Java 8 :: Lambda Expression - Part 1

![lambda_symbol](./assets/lambda_symbol.png)

In the last post, a lambda expression was used as an example while explaining the behavior parameterization. Now it’s time to look at the lambda expression, and it will be divided into two parts. Part 1 will explain the basics of lambda expressions. In Part 2, we’ll look at various examples that use lambda expressions (utilization).

# What is Lambda?
The Lambda expression is __new in Java 8__. While lambda does not provide something that Java could not do with pre-Java 8, lambda allows you to pass code in a concise way, and when using behavior parameter you don’t need to write code that gets stuck and outdated, such as anonymous classes. The following is the features of the lambda expression.

* Anonymous
* Function
* Passing
* Simplicity

Lambda has no name unlike the usual methods. And Lambda is a function, not a method, because it doesn’t depend on a particular class. Also it can be passed as method arguments or stored invariables. Finally, it saves you the trouble of implementing a lot of useless code.

# Structure of lambda

![lambda_symbol](./assets/lambda_structure.png)

As shown above, the lambda is divided into three parts.

* __Parameter List (pink area)__ - List of parameters to be consumed in the lambda’s body
* __Arrow (green area)__ - The arrow separates the lambda’s parameter list and the body
* __Lambda’s Body (blue area)__ - The expression that corresponds to the return value of the lambda (Of course, you can have multiple lines of code, as we’ll se later)
The following are the examples of lambda expressions supported in Java 8.

``` Java
// Return int with String
(String s) -> s.length()
    
// Return boolean with two Objects
(Student s1, Student s2) -> s1.getStdNo() > s2.getStdNo()
    
// No return value(void) with two int
(int x, int y) -> {
    System.out.println("Pyoduct of two numbers : ");
    System.out.println(x*y);
}

// Return int with no parameter
() -> 1
 
// Return String with String
(String name) -> { return "Hello " + name; }
```

[EX-01]

A return statement is implied in a lambda expression, so you don’t need to explicitly use a return statement (except when you have an expression with multiple lines that aren’t void).

# Where and how?

``` Java
// java.util.Comparator
@FunctionalInterface
public interface Comparator<T> {
    int compareTo(T o1, T o2);
}

...
inventory.sort((Fish f1, Fish f2) -> f1.getWeight().compareTo(a2.getWeight)));
...
```

[EX-02]

The code above is an example from the last post. This is an example of sorting the list by comparing the weights of the two fish. The `sort` method of `List` takes a `Comparator` as its parameter. This `Comparator` is a functional interface provided by Java itself. As you can see in the example, lambda expressions can be used instead of expecting a functional interface.

## Functional Interface
__Functional Interface__ is an interface that __has only one abstract method__ (even if there are several default methods, if only one abstract method exists, it is a functional interface).

``` Java
// java.util.function.Predicate
@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);
}

public static List<T> filter(List<T> list, Predicate<T> p) {
    List<T> result = new ArrayList<>();
    for(T e : list)
        if(p.test(e))
            result.add(e);
    return result;
}
```

[EX-03]

As you can see from `[EX-02]` and `[EX-03]`, `Comparator` and `Predicate` are functional interfaces with only one abstract method (`compareTo`, `test`). Since they were functional interfaces, we could use lambda expressions instead of implemented object.

So what can we do with a functional interface? We can pass an abstract method implementation of a functional interface directly to a lambda expression, so the entire expression can be treated as an instance of a functional interface.

``` Java

// java.lang.Runnable
@FunctionalInterface
public interface Runnable(){
    void run();
}

// Assign lambda expression to variable
Runnable lambda = () -> System.out.println("I'm lambda expression!");

// Using anonymous classes
Runnable anonymous = new Runnable() {
    public void run() {
        System.out.println("I'm anonymous class!");
    }
}

public static void process(Runnable r) {
    r.run();
}

...
// "I'm lambda expression!"
process(lambda);
// "I'm anonymous class!"
process(anonymous);
// "I'm lambda expression too!"
process(() -> "I'm lamda expression too!");
...
```

[EX-04]

I mentioned earlier that a lambda expression can be used like a functional interface. This is because the method signature of the lambda expression is the same as the method signature of the target functional interface (required). `[EX-04]` shows that the method signature of `Runnable` is identical to the supplied lambda expression’s signature (`void` -> `void`). We call the method signature of functional interface: __Function Descriptor__. Therefore, if the function descriptors match, we can use lambda expressions freely!

# Java’s built-in functional interfaces
The `Predicate` and `Runnable` we used was one of the functional interfaces built into Java. In addition to this, I’ll introduce some of the more popular functional interfaces among the functional interfaces provided by java.

## Predicate
The `java.util.function.Predicate<T>` interface defines an abstract method named `test`, and test returns a `boolean` with an object of the generic type `T` as an argument. The following example shows how to filter only objects that are in the third grade by taking a `Student` object as an argument.

``` Java

// java.util.function.Predicate<T>
@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);
}

public static <T> List<T> filter(List<T> list, Predicate<T> p) {
    List<T> result = new ArrayList<>();
    for(T e : list)
        if(p.test(e))
            result.add(s);
    return result;
}

...
List<Student> thirdGrade = filter(students, (Student s) -> s.getGrade() == 3); 
...
```

If you look at the Javadoc Specification for the `Predicate` interface, there are also methods like `and` and `or`. This will be covered in Part 2 Utilization.

## Consumer
The `java.util.function.Consumer<T>` interface defines an abstract method named `accept`, and returns `void` with an object of the generic type `T` as an argument. The following example uses lambda to print the name of `Student` object.

``` Java
// java.util.function.Consumer<T>
@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
}

public static <T> void<T> forEach(List<T> list, Consumer<T> c) {
    for(T e : list)
        c.accept(e);
}

...
forEach(students, (Student s) -> System.out.println(s.getName())); 
...
```

## Supplier
The `java.util.function.Supplier<T>` interface defines an abstract method named `get`, and returns an object of the generic type `T` with no argument (`void`). The following example uses `forEach` and lambda to generate messages.

``` Java
// java.util.function.Supplier<T>
@FunctionalInterface
public interface Supplier<T> {
    T get();
}

public static <T> List<T> get(List<T> list, Supplier<T> s) {
    List<T> result = new ArrayList<>();
    for(T e : list)
        result.add(e + s.get());
    return result;
}

...
List<String> messages = get(attendees, () -> " attended today"); 
...
```

## Function
The `java.util.functions.Function<T, R>` interface defines an abstract method named `apply`, and returns an object of the generic type `R` with an object of the generic type `T` as an argument. The `Function` interface is useful for mapping input to output. The following example takes a `Character` `List` as an argument and converts it to an `Integer` `List`, which is an ASCII code value for each character.

``` Java

// java.util.function.Function<T, R>
@FunctionalInterface
public interface Function<T, R> {
    R apply(T t);
}

public static <T> List<T> map(List<T> list, Function<T, R> f) {
    List<T> result = new ArrayList<>();
    for(T e : list)
        result.add(f.apply(e));
    return result;
}

...
List<Integer> ASCII = map(characters, (Character c) -> c.getNumericValue(c)); 
...
```

## Primitive type specialization
So far, we have looked at four functional types, `Predicate<T>`, `Consumer<T>`, `Supplier<T>` and `Function<T, R>`. All types of Java correspond to Reference type or Primitive type. However, we can __only use Reference types in generic parameters__ (eg, `T`, `R`) (in Java). Java provides auto boxing and unboxing functions that convert primitive types to reference types and vice versa, but this conversion process costs.

Java 8 provides a special version of the functional interface to avoid auto boxing when using primitive types as inputs and outputs. For example, in the example below, `IntPredicate` does not box a value of 1000, but `Predicate<Integer>` boxes a value of 1000 into an Integer object.

``` Java
// java.util.function.IntPredicate
@FunctionalInterface
public interface IntPredicate {
    boolean test(int t);
}

...
// No boxing, true
IntPredicate evenNumber = (int i) -> i % 2 == 0;
evenNumbers.test(1000);

// Boxing, false
Predicate<Integer> oddNumber = (Integer i) -> i % 2 == 1;
oddNumbers.test(1000);
...
```

If necessary, we can create a functional interface directly, and are a lot of built-in functional interfaces as above, so we can pick and use it when we need.

# Summary
Today we’ve took a look the basics of lambda with some examples. Today’s point is that lambda expressions are a kind of anonymous function, with no names, but with parameter lists, bodies, semantics, and exceptions. Also we can implement concise code with lambda expressions. But only can use lambda where functional interface expected.

In Part 2, we’ll look at some of the functional interfaces just like we’ve seen today, and some more intensive content.


-----
Reference : https://www.manning.com/books/java-8-in-action