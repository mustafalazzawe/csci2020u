/*
Lab04 -
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {

    private TextField userNameF;
    private PasswordField pwField;
    private TextField fullNameF;
    private TextField emailF;
    private TextField phoneF;
    private DatePicker dobField;
    private Button registerB;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Lab04 Solution");

        GridPane gp = new GridPane();

        //sets the type of padding so it looks good
        gp.setPadding(new Insets(10, 10, 10, 10));

        //setting the gaps (vertical and horizontal) in between each field
        gp.setVgap(10);
        gp.setHgap(10);


        //set our variables

        //username label & field
        Label userNameL = new Label("Username: ");
        userNameF = new TextField();
        userNameF.setPromptText("eg. GOAT_raptor10");

        gp.add(userNameL, 0, 0);
        gp.add(userNameF, 1, 0);

        //password label & field
        Label pwLabel = new Label("Password: ");
        pwField = new PasswordField();

        gp.add(pwLabel, 0, 1);
        gp.add(pwField, 1, 1);

        //fullname label & field
        Label fullNameL = new Label("Full Name: ");
        fullNameF = new TextField();
        fullNameF.setPromptText("eg. DeMar DeRozan");

        gp.add(fullNameL, 0, 2);
        gp.add(fullNameF, 1, 2);

        //email label & field
        Label emailL = new Label("E-Mail: ");
        emailF = new TextField();
        emailF.setPromptText("eg. demar.derozan@gmail.com");

        gp.add(emailL, 0, 3);
        gp.add(emailF, 1, 3);

        //phone label & field
        Label phoneL = new Label("Phone #: ");
        phoneF = new TextField();
        phoneF.setPromptText("9057673872");

        gp.add(phoneL, 0, 4);
        gp.add(phoneF, 1, 4);

        //dob label & field
        Label dobLabel = new Label("Date of Birth: ");
        dobField = new DatePicker();
        dobField.setPromptText("dd/mm/yyyy");

        gp.add(dobLabel, 0, 5);
        gp.add(dobField, 1, 5);

        //register button
        registerB = new Button("Register");

        //this set default button makes it so if there's one button on the form, we can press enter to press it
        registerB.setDefaultButton(true);

        gp.add(registerB, 1, 6);

        //this is out button event handler. We will work with what happens here
        registerB.setOnAction(new EventHandler<ActionEvent>() {
                                  public void handle(ActionEvent event) {
                                      System.out.println(userNameF.getText());
                                      System.out.println(pwField.getText());
                                      System.out.println(fullNameF.getText());
                                      System.out.println(emailF.getText());
                                      System.out.println(phoneF.getText());
                                      System.out.println(dobField.getValue());

                                      //clear previous inputs
                                      userNameF.clear();
                                      pwField.clear();
                                      fullNameF.clear();
                                      emailF.clear();
                                      phoneF.clear();
                                      //dobField.clear();
                                  }
                              }
        );

        //create the scene
        Scene scene = new Scene(gp, 500, 300);

        //set the scene
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
