package lambda.part1;

public class FunctionalInterface {

    private static Runnable lambda = () -> System.out.println("I'm lambda expression!");

    private static Runnable anonymous = new Runnable() {
        @Override
        public void run() {
            System.out.println("I'm anonymous class!");
        }
    };

    private static void process(Runnable r) {
        r.run();
    }

    public static void main(String[] args) {
        // I'm lambda expression!
        process(lambda);
        // I'm anonymous class!
        process(anonymous);
        // I'm lambda expression too!
        process(() -> System.out.println("I'm lambda expression too!"));
    }
}
