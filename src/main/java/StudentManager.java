import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private List<Student> students;

    public StudentManager(){
        this.students= new ArrayList<>();
    }
    public boolean addStudent(Student student){
        if (!students.contains(student)){
            students.add(student);
            return true;
        }
        System.out.println("Student with the Id: "+student.getId()+" already exist!");
        return false;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public Student findStudentById(int id){
        for(Student s: students){
            if (s.getId()==id)  return s;
        }
        return null;
    }

    public boolean updateStudent(int id, String newName, int newAge, String newGrade){
        Student s = findStudentById(id);
        if (s==null){
            return false;
        }
        s.setName(newName);
        s.setAge(newAge);
        s.setGrade(newGrade);
        return true;
    }
    public boolean deleteStudent(int id){
        Student s= findStudentById(id);
        if (s==null)    return false;

        students.remove(s);
        return true;
    }
    public void saveStudents(){
        FileUtil.saveData("students.txt", getAllStudents());
    }
    public void loadStudents(){
        List<String> lines = FileUtil.loadData("students.txt");
        students.clear();
        for (String line: lines){
            students.add(Student.fromCSV(line));
        }
        System.out.println("Students loaded");
    }
}