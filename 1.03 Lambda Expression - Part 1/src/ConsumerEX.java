package lambda.part1;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerEX {

    private static List<Student> students = Arrays.asList(new Student("Anna", 1, "Computer", 180034), new Student("David", 3, "Computer", 160101));

    public static <T> void forEach(List<T> list, Consumer<T> c) {
        for(T e : list)
            c.accept(e);
    }

    public static void main(String[] args) {
        forEach(students, (Student s) -> System.out.println(s.getName()));
    }

}
