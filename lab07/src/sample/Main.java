/*
lab07 -
Mustafa Al-Azzawe
100617392
*/

package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

import static sample.WarningData.readData;

public class Main extends Application{

    private static Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE,
            Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
    };

    private static String[] values = {
            "FLASH FLOOD", "SEVERE THUNDERSTORM",
            "SPECIAL MARINE", "TORNADO"
    };

    public static void main(String args[]) {
        launch(args);
    }

    private Canvas canvas;

    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();

        canvas = new Canvas(800, 425);
        root.getChildren().add(canvas);

        Scene scene = new Scene(root, 800, 425, Color.WHITE);
        primaryStage.setTitle("Lab07 Solution");
        primaryStage.setScene(scene);
        primaryStage.show();

        drawPie();
    }

    public void drawPie(){
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        WarningData w = new WarningData();
        w.readData("weatherwarnings-2015.csv", ",");

        Integer[] warningValues = w.getData();

        double startAng = 0;
        double heightOffset = 100;
        double currLen;
        double sum = 0;

        gc.setStroke(Color.BLACK);

        for (int i = 0; i < 4; i++) {
            sum += warningValues[i];
        }
        for (int i = 0; i < 4; i++) {
            currLen = (warningValues[i] / sum) * 360;

            gc.setFill(pieColours[i]);
            gc.strokeRect(50, heightOffset, 50, 25);
            gc.fillRect(50, heightOffset, 50, 25);
            gc.fillArc(400, 50, 300, 300, startAng, currLen, ArcType.ROUND);
            gc.setFill(Color.BLACK);
            gc.fillText(values[i], 105, heightOffset + 15);

            startAng += currLen;
            heightOffset += 50;
        }

    }
}