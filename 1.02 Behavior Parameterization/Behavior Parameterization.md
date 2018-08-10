# Java 8 :: Behavior parameterization

No matter what we do, our client requirements change all the time. Changing requirements are unavoidable in software engineering. For example, imagine that a fisherman has an application that helps make inventory check up easier. He will say, “I want to find all of the mackerel.” But one day later, “I want to find all of the fish that over 500g.” And another day passes, “I want to find all of the fish that over 500g and is mackerel.” How should we deal with these ever-changing requirements? It would be nice if our engineering costs could be minimized. In addition, new features should be easy to implement and maintain a long-term perspective.

We can easily respond to changing requirements using __behavior parameterization__. It means a block of code that has not yet decided how to execute. This block of code is later called by the program. In other words, execution of the block of code is delayed later. For example, we can pass this block of code to the parameter of a method that will be executed later. As a result, the behavior of the method is parameterized according to the block of code. For example, let’s suppose you implement the following method when processing a collection:

* Can perform ‘some action’ on all elements of the list
* Can perform ‘any other action’ after completing list-related tasks
* Can perform ‘another action’ when an error occurs
Using behavior parameterization, we can perform various functions like this.

First, let’s look at an example of how to implement code to flexibly respond to changing requirements. Some readers have already used behavior parameterization patterns to sort the List using the classes and interfaces included in the existing Java API, to filter filenames, to execute code blocks in threads, or to handle GUI events. Adding parameterization will increase useless code, but we will solve this problem with the __Java 8 lambda expression__.

# Changing requirements

``` java
public static List<Fish> filterMackerel(List<Fish> inventory){
    List<Fish> result = new ArrayList<>();
    for(Fish fish : inventory)
        if("mackerel".equals(fish.getName()))
            result.add(fish);
    return result;
}
```

This code is for filtering mackerel. And `"mackerel".equals(fish.getName())` is the points of this code. But suddenly, the fisherman changes his mind to filter flatfish too. Then how should we fix it? Maybe we can copy the method, create a new method named filterFlatFishes, and change the if statement’s condition to flatfish. In this way, we can filter flatfish but cannot respond appropriately when fisherman requires additional filter conditions. But don’t worry, just remember that there are a great rule for this kind of situation: __‘Implement similar code and then abstract’__

# Parameterize the name

``` java
public static List<Fish> filterFishesByName(List<Fish> inventory, String name){
    List<Fish> result = new ArrayList<>();
    for(Fish fish : inventory)
        if(fish.getName().equals(name))
            result.add(fish);
    return result;
}
```

Now the fisherman will be satisfied. We can call the method like this:

``` java
List<Fish> mackerels = filterFishesByName(inventory, "mackerel");
List<Fish> flatfishes = filterFishesByName(inventory, "flatfish");
```

Isn’t that easy? But suddenly the fisherman came back and said, “It would be great if we could distinguish a heavy fish in addition to the name, and the fishes weighing more than 500g are called heavy fishes.” Then we will think: ‘Then we can create another method to filter by weight!’ Hmm, let’s think about it for a moment. What happens if filter conditions such as color, origin, etc. are added instead of name and weight? Is it impossible to filter many of these conditions at once?

# Behavior Parameterization
We’ve found that there was a need for a way to flexibly respond to changing requirements instead of adding parameters or methods through the previous example. We can determine the selection conditions as follows: Returns a boolean value based on some attribute of the fish. And we call this behavior: __a predicate__. Let’s define the interface that determines the selection criteria.

``` java
public interface FishPredicate {
    boolean test(Fish fish);
}

public class FishOriginKoreaPredicate implements FishPredicate {
    @Override
    public boolean test(Fish fish){
        return fish.getOrigin().equals("Korea");
    }
}

public class FishNameFlatFishPredicate implements FishPredicate {
    @Override
    public boolean test(Fish fish){
        return fish.getName().equals("flatfish");
    }
}
```

Now we can expect the filter method will behave differently based on the above conditions. And we call this a Strategy design pattern. Strategy design pattern is a technique defines an algorithm family that encapsulates each algorithm (called a strategy) and then selects algorithms at runtime. In our example, `FishPredicate` is an algorithm family and `FishOriginKoreaPredicate` and `FishNameFlatFishPredicate` are strategies. Then how does `FishPredicate` perform different actions? In `filterFishes`, we need to modify the method to get the `FishPredicate` object and check the condition of the fish. Just like this, we can __perform various operation internally by receiving__ various actions (or strategies) with __parameters__ using __behavior parameterization__.

# Filter by abstract condition

``` java
public static List<Fish> filterFishes(List<Fish> inventory, FishPredicate p) {
    List<Fish> result = new ArrayList<>();
    for(Fish fish : inventory)
        if(p.test(fish))
            result.add(fish);
    return result;
}
```

The above code shows `FishPredicate` applied to the filter method. WOW! It’s more flexible and easier to read than the first code, and easier to use! Now we can create various `FishPredicate` as we need and pass them to the `filterFishes` method. For instance, if the fisherman asks us to find a fish that weighs over 700g, we can just create a class that implements `FishPredicate` properly. Finally, we have flexible code that can handle all the changes related to the fish’s attributes!

But let’s think a little more. Still, our filter method __only accepts objects__ as arguments, so we need to pass the `test` method wrapped in a `Predicate` object. __It means every time a condition is added, a new class must be implemented__. Then what can we do to solve this?

# Java 8 Lambda Expressions
No one wants to use complex features or concepts. As I said before, writing an implementation class each time is a very cumbersome task and a waste of time.

## Anonymous Class
Before we using lambda expressions, let’s talk about __Anonymous class__. Have you ever heard of it once? Java provides a technique called anonymous classes that allow you to __concurrently declare and instantiate a class together__. It is __similar to Local class__ (a class declared inside a block). Anonymous class allows you to create and use the required implementation instantly (Anonymous classes are often used when implementing GUI event handler objects).

``` java
List<Fish> koreanFishes = filterFishes(inventory, new FishPredicate() {
    @Override
    public boolean test(Fish fish){
        return "korea".equals(fish.getOrigin());
    }
});
```

Of course, we can also pass anonymous classes as parameters as above. But we still can see the shortage, right? The amount of code is less than when implementing the `Predicate` object directly, but the anonymous class still takes up a lot of space. The mouth-filling code is bad. Mouth-filling code is time-consuming to implement and maintain. It also takes away the pleasure of reading and gets out of had from developers. The code is good code that can understand at once. Although we can use the anonymous class to reduce the process of declaring several classes that implement the interface, it is still unsatisfactory. After all, we eventually need to implement a method (like the `test` method of `Predicate`) that creates an object and explicitly defines a new behavior.

## Lambda Expressions

``` java
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

With lambda expression, our example can be generalized as above. Now we can filter everything we want. It means that we can now filter orange or peach!

``` java
List<Peach> heavyPeaches = filter(inventory, (Peach peach) -> peach.getWeight() > 150);
List<Orange> greenOranges = filter(inventory, (Orange orange) -> "green".equals(orange.getColor()));
```

That’s so cool, right? So we were able to catch both flexibility and simplicity. It would not have been possible without Java 8 (Let’s learn more about the lambda expression here). Then, from now on, let’s look at a few more practical examples of behavior parameterization using a lambda expression.

# Practical Examples
So far, behavior parameterization has proven to be a useful pattern that easily adapts to changing requirements. The behavioral parameterization pattern encapsulates an action (with a piece of code) and passes it to a method to parameterize the behavior of the method. We will look at three examples, such as sorting with `Comparator`, running code blocks with `Runnable`, and handling GUI events so that we can be better understand the concept of code passing.

## Sort with Comparator
Sorting collections is a repetitive programming task. For example, at first, the fisherman would say he wants to sort fish by name. But soon he may want to change his mind and sort by weight. In fact, this is a common occurrence. Therefore, there is an urgent need for developers to perform various sorting operations that can easily respond to changing requirements.

The `List` in Java 8 includes the `sort` method. We can parameterize the behavior or sort using a `java.util.Comparator` object with the following interface:

``` java
// java.util.Comparator
public interface Comparator<T> {
    public int compare(T o1, T o2);
}

...
inventory.sort((Fish f1, Fish f2) -> f1.getWeight().compareTo(a2.getWeight)));
...
```

We can implement the `Comparator` to vary the behavior of the sort method. If we use the above code, we can sort the list in descending order of weight. If the fisherman’s requirements change, simply implement the appropriate `Comparator` and pass it to the sort method.

## Run code blocks with Runnable
In Java, we can use the `Runnable` interface to specify a block of code to be executed later in a thread. As you can see in the code below, the result of executing the code block is `void`.

``` java
//java.lang.Runnable
public interface Runnable {
    public void run();
}  

...
Thread t = new Thread(() -> System.out.println("Hello Behavior Parameterization!")); 
...
```

As I am, and many developers have used anonymous classes, lambda expressions can be used to implement the code as concisely as above.

## Handle GUI Events
Generally, GUI programming works by performing operations corresponding to events such as mouse clicks and scrolling. First, let’s look at the example code.

``` java
Button button = new Button("Submit");
button.setOnAction(new EventHandler<ActionEvent>() {
    public void handle(ActionEvent event) {
        label.setText("Submitted");
    }
});
```

This is an implementation of an anonymous class that sets how JavaFX responds to events by passing an event handler to the `setOnAction` method. That is, the `EventHandler` parameterizes the behavior of the method with `setOnAction`. So this can also be concisely represented as a lambda expression:

``` Java
button.setOnAction((ActionEvent event) -> label.setText("Submitted"));
```

# Summary
The behavior parameterization enables separation of the behavior of the object, thereby enhancing the reusability of the code and creating a flexible API. Also, using lambda of functional programming can greatly improve readability. In the following post, let’s learn about the __lambda expression__ we used today.