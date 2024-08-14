import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Course {
    String courseCode;
    String title;
    String description;
    int capacity;
    String schedule;
    int enrolled;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolled = 0;
    }

    public boolean hasAvailableSlots() {
        return enrolled < capacity;
    }

    public void enrollStudent() {
        if (hasAvailableSlots()) {
            enrolled++;
        }
    }

    public void dropStudent() {
        if (enrolled > 0) {
            enrolled--;
        }
    }
}

class Student {
    String studentId;
    String name;
    List<Course> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public void registerCourse(Course course) {
        if (course.hasAvailableSlots() && !registeredCourses.contains(course)) {
            registeredCourses.add(course);
            course.enrollStudent();
        } else {
            System.out.println("Cannot register for " + course.title + ". Either full or already registered.");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.remove(course)) {
            course.dropStudent();
        } else {
            System.out.println("Not registered in " + course.title);
        }
    }
}

public class CourseRegistrationSystem {
    private static Map<String, Course> courses = new HashMap<>();
    private static Map<String, Student> students = new HashMap<>();

    public static void main(String[] args) {
        // Sample data
        addCourse("CS101", "Introduction to Computer Science", "Basic concepts in computer science", 30, "MWF 9-10 AM");
        addCourse("MATH101", "Calculus I", "Introduction to calculus", 25, "TTh 10-11:30 AM");
        addStudent("S001", "Alice Johnson");
        addStudent("S002", "Bob Smith");

        // Menu-driven interface
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. List Courses");
            System.out.println("2. Register Student");
            System.out.println("3. Drop Course");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    listCourses();
                    break;
                case 2:
                    System.out.print("Enter student ID: ");
                    String studentId = scanner.nextLine();
                    System.out.print("Enter course code: ");
                    String courseCode = scanner.nextLine();
                    registerStudentInCourse(studentId, courseCode);
                    break;
                case 3:
                    System.out.print("Enter student ID: ");
                    studentId = scanner.nextLine();
                    System.out.print("Enter course code: ");
                    courseCode = scanner.nextLine();
                    dropStudentFromCourse(studentId, courseCode);
                    break;
                case 4:
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addCourse(String courseCode, String title, String description, int capacity, String schedule) {
        courses.put(courseCode, new Course(courseCode, title, description, capacity, schedule));
    }

    private static void addStudent(String studentId, String name) {
        students.put(studentId, new Student(studentId, name));
    }

    private static void listCourses() {
        for (Course course : courses.values()) {
            System.out.println(course.courseCode + ": " + course.title + " - " + course.description +
                    " (" + course.enrolled + "/" + course.capacity + ") - " + course.schedule);
        }
    }

    private static void registerStudentInCourse(String studentId, String courseCode) {
        Student student = students.get(studentId);
        Course course = courses.get(courseCode);
        if (student != null && course != null) {
            student.registerCourse(course);
        } else {
            System.out.println("Invalid student ID or course code.");
        }
    }

    private static void dropStudentFromCourse(String studentId, String courseCode) {
        Student student = students.get(studentId);
        Course course = courses.get(courseCode);
        if (student != null && course != null) {
            student.dropCourse(course);
        } else {
            System.out.println("Invalid student ID or course code.");
        }
    }
}