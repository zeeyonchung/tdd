package sample2;

public class Person {

    String firstName;
    String middleName;
    String lastName;

    public Person(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String fullName() {
        return String.format("%s %s %s", firstName, middleName, lastName);
    }
}
