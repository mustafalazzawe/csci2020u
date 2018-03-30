package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Application {

    TextArea textArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setTitle("Lab10 Solution - Server");
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);

        textArea = new TextArea();
        Button exitBtn = new Button("Exit");
        exitBtn.setOnAction(evt -> System.exit(0));
        gridPane.add(textArea, 0, 0);
        gridPane.add(exitBtn, 0, 1);

        root.getChildren().add(gridPane);

        Scene scene = new Scene(root, 570, 400);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        Thread t = new Thread(new handleConnections());
        t.start();
    }
    public class handleConnections implements Runnable {
        String message;

        private ServerSocket serverSocket;
        Thread[] threads = null;

        public handleConnections() {
            try {
                this.serverSocket = new ServerSocket(60000);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run() {
            System.out.println("thread is running...");
            while (true) {
                try {
                    System.out.println("now accepting socket connections.");
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("connection!");
                    // Launch serverGetTextJob and put it into a thread
                    Thread t = new Thread(new getText(clientSocket));
                    t.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    public class getText implements Runnable {
        private Socket clientSocket;
        private String message;

        public getText (Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while ((message = in.readLine()) != null) {
                    textArea.appendText(message + "\n");
                    System.out.println("message read -> " + message);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}