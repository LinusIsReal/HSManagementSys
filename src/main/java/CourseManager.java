import java.util.ArrayList;
import java.util.List;

public class CourseManager {
    private List<Course> courses;

    public CourseManager() {
        courses = new ArrayList<>();
    }
    public List<Course> getAllCourses() {
        return courses;
    }
    public Course findCourse(String code){
        for (Course c:courses){
            if (c.getCourseCode().equals(code)) return c;
        }
        return null;
    }
    public boolean addCourse (Course c){
        if (findCourse(c.getCourseCode())!=null){
            System.out.println("The course "+c.getCourseTitle()+" already exists");
            return false;
        }
        courses.add(c);
        return true;
    }
    public boolean deleteCourse(String code){
        var c = findCourse(code);
        if (c!=null){
            courses.remove(c);
            return true;
        }
        System.out.println("A course with the code: "+code+" does not exist");
        return false;
    }
    public boolean assignTeacherToCourse(String courseCode, Teacher t){
        var c = findCourse(courseCode);
        if (c!=null){
            c.setTeacher(t);
            return true;
        }
        System.out.println("Course not found");
        return false;
    }
    public boolean enrollStudentInCourse(String code, Student s){
        var c = findCourse(code);
        if (c!=null){
            c.enrollStudent(s);
            return true;
        }
        System.out.println("Course not found");
        return false;
    }
    public void saveCourses(){
        FileUtil.saveData("courses.txt",getAllCourses());
    }

    public void loadCourses(){
        List<String> lines = FileUtil.loadData("courses.txt");
        courses.clear();
        for (String line: lines){
            courses.add(Course.fromCSV(line));
        }
        System.out.println("Courses loaded");
    }
}
