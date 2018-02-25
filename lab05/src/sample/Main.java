/*
lab05 - Main.java
Mustafa Al-Azzawe
100617392
*/

package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sun.util.resources.cldr.lag.CalendarData_lag_TZ;

public class Main extends Application {
    private TableView<StudentRecord> students;

    private TextField sid;
    private TextField assignments;
    private TextField midterm;
    private TextField finalExam;

    private Button add;
    private Label addResult;

    public static void main(String[] args) { Application.launch(args); }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lab05 Solution");

        BorderPane layout = new BorderPane();

        //create the table of student records

        //SID
        TableColumn<StudentRecord, String> idCol = new TableColumn<>("SID");
        idCol.setPrefWidth(100);
        idCol.setCellValueFactory(new PropertyValueFactory<>("sid"));

        //Assignments
        TableColumn<StudentRecord, Float> aCol = new TableColumn<>("Assignments");
        aCol.setPrefWidth(200);
        aCol.setCellValueFactory(new PropertyValueFactory<>("assignments"));

        //Midterm
        TableColumn<StudentRecord, Float> mCol = new TableColumn<>("Midterm");
        mCol.setPrefWidth(100);
        mCol.setCellValueFactory(new PropertyValueFactory<>("midterm"));

        //Final Exam
        TableColumn<StudentRecord, Float> examCol = new TableColumn<>("Final Exam");
        examCol.setPrefWidth(100);
        examCol.setCellValueFactory(new PropertyValueFactory<>("finalExam"));

        //Final Mark
        TableColumn<StudentRecord, Float> markCol = new TableColumn<>("Final Mark");
        markCol.setPrefWidth(100);
        markCol.setCellValueFactory(new PropertyValueFactory<>("finalMark"));

        //Letter Grade
        TableColumn<StudentRecord, String> letterCol = new TableColumn<>("Letter Grade");
        letterCol.setPrefWidth(100);
        letterCol.setCellValueFactory(new PropertyValueFactory<>("letter"));

        this.students = new TableView<>();
        this.students.getColumns().add(idCol);
        this.students.getColumns().add(aCol);
        this.students.getColumns().add(mCol);
        this.students.getColumns().add(examCol);
        this.students.getColumns().add(markCol);
        this.students.getColumns().add(letterCol);

        //form at the bottom
        this.sid = new TextField();
        this.sid.setPromptText("SID");

        this.assignments = new TextField();
        this.assignments.setPromptText("#/100");

        this.midterm = new TextField();
        this.midterm.setPromptText("#/100");

        this.finalExam = new TextField();
        this.finalExam.setPromptText("#/100");

        this.add = new Button("Add Entry");
        this.add.setDefaultButton(true);
        this.add.setOnAction(e -> addStudent());

        this.addResult = new Label("");

        //create the form layout
        GridPane bottom = new GridPane();
        bottom.setPadding(new Insets(10));
        bottom.setHgap(10);
        bottom.setVgap(10);

        //SID text field
        bottom.add(new Label("SID"), 0, 0);
        bottom.add(sid, 1, 0);

        //assignments text field
        bottom.add(new Label("Assignments"), 2, 0);
        bottom.add(assignments, 3, 0);

        //midterm text field
        bottom.add(new Label("Midterm"), 0, 1);
        bottom.add(midterm, 1, 1);

        //final exam text field
        bottom.add(new Label("Final Exam"), 2, 1);
        bottom.add(finalExam, 3, 1);

        //button
        bottom.add(add, 1, 3);
        bottom.add(addResult, 2, 3);

        layout.setCenter(students);
        layout.setBottom(bottom);

        Scene scene = new Scene(layout, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
        this.students.setItems(DataSource.getAllStudents());
    }

    public boolean addStudent() {
        //check if all fields have values
        String id = sid.getText();
        float _assignments;
        float _midterm;
        float _finalExam;

        if (id.equals("") || assignments.getText().equals("") || midterm.getText().equals("") || finalExam.getText().equals("")) {
            _assignments = 0.0f;
            _midterm = 0.0f;
            _finalExam = 0.0f;
            this.addResult.setText("Empty Field(s)");
            return false;
        } else {
            _assignments = Float.parseFloat(assignments.getText());
            _midterm = Float.parseFloat(midterm.getText());
            _finalExam = Float.parseFloat(finalExam.getText());
            this.addResult.setText("Added!");
        }


        //add the student
        this.students.getItems().add(new StudentRecord(id, _assignments, _midterm, _finalExam));

        //clear the fields
        this.sid.setText("");
        this.assignments.setText("");
        this.midterm.setText("");
        this.finalExam.setText("");

        return true;
    }
}