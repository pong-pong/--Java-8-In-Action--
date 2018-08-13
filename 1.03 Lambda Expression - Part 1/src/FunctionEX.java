package lambda.part1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionEX {

    private static List<Character> characters = Arrays.asList('a', 'b', 'c');

    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for (T e : list)
            result.add(f.apply(e));
        return result;
    }

    public static void main(String[] args) {
        List<Integer> ASCII = map(characters, (Character c) -> c.getNumericValue(c));
    }

}
