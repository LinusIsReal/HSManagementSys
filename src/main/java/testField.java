import java.util.ArrayList;
import java.util.List;

public class testField {
    public static void main(String[] args) {
        Student t = new Student(4545, "Mustafa",40, "A");
        Student t2 = new Student(4545, "Ahmed",46, "B");

        List<Student> teachers = new ArrayList<>();
        teachers.add(t);
        if (teachers.contains(t2)){
            System.out.println("already exist");
        }
        else {
            System.out.println("new teacher added");
        }
    }
}
