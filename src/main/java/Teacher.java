public class Teacher extends Person implements CSVConvertible{
    private String subject;
    private double salary;

    public Teacher() {
        this.subject = "empty";
        this.salary = -1;
    }
    public Teacher(int id, String name, int age, String subject, double salary) {
        super(id, name, age);
        this.subject = subject;
        this.salary = salary;
    }

    // getters and setters
    public String getSubject() {return subject;}
    public void setSubject(String subject) {this.subject = subject;}
    public double getSalary() {return salary;}
    public void setSalary(double salary) {this.salary = salary;}

    //    methods
    @Override
    public void displayInfo() {
        System.out.printf("""
                ==============Teacher info==============
                Teacher name: %s
                Teacher id: %s
                Teacher age: %d
                Teacher subject: %s
                Teacher salary: %.2f
                ----------------------------------------
                """, name, id, age, subject, salary);
    }

    @Override
    public String toString() {
        return super.toString()+
                "subject='" + subject + '\'' +
                ", salary=" + salary;
    }

    @Override
    public String toCSV() {
        return getId()+","+getName()+","+getAge()+","+getSubject()+","+getSalary();
    }

    //parsing the csv file data
    public static Teacher fromCSV(String csvLine) {
        // separating the line's elements
        String[] parts = csvLine.split(",");
        //parsing
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        int age = Integer.parseInt(parts[2]);
        String subject = parts[3];
        double salary = Double.parseDouble(parts[4]);
        return new Teacher(id, name, age, subject, salary);
    }
}