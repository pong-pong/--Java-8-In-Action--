import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BehaviorParameterization {

    public static List<Fish> inventory = new ArrayList<>();

    // Filter fish named "mackerel"
    public static List<Fish> filterMackerel(List<Fish> inventory) {
        List<Fish> result = new ArrayList<>();
        for (Fish fish : inventory)
            if ("mackerel".equals(fish.getName()))
                result.add(fish);
        return result;
    }

    // Filter fish by name(parameter)
    public static List<Fish> filterFishesByName(List<Fish> inventory, String name) {
        List<Fish> result = new ArrayList<>();
        for (Fish fish : inventory)
            if (name.equals(fish.getName()))
                result.add(fish);
        return result;
    }

    // Filter fish with FishPredicate
    public static List<Fish> filterFishes(List<Fish> inventory, FishPredicate p) {
        List<Fish> result = new ArrayList<>();
        for (Fish fish : inventory)
            if (p.test(fish))
                result.add(fish);
        return result;
    }

    // Filter Korean fishes with Anonymous Class
    List<Fish> koreanFishesWithAC = filterFishes(inventory, new FishPredicate() {
        @Override
        public boolean test(Fish fish) {
            return "Korea".equals(fish.getOrigin());
        }
    });

    // Generalized fish filtering method with Predicate
    public static List<Fish> filter(List<Fish> inventory, Predicate<Fish> p) {
        List<Fish> result = new ArrayList<>();
        for (Fish fish : inventory)
            if (p.test(fish))
                result.add(fish);
        return result;
    }

    // Filter Korean fish with Lambda Expression
    List<Fish> koreanFishesWithLE = filterFishes(inventory, (Fish fish) -> "Korea".equals(fish.getOrigin()));

    public static void main(String[] args){
        // Sort by weigh ascending
        inventory.sort((Fish f1, Fish f2) -> f1.getWeight().compareTo(f2.getWeight()));

        // Run code blocks with Runnable
        Thread t = new Thread(() -> System.out.println("Hello, Behavior Parameterization!"));
        t.run();

        Button button = new Button("Submit");
        Label label = new Label("Waiting...");

        // Handle GUI event with Anonymous Class
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                label.setText("Submitted");
            }
        });

        // Handle GUI event with Lambda Expression
        button.setOnAction((ActionEvent event) -> label.setText("Submitted"));
    }



}
