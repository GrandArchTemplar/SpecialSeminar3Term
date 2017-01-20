package test;

import employee.Employee;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static employee.EmployeeUtils.*;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;
import static org.junit.runners.MethodSorters.NAME_ASCENDING;

/**
 * Created by grandarchtemplar on 06/12/16.
 * This code may work
 */

@FixMethodOrder(NAME_ASCENDING)
public class EmployeeUtilsTest {
    private List<Employee> list;
    private int max;
    private int min;
    private double average;
    @Before
    public void setUp() throws Exception {
        list = getEmployee("Employee.txt");
        list.sort(comparingInt(Employee::getSalary));
        min = list.get(0).getSalary();
        list.sort((e1, e2) -> e2.getSalary() - e1.getSalary());
        max = list.get(0).getSalary();
        average = 0;
        list.forEach(e -> average += e.getSalary());
        average /= list.size();
    }

    public void overheat() {
        IntStream.range(0, 10000).boxed().parallel().map(i -> i * i).forEach(i -> {
        });
    }

    @Test
    public void _overclock() throws Exception {
        int[] i = {0};
        IntStream.generate(() -> i[0]++).limit(1345).forEach(t -> overheat());
    }

    @Test
    public void maxTest() throws Exception {
        assertEquals(max, maxSalary(list));
        assertEquals(max, maxSalary1(list));
        assertEquals(max, maxSalary2(list));
        assertEquals(max, maxSalary3(list));
        assertEquals(max, maxSalary4(list));
    }

    @Test
    public void minTest() throws Exception {
        assertEquals(min, minSalary(list));
        assertEquals(min, minSalary1(list));
        assertEquals(min, minSalary2(list));
        assertEquals(min, minSalary3(list));
        assertEquals(min, minSalary4(list));
    }

    @Test
    public void averageTest() throws Exception {
        assertEquals(average, averageSalary(list), 1.0E-9);
        assertEquals(average, averageSalary1(list), 1.0E-9);
    }

    @Test
    public void jobCountTest() throws Exception {
        assertEquals("Manager=1;Programmer=1;ex-CEO=1;nobody=1",
                jobCount(list).entrySet()
                        .stream()
                        .map(Map.Entry::toString)
                        .sorted()
                        .collect(joining(";")));
        assertEquals("Manager=1;Programmer=1;ex-CEO=1;nobody=1",
                jobCount1(list).entrySet()
                        .stream()
                        .map(Map.Entry::toString)
                        .sorted()
                        .collect(joining(";")));
    }

    @Test
    public void abcTest() throws Exception {
        assertEquals("B=2;E=1;S=1",
                abc(list).entrySet()
                        .stream()
                        .map(Map.Entry::toString)
                        .sorted()
                        .collect(joining(";")));
        assertEquals("B=2;E=1;S=1",
                abc1(list).entrySet()
                        .stream()
                        .map(Map.Entry::toString)
                        .sorted()
                        .collect(joining(";")));
    }
}