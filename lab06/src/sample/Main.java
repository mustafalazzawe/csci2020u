/*
Lab06 -
Mustafa Al-Azzawe
100617392
*/

package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

public class Main extends Application {

    //private TextField barTitle;

    private static double[] avgHousingPricesByYear = {
            247381.0,264171.4,287715.3,294736.1,
            308431.4,322635.9,340253.0,363153.7
    };
    private static double[] avgCommercialPricesByYear = {
            1121585.3,1219479.5,1246354.2,1295364.8,
            1335932.6,1472362.0,1583521.9,1613246.3
    };

    private static String[] ageGroups = {
            "18-25", "26-35", "36-45", "46-55", "56-65", "65+"
    };
    private static int[] purchasesByAgeGroup = {
            648, 1021, 2453, 3173, 1868, 2247
    };
    private static Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE,
            Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
    };

    public static void main(String[] args) {
        Application.launch(args);
    }

    private Canvas canvas;

    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root, 800, 400, Color.WHITE);

        canvas = new Canvas(800, 400);
        //canvas.setWidth(800);
        //canvas.setHeight(400);

        root.getChildren().add(canvas);

        primaryStage.setTitle("Lab06 Solution");
        primaryStage.setScene(scene);
        primaryStage.show();

        drawCharts();
    }

    private void drawCharts() {
        GraphicsContext g = canvas.getGraphicsContext2D();

        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        double numValue;
        int x = 0;
        for(int i = 0; i < avgHousingPricesByYear.length; i++){
            //RED BAR - housing prices by year
            numValue = avgHousingPricesByYear[i];
            numValue = (20 + (numValue - avgHousingPricesByYear[0])*((canvas.getHeight() - 20) - 20))
                    /(avgCommercialPricesByYear[avgCommercialPricesByYear.length - 1] - avgHousingPricesByYear[0]);

            g.setFill(Color.RED);
            g.fillRect(x, canvas.getHeight() - numValue - 20, 20, numValue);
            x+=20;

            //BLUE BAR - commercial prices by year
            numValue = avgCommercialPricesByYear[i];
            numValue = (20 + (numValue - avgCommercialPricesByYear[0])*((canvas.getHeight() - 20) -20))
                    /(avgCommercialPricesByYear[avgCommercialPricesByYear.length - 1] - avgCommercialPricesByYear[0]);

            g.setFill(Color.BLUE);
            g.fillRect(x, canvas.getHeight() - numValue - 20, 20, numValue);
            x+=30;
        }

        x+=50;


        //pie chart
        double total = 0;
        for(int i = 0; i < purchasesByAgeGroup.length; i++){
            total+=purchasesByAgeGroup[i];
        }

        numValue = 0;
        double startAngle = 0;
        for(int i = 0; i< purchasesByAgeGroup.length; i++){
            numValue = (purchasesByAgeGroup[i]/total) * 360;
            g.setFill(pieColours[i]);
            g.fillArc(x, canvas.getHeight()/2 - (300/2), 300, 300, startAngle, numValue, ArcType.ROUND);
            startAngle+=numValue;
        }

    }
}
