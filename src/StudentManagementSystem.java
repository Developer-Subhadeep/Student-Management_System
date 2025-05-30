// StudentManagementSystem.java
import java.io.*;
import java.util.*;

class Student implements Serializable {
    private int id;
    private String name;
    private int age;
    private String course;

    public Student(int id, String name, int age, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }

    public int getId() { return id; }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setCourse(String course) { this.course = course; }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Age: " + age + " | Course: " + course;
    }
}

public class StudentManagementSystem {
    static Scanner scanner = new Scanner(System.in);
    static List<Student> students = new ArrayList<>();
    static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        loadData();
        while (true) {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> searchStudent();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();
                case 6 -> {
                    saveData();
                    System.out.println("Exiting... Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }

    static void addStudent() {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Course: ");
        String course = scanner.nextLine();

        students.add(new Student(id, name, age, course));
        System.out.println("Student added successfully.");
    }

    static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            students.forEach(System.out::println);
        }
    }

    static void searchStudent() {
        System.out.print("Enter ID to search: ");
        int id = scanner.nextInt();
        Student student = findStudentById(id);
        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    static void updateStudent() {
        System.out.print("Enter ID to update: ");
        int id = scanner.nextInt();
        Student student = findStudentById(id);
        if (student != null) {
            scanner.nextLine();
            System.out.print("Enter new Name: ");
            student.setName(scanner.nextLine());
            System.out.print("Enter new Age: ");
            student.setAge(scanner.nextInt());
            scanner.nextLine();
            System.out.print("Enter new Course: ");
            student.setCourse(scanner.nextLine());
            System.out.println("Student updated.");
        } else {
            System.out.println("Student not found.");
        }
    }

    static void deleteStudent() {
        System.out.print("Enter ID to delete: ");
        int id = scanner.nextInt();
        Student student = findStudentById(id);
        if (student != null) {
            students.remove(student);
            System.out.println("Student deleted.");
        } else {
            System.out.println("Student not found.");
        }
    }

    static Student findStudentById(int id) {
        for (Student s : students) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    static void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(students);
        } catch (IOException e) {
            System.out.println("Failed to save data.");
        }
    }

    static void loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (List<Student>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load data.");
        }
    }
}
