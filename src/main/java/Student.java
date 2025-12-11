public class Student extends Person implements CSVConvertible {
    private String grade;
    public Student(){
        super();
        this.grade="N";
    }

    public String getGrade() {return grade;}
    public void setGrade(String grade) {this.grade = grade;}

    public Student(int id, String name, int age, String grade){
        super(id, name, age);
        this.grade=grade;
    }

    @Override
    public void displayInfo() {
        System.out.printf("""
                ==============Student info==============
                student name: %s
                student id: %s
                student age: %d
                student grade: %s %n
                ----------------------------------------
                """,name, id, age, grade);
    }

    @Override
    public String toString() {
        return  super.toString() +
                 '\'' +"grade='" + grade;
    }

    @Override
    public String toCSV() {
        return getId() + "," + getName() + "," + getAge() + "," + getGrade();
    }
    //parsing the csv file data
    public static Student fromCSV(String csvLine) {
        // separating the line's elements
        String[] parts = csvLine.split(",");
        //parsing
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        int age = Integer.parseInt(parts[2]);
        String grade = parts[3];
        return new Student(id, name, age, grade);
    }
}