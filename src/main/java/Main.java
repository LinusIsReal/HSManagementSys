import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {
    public StudentManager studentManager = new StudentManager();
    public TeacherManager teacherManager = new TeacherManager();
    public CourseManager courseManager = new CourseManager();

    private BorderPane root = new BorderPane();
    private ObservableList<Course> courseList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // loading data
        studentManager.loadStudents();
        teacherManager.loadTeachers();
        courseManager.loadCourses();

        BorderPane root = new BorderPane();
        Node dashboardView = createDashboardView();
        Node studentView = createStudentView();
        Node teacherView = createTeacherView();
        Node courseView = createCourseView();

        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(15));
        sidebar.setStyle("-fx-background-color: #2c3e50;");
        sidebar.setPrefWidth(150);
        
        Button btnDash = createNavButton("Dashboard");
        Button btnStud = createNavButton("Students");
        Button btnTeach = createNavButton("Teachers");
        Button btnCourse = createNavButton("Courses");

        btnDash.setOnAction(e-> root.setCenter(dashboardView));
        btnStud.setOnAction(e-> root.setCenter(studentView));
        btnTeach.setOnAction(e-> root.setCenter(teacherView));
        btnCourse.setOnAction(e-> root.setCenter(courseView));

        sidebar.getChildren().addAll(btnDash, btnStud, btnTeach,btnCourse);
        root.setLeft(sidebar);
        root.setCenter(dashboardView);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("High School Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @Override
    public void stop(){
        System.out.println("Closing application");
        studentManager.saveStudents();
        teacherManager.saveTeachers();
        courseManager.saveCourses();
    }


    private Button createNavButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(2000);
        btn.setPrefHeight(40);
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-alignment: CENTER_LEFT;");
        btn.setOnMouseEntered(e-> btn.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-size: 14px; -fx-alignment: CENTER_LEFT;"));
        btn.setOnMouseExited(e-> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-alignment: CENTER_LEFT;"));

        return btn;
    }
    private Node createDashboardView() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        content.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("School Management System");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD,24));
        Label statsLabel = new Label("System Statistics:");
        statsLabel.setFont(Font.font("Arial", 16));

        Label lblStudents = new Label("Total Students: "+ studentManager.getAllStudents().size());
        Label lblTeachers = new Label("Total Teachers: "+ teacherManager.getAllTeachers().size());
        Label lblCourses = new Label("Total Courses: "+ courseManager.getAllCourses().size());

        lblStudents.setStyle("-fx-text-fill: blue; -fx-font-size: 14px;");
        lblTeachers.setStyle("-fx-text-fill: green; -fx-font-size: 14px;");
        lblCourses.setStyle("-fx-text-fill: purple; -fx-font-size: 14px;");

        content.getChildren().addAll(titleLabel, statsLabel, lblStudents,lblTeachers, lblCourses);
        return content;

    }

    private Node createStudentView() {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));

        // table
        TableView<Student> table = new TableView<>();

        TableColumn<Student, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Student, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, Integer> colAge = new TableColumn<>("Age");
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Student, String> colGrade = new TableColumn<>("Grade");
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));

        table.getColumns().addAll(colId, colName, colAge, colGrade);
        table.setItems(FXCollections.observableArrayList(studentManager.getAllStudents()));

        //form to add, delete and edit
        TextField tfId = new TextField(); tfId.setPromptText("ID");
        TextField tfName = new TextField(); tfName.setPromptText("Name");
        TextField tfAge = new TextField(); tfAge.setPromptText("Age");
        TextField tfGrade = new TextField(); tfGrade.setPromptText("Graded");

        HBox formLayout = new HBox(10, tfId, tfName, tfAge,tfGrade);
        formLayout.setPadding(new Insets(10));

        Button btnAdd = new Button("Add");
        Button btnUpdate = new Button("Update");
        Button btnDelete = new Button("Delete");
        // adding
        btnAdd.setOnAction(e-> {
            try{
                int id = Integer.parseInt(tfId.getText());
                String name = tfName.getText();
                int age = Integer.parseInt(tfAge.getText());
                String grade = tfGrade.getText();

                studentManager.addStudent(new Student(id,name, age, grade));
                table.setItems(FXCollections.observableArrayList(studentManager.getAllStudents()));
                tfId.clear(); tfName.clear(); tfAge.clear(); tfGrade.clear();
            } catch (Exception ex){ System.out.println("Invalid Input"); }
        });
        // updating
        btnUpdate.setOnAction(e->{
            try{
                int id = Integer.parseInt(tfId.getText());
                String name = tfName.getText();
                int age = Integer.parseInt(tfAge.getText());
                String grade = tfGrade.getText();
                studentManager.updateStudent(id, name, age, grade);
                table.setItems(FXCollections.observableArrayList(studentManager.getAllStudents()));
                table.refresh();
                tfId.clear(); tfName.clear(); tfAge.clear(); tfGrade.clear();
            } catch (Exception ex){ System.out.println("Invalid Input"); }
        });
        // deleting
        btnDelete.setOnAction(e->{
            try{
                int id = Integer.parseInt(tfId.getText());
                studentManager.deleteStudent(id);
                table.setItems(FXCollections.observableArrayList(studentManager.getAllStudents()));
                tfId.clear(); tfName.clear(); tfAge.clear(); tfGrade.clear();
            }catch (Exception ex){
                System.out.println("Invalid Input");
            }
        });
        HBox buttonLayout = new HBox(10,btnAdd, btnUpdate,btnDelete);
        buttonLayout.setPadding(new Insets(10));
        VBox bottomPanel = new VBox(formLayout, buttonLayout);
        layout.setCenter(table);
        layout.setBottom(bottomPanel);
        return layout;
    }


    private Node createTeacherView() {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));

        // table
        TableView<Teacher> table = new TableView<>();

        TableColumn<Teacher, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new  PropertyValueFactory<>("id"));
        TableColumn<Teacher,String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Teacher,Integer> colAge = new TableColumn<>("Age");
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        TableColumn<Teacher,String> colSubject = new TableColumn<>("Subject");
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        TableColumn<Teacher,Double> colSalary = new TableColumn<>("Salary");
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        table.getColumns().addAll(colId,colName,colAge,colSubject,colSalary);
        table.setItems(FXCollections.observableArrayList(teacherManager.getAllTeachers()));

        //form to add, update and delete
        // fields for the inputs..
        TextField idF = new TextField(); idF.setPromptText("ID");
        TextField nameF = new TextField(); nameF.setPromptText("Name");
        TextField ageF = new TextField(); ageF.setPromptText("Age");
        TextField subjectF = new TextField(); subjectF.setPromptText("Subject");
        TextField salaryF = new TextField(); salaryF.setPromptText("Salary");

        HBox formLayout = new HBox(10, idF, nameF, ageF, subjectF, salaryF);
        formLayout.setPadding(new Insets(10));

        // buttons for the inputs
        Button btnAdd = new Button("Add");
        Button btnUpdate = new Button("Update");
        Button btnDelete = new Button("Delete");
        //actions
        btnAdd.setOnAction(e->{
            try{
                int id = Integer.parseInt(idF.getText());
                String name = nameF.getText();
                int age = Integer.parseInt(ageF.getText());
                String subject = subjectF.getText();
                double salary = Double.parseDouble(salaryF.getText());

                teacherManager.addTeacher(new Teacher(id, name, age, subject, salary));
                table.setItems(FXCollections.observableArrayList(teacherManager.getAllTeachers()));
                idF.clear(); nameF.clear(); ageF.clear(); subjectF.clear(); salaryF.clear();
            } catch (Exception ex){ System.out.println("Invalid Input"); }
        });
        btnUpdate.setOnAction(e->{
            try{
                int id = Integer.parseInt(idF.getText());
                String name = nameF.getText();
                int age = Integer.parseInt(ageF.getText());
                String subject = subjectF.getText();
                double salary = Double.parseDouble(salaryF.getText());
                teacherManager.updateTeacher(id, name, age, subject, salary);

                table.setItems(FXCollections.observableArrayList(teacherManager.getAllTeachers()));
                table.refresh();
                idF.clear(); nameF.clear(); ageF.clear(); subjectF.clear(); salaryF.clear();
            }catch (Exception ex){ System.out.println("Invalid Input"); }
        });
        btnDelete.setOnAction(e->{
            try{
                int id = Integer.parseInt(idF.getText());
                teacherManager.deleteTeacher(id);
                table.setItems(FXCollections.observableArrayList(teacherManager.getAllTeachers()));
                idF.clear(); nameF.clear(); ageF.clear(); subjectF.clear(); salaryF.clear();
            }catch (Exception ex){ System.out.println("Invalid Input"); }
        });
        // creating the buttons section
        HBox buttonLayout = new HBox(10, btnAdd, btnUpdate, btnDelete);
        buttonLayout.setPadding(new Insets(10));
        VBox bottomPanel = new VBox(formLayout, buttonLayout);

        // positioning the element of the page
        layout.setCenter(table);
        layout.setBottom(bottomPanel);

        return layout;
    }

    private Node createCourseView() {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));

        // table
        TableView<Course> table = new TableView<>();

        TableColumn<Course,String> colCode = new TableColumn<>("Code");
        colCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        TableColumn<Course,String> colTitle = new TableColumn<>("Title");
        colTitle.setCellValueFactory(new PropertyValueFactory<>("courseTitle"));
        TableColumn<Course,String> colTeacher = new TableColumn<>("Instructor");
        colTeacher.setCellValueFactory(cellData -> {
            var t = cellData.getValue().getTeacher();
            return new SimpleStringProperty(t==null? "None":t.getName());
        });
        TableColumn<Course,String> colStudents = new TableColumn<>("Enrolled");
        colStudents.setCellValueFactory(cellData -> {
            int count = cellData.getValue().getEnrollStudents().size();
            return new SimpleStringProperty(count+" Students");
        });
        table.getColumns().addAll(colCode, colTitle, colTeacher, colStudents);

        // form
        courseList = FXCollections.observableArrayList(courseManager.getAllCourses());
        table.setItems(courseList);
        VBox actionPanel = new VBox(10);
        actionPanel.setPadding(new Insets(10));

        // managing courses
        HBox courseBox = new HBox(10);
        TextField codeF = new TextField(); codeF.setPromptText("Course Code");
        TextField titleF = new TextField(); titleF.setPromptText("Course Title");
        Button btnAdd = new Button("Add Course");
        Button btnDelete = new Button("Delete Course");
        courseBox.getChildren().addAll(codeF, titleF, btnAdd, btnDelete);
        // managing enrollments
        HBox assignBox = new HBox(10);
        TextField teacherIdF = new TextField(); teacherIdF.setPromptText("Teacher ID");
        TextField studentsIdF = new TextField(); studentsIdF.setPromptText("Student ID");
        Button btnAssign = new Button("Assign Teacher");
        Button btnEnroll = new Button("Enroll Student");

        assignBox.getChildren().addAll(new Label("Actions for Selected Course:"),teacherIdF, studentsIdF, btnAssign, btnEnroll);
        actionPanel.getChildren().addAll(new Label("New Course:"), courseBox, new Separator(), assignBox);

        // buttons actions
        btnAdd.setOnAction(e-> {
            String code = codeF.getText();
            String title = titleF.getText();
            if (!code.isEmpty() && !title.isEmpty()){
                Course newCourse = new Course(code, title);
                courseManager.addCourse(newCourse);
                courseList.add(newCourse);
                codeF.clear(); titleF.clear();
            }
        });
        btnDelete.setOnAction(e-> {
            Course selected = table.getSelectionModel().getSelectedItem();
            if (selected != null){
                courseManager.deleteCourse(selected.getCourseCode());
                courseList.remove(selected);
            }
        });
        // assign teacher
        btnAssign.setOnAction(e->{
            Course selected = table.getSelectionModel().getSelectedItem();
            try{
                int teacherId = Integer.parseInt(teacherIdF.getText());
                Teacher t = teacherManager.findTeacher(teacherId);
                if (selected != null && t!= null){
                    courseManager.assignTeacherToCourse(selected.getCourseCode(), t);
                    //refresh
                    table.refresh();
                    teacherIdF.clear();
                } else {
                    System.out.println("Invalid Course Selection or Teacher ID");
                }
            } catch (NumberFormatException ex){
                System.out.println("Invalid ID");
            }
        });

        btnEnroll.setOnAction(e->{
            Course selected = table.getSelectionModel().getSelectedItem();
            try{
                int studentId = Integer.parseInt(studentsIdF.getText());
                Student s = studentManager.findStudentById(studentId);
                if (selected != null && s != null){
                    courseManager.enrollStudentInCourse(selected.getCourseCode(), s);
                    table.refresh();
                    studentsIdF.clear();
                } else {
                    System.out.println("Invalid Course or Student ID");
                }
            } catch (NumberFormatException ex){
                System.out.println("Invalid ID");
            }
        });
        layout.setCenter(table);
        layout.setBottom(actionPanel);
        return layout;
    }
}
