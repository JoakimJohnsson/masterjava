package se.johnsson.joakim.oop;

import java.time.LocalDate;

// This Class have abstract methods, so it needs to be declared abstract.
// Abstract classes cannot be instantiated, but they can be subclassed.
// It's meant to be a base Class, for other Employee types.
public abstract class Employee {
    public static final String DEFAULT_NAME = "Unknown";
    // Static = Class level attribute (always the same for all instances of this class)
    // Used here to keep track of what id to set for the next instance of the class.
    private static int nextId;

    // These are instance variables - different for each instance of the class
    private Integer id;
    private String name;
    private LocalDate hireDate;

    public Employee() {
        // 'this' keyword used as a method. A way for one constructor to invoke another in the same class that matches
        // it's signature.. It Points to the constructor below, that takes a name as an argument.
        // The correct way to reference this constant from outside of this class would, of course, be Employee.DEFAULT_NAME.
        this(DEFAULT_NAME);
    }

    public Employee(String name) {
        id = nextId++;
        this.name = name;
        hireDate = LocalDate.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Many Employee types will have their own way to calculate pay.. This method is needed here now, to have the
    // opportunity to loop over all Employees later to get their pay, without having to resort to their type.
    // An abstract method is a method that is declared without an implementation (without braces, and followed by a
    // semicolon). If a class includes abstract methods, then the class itself must be declared abstract.
    // Child classes MUST override this method.
    public abstract double getPay();

    // Whenever you want to print an instance of this Class to see what data it contains. Use an overridden toString()
    // method to control how it will be displayed.
    @Override
    public String toString() {
        return String.format("Employee{id=%d}, name='%s', hireDate=%s}", id, name, hireDate);
    }
}
