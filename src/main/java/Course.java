import java.util.ArrayList;
import java.util.List;

public class Course implements CSVConvertible {
    protected String courseCode;
    protected String courseTitle;
    protected Teacher teacher;
    protected List<Student> enrollStudents;

    public Course(String courseCode, String courseTitle) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.enrollStudents = new ArrayList<>();
    }

    public String getCourseCode() {return courseCode;}
    public void setCourseCode(String courseCode) {this.courseCode = courseCode;}

    public String getCourseTitle() {return courseTitle;}
    public void setCourseTitle(String courseTitle) {this.courseTitle = courseTitle;}

    public Teacher getTeacher() {return teacher;}
    public void setTeacher(Teacher teacher) {this.teacher = teacher;}

    public List<Student> getEnrollStudents() {return enrollStudents;}

    //    enrolling and unenrolling operations
    public void enrollStudent(Student student){
        if (!enrollStudents.contains(student)){ enrollStudents.add(student); }
    }
    public boolean unenrollStudent(Student student){return enrollStudents.remove(student);}


    public void displayInfo(){
        StringBuilder students = new StringBuilder(150);

        enrollStudents.forEach(s->{
            students.append("ID: ").append(s.getId()).append(" Name: ").append(s.getName()).append("\n");
        });

        var output =  String.format("""
                ==============course info==============
                Course code: %s
                Course title: %s
                Course teacher: %s
                Enrolled students: %s
                ---------------------------------------
                """,this.courseCode, this.courseTitle,this.teacher, students);
        System.out.println(output);
    }

    @Override
    public String toString() {
        var teacherName = teacher!=null? teacher.getName():"none";
        return "courseCode='" + courseCode + '\'' +
                ", courseTitle='" + courseTitle + '\'' +
                ", teacher=" + teacher;
    }

    @Override
    public String toCSV() {
        return courseCode+","+courseTitle+","+teacher.getName();
    }

    //parsing the csv file data
    public static Course fromCSV(String csvLine) {
        // separating the line's elements
        String[] parts = csvLine.split(",");
        //parsing
        String code = parts[0];
        String title = parts[1];
        return new Course (code, title);
    }
}
