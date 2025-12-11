import java.util.ArrayList;
import java.util.List;

public class TeacherManager {
    private List<Teacher> teachers;
    public TeacherManager(){
        this.teachers= new ArrayList<>();
    }
    public Teacher findTeacher(int id){
        for (Teacher t:teachers){
            if (t.getId()==id) return t;
        }
        return null;
    }
    public void addTeacher(Teacher t){
        if (findTeacher(t.getId())!=null){
            System.out.println("Teacher with the id"+t.getId()+" already exist");
        }
        else {
            teachers.add(t);
            System.out.println("Teacher "+t.getName()+" has been added");
        }
    }
    public List<Teacher> getAllTeachers() {
        return teachers;
    }
    public boolean updateTeacher (int id, String newName, int newAge, String newSubject, double newSalary){
        var teacher = findTeacher(id);
        if (teacher!=null){
            teacher.setName(newName);
            teacher.setAge(newAge);
            teacher.setSubject(newSubject);
            teacher.setSalary(newSalary);
            return true;
        }
        return false;
    }
    public boolean deleteTeacher(int id){
        var teacher = findTeacher(id);
        if (teacher!= null){
            teachers.remove(teacher);
            return true;
        }
        return false;
    }
    public void saveTeachers(){
        FileUtil.saveData("teachers.txt", getAllTeachers());
    }
    public void loadTeachers(){
        List<String> lines = FileUtil.loadData("teachers.txt");
        teachers.clear();
        for (String line: lines){
            teachers.add(Teacher.fromCSV(line));
        }
        System.out.println("Teachers loaded");
    }
}