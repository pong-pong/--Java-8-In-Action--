package lambda.part1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class PredicateEX {

    private static List<Student> students = Arrays.asList(new Student("Anna", 1, "Computer", 180034), new Student("David", 3, "Computer", 160101));

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for (T e : list)
            if (p.test(e))
                result.add(e);
        return result;
    }

    public static void main(String[] args) {
        List<Student> thirdGrade = filter(students, (Student s) -> s.getGrade() == 3);
    }

}
