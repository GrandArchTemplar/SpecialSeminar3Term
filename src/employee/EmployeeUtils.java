package employee;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;


/**
 * Created by grandarchtemplar on 06/12/16.
 * This code may work
 */
public class EmployeeUtils {
    public static int maxSalary(List<Employee> list) {
        return list
                .stream()
                .max(comparing(Employee::getSalary))
                .map(Employee::getSalary)
                .orElse(0);
    }

    public static int maxSalary1(List<Employee> list) {
        return list.stream().mapToInt(Employee::getSalary).max().orElse(0);
    }

    public static int maxSalary2(List<Employee> list) {
        return list.stream().map(Employee::getSalary).max(Integer::compareTo).orElse(0);
    }

    @Deprecated
    public static int maxSalary3(List<Employee> list) {
        return list.stream().map(Employee::getSalary).collect(minBy((i1, i2) -> i2 - i1)).orElse(0);
    }

    @Deprecated
    public static int maxSalary4(List<Employee> list) {
        return list
                .stream()
                .collect(minBy((Employee e1, Employee e2) -> e2.getSalary() - e1.getSalary()))
                .get()
                .getSalary();
    }

    public static int minSalary(List<Employee> list) {
        return list
                .stream()
                .min(comparing(Employee::getSalary))
                .map(Employee::getSalary)
                .orElse(0);
    }

    public static int minSalary1(List<Employee> list) {
        return list.stream().mapToInt(Employee::getSalary).min().orElse(0);
    }

    public static int minSalary2(List<Employee> list) {
        return list.stream().map(Employee::getSalary).min(Integer::compareTo).orElse(0);
    }

    @Deprecated
    public static int minSalary3(List<Employee> list) {
        return list.stream().map(Employee::getSalary).collect(maxBy((i1, i2) -> i2 - i1)).orElse(0);
    }

    @Deprecated
    public static int minSalary4(List<Employee> list) {
        return list
                .stream()
                .collect(maxBy((Employee e1, Employee e2) -> e2.getSalary() - e1.getSalary()))
                .get()
                .getSalary();
    }

    public static double averageSalary(List<Employee> list) {
        return list.stream().mapToInt(Employee::getSalary).average().orElse(0);
    }

    public static double averageSalary1(List<Employee> list) {
        return list.stream().collect(averagingDouble(Employee::getSalary));
    }

    public static Map<String, Integer> jobCount(List<Employee> list) {
        return list.stream().collect(groupingBy(Employee::getPost, summingInt(e -> 1)));
    }

    public static Map<String, Integer> jobCount1(List<Employee> list) {
        return list
                .stream()
                .collect(groupingBy(
                        Employee::getPost,
                        reducing(0, e -> 1, Integer::sum)));
    }

    public static Map<Character, Integer> abc(List<Employee> list) {
        return list.stream().collect(groupingBy(e -> e.getSurname().charAt(0), summingInt(e -> 1)));
    }


    public static Map<Character, Integer> abc1(List<Employee> list) {
        return list
                .stream()
                .collect(groupingBy((Employee e) -> e.getSurname().charAt(0),
                        reducing(0, e -> 1, Integer::sum)));
    }



    public static List<Employee> getEmployee(String filename) {
        try {
            return Files.lines(Paths.get(filename))
                    .map(s -> s.split("[,\\s]+"))
                    .map(s -> new Employee(s[0], s[1], Integer.parseInt(s[2])))
                    .collect(toList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
