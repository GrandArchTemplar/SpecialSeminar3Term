package employee;

/**
 * Created by grandarchtemplar on 06/12/16.
 * This code may work
 */
public class Employee {
    private final String surname;
    private final String post;
    private final int salary;

    public String getSurname() {
        return surname;
    }

    public String getPost() {
        return post;
    }

    public int getSalary() {
        return salary;
    }

    public Employee(String surname, String post, int salary) {

        this.surname = surname;
        this.post = post;
        this.salary = salary;
    }
}
