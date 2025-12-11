import java.util.Objects;

public abstract class Person {
    protected int id;
    protected String name;
    protected int age;

    public Person(){
        this(0,"empty",-1);
    }
    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    // getters
    public int getId() { return id; }
    public String getName() { return name;}
    public int getAge() {return age;}
    // setters
    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setAge(int age) {this.age = age;}


    // to use method array's method contain with the instances of person
    @Override
    public boolean equals(Object obj) {
        if (this==obj) return true;
        if (!(obj instanceof Person)) return false;

        return Objects.equals(this.id,((Person) obj).id);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


    public abstract void displayInfo();

    @Override
    public String toString() {
        return "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age;
    }
}
