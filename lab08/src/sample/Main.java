/*
lab08 - Main.java
Mustafa Al-Azzawe
100617392
 */

package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import sun.util.resources.cldr.lag.CalendarData_lag_TZ;

public class Main extends Application {
    public static TableView<StudentRecord> students;
    public static ObservableList<StudentRecord> records = FXCollections.observableArrayList();
    public static String currentFilename = "myfile.csv";

    private TextField sid;
    private TextField assignments;
    private TextField midterm;
    private TextField finalExam;

    private Button add;
    private Label addResult;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lab08 Solution");

        BorderPane layout = new BorderPane();

        students = new TableView<>();
        createColumns();

        //form at the bottom
        this.sid = new TextField();
        this.sid.setPromptText("SID");

        this.assignments = new TextField();
        this.assignments.setPromptText("mark/100");

        this.midterm = new TextField();
        this.midterm.setPromptText("mark/100");

        this.finalExam = new TextField();
        this.finalExam.setPromptText("mark/100");

        this.add = new Button("Add Entry");
        this.add.setDefaultButton(true);
        this.add.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                addStudent();

                //clear the fields
                sid.setText("");
                assignments.setText("");
                midterm.setText("");
                finalExam.setText("");
            }
        });
        addResult = new Label("");

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

        VBox topContainer = new VBox();
        MenuBar menuBar = new MenuBar();

        topContainer.getChildren().add(menuBar);

        Menu menuFile = new Menu("File");
        MenuItem newFile = new MenuItem("New");
        newFile.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                students.getItems().clear();
            }
        });
        MenuItem openFile = new MenuItem("Open");
        openFile.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                //javafx file chooser
                FileChooser fc = new FileChooser();
                fc.setInitialDirectory(new File("."));
                fc.setTitle("Choose a File");
                fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV files", "*.csv"));

                //show file chooser
                File f = fc.showOpenDialog(primaryStage);
                if(f != null){
                    currentFilename = f.getName();
                    handleOpen(f);
                }
            }
        });
        MenuItem saveFile = new MenuItem("Save");
        saveFile.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                try{
                    handleSave();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        saveFile.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        MenuItem saveAsFile = new MenuItem("Save As");
        saveAsFile.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                //javafx file chooser
                FileChooser fc = new FileChooser();
                fc.setInitialDirectory(new File("."));
                fc.setTitle("Save As");
                fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".csv", "*.csv"));

                //show file chooser
                File f = fc.showSaveDialog(primaryStage);
                if(f != null){
                    currentFilename = f.getName();
                    try{
                        handleSave();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        MenuItem exitFile = new MenuItem("Exit");
        exitFile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        menuFile.getItems().addAll(newFile, openFile, saveFile, saveAsFile, exitFile);
        menuBar.getMenus().addAll(menuFile);

        layout.setCenter(students);
        layout.setTop(topContainer);
        layout.setBottom(bottom);

        Scene scene = new Scene(layout, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void createColumns(){
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

        // Student ID, Assignments, Midterm, Final exam
        records.add(new StudentRecord("100100100", 75.0f, 68.0f, 54.25f));
        records.add(new StudentRecord("100100101", 70.0f, 69.25f, 51.5f));
        records.add(new StudentRecord("100100102", 100.0f, 97.0f, 92.5f));
        records.add(new StudentRecord("100100103", 90.0f, 88.5f, 68.75f));
        records.add(new StudentRecord("100100104", 72.25f, 74.75f, 58.25f));
        records.add(new StudentRecord("100100105", 85.0f, 56.0f, 62.5f));
        records.add(new StudentRecord("100100106", 70.0f, 66.5f, 61.75f));
        records.add(new StudentRecord("100100107", 55.0f, 47.0f, 50.5f));
        records.add(new StudentRecord("100100108", 40.0f, 32.5f, 27.75f));
        records.add(new StudentRecord("100100109", 82.5f, 77.0f, 74.25f));

        students.setItems(records);

        students.getColumns().add(idCol);
        students.getColumns().add(aCol);
        students.getColumns().add(mCol);
        students.getColumns().add(examCol);
        students.getColumns().add(markCol);
        students.getColumns().add(letterCol);
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
        StudentRecord newStudent =
                new StudentRecord(id, _assignments, _midterm, _finalExam);
        records.add(newStudent);

        return true;
    }


    public static void handleOpen(File f){
        try{
            BufferedReader br = new BufferedReader(new FileReader(f));
            students = new TableView<>();
            records.clear();
            int count = 0;
            String row;
            while((row = br.readLine()) != null){
                if(count != 0){
                    String[] data = row.split(",");
                    StudentRecord temp =
                            new StudentRecord(data[0],
                                    Float.valueOf(data[1]),
                                    Float.valueOf(data[2]),
                                    Float.valueOf(data[3]));
                    records.add(temp);
                }
                count++;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        students.setItems(records);
    }

    public void handleSave(){
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileOutputStream(currentFilename));
            String titles = "SID" + "," +
                    "Assignments" + "," +
                    "Midterm" + "," +
                    "Final Exam";
            out.println(titles);
            for(StudentRecord record : records){
                String row = record.getSid() + "," +
                        record.getAssignments() + "," +
                        record.getMidterm() + "," +
                        record.getFinalExam();
                out.println(row);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
        System.out.println("Saved!");
    }
}
