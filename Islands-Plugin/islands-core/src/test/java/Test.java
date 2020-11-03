import java.util.Arrays;
import java.util.List;

public class Test {

    @org.junit.Test
    public void test() {
        List<Number> list = Arrays.asList(1, 2, 3, -5);
        Number integer = biggestChange(list);
        System.out.println(integer);
    }

    public Number biggestChange(List<Number> list) {
        return list.stream()
                .map(t -> Math.abs(t.doubleValue()))
                .max(Double::compareTo)
                .get();
    }

}

