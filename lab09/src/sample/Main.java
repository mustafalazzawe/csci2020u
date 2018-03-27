package sample;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Iterator;


public class Main extends Application {

    private static final int WIN_WIDTH = 800;
    private static final int WIN_HEIGHT = 600;
    private static final String API_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol=";
    private final static String API_KEY = "&apikey=CBENS0PKUNIKH7W7";
    private Canvas canvas;

    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, WIN_WIDTH, WIN_HEIGHT);

        canvas = new Canvas();
        canvas.setWidth(WIN_WIDTH);
        canvas.setHeight(WIN_HEIGHT);

        root.getChildren().add(canvas);

        primaryStage.setTitle("Lab09 Solution");
        primaryStage.setScene(scene);
        primaryStage.show();

        try {
            plotClosingPrices("AAPL", Color.BLUE);
            plotClosingPrices("GOOG", Color.RED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void plotClosingPrices(String stockTicker, Color color) {

        Set<Map.Entry<String, JsonElement>> stockData = downloadStockPrices(stockTicker);
        TreeMap<String, Float> closingPriceData = getClosingPrices(stockData);

        drawLinePlot(closingPriceData.values(), color);
    }

    private TreeMap<String, Float> getClosingPrices(Set<Map.Entry<String, JsonElement>> data) {

        TreeMap<String, Float> closingPrices = new TreeMap<>();

        // Iterate over stock data
        for (Map.Entry<String, JsonElement> entry : data) {
            closingPrices.put(entry.getKey(), entry.getValue().getAsJsonObject().get("4. close").getAsFloat());
        }

        return closingPrices;
    }

    private Set<Map.Entry<String, JsonElement>> downloadStockPrices(String stockTicker) {

        // Obtain JSON data
        JsonObject jsonRoot = JSONReader.download(API_URL + stockTicker + API_KEY);
        JsonObject values = jsonRoot.get("Monthly Time Series").getAsJsonObject();

        return values.entrySet();
    }

    private void drawLinePlot(Collection<Float> data, Color lineColor) {

        double width = canvas.getWidth();
        double height = canvas.getHeight();
        double widthPadding = width / 10;
        double heightPadding = height / 10;
        double maxWidth = width - 2*widthPadding;
        double maxHeight = height - 2*heightPadding;
        double xDist = maxWidth / data.size();
        float highestNum = getHighestNum(data);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.strokeLine(widthPadding, heightPadding, widthPadding, height-heightPadding);
        gc.strokeLine(widthPadding, height-heightPadding, width-widthPadding, height-heightPadding);

        Iterator<Float> iter = data.iterator();
        int i = 0;
        float prevNum = iter.next();
        float currNum;
        while (iter.hasNext()) {
            currNum = iter.next();
            plotLine(widthPadding+i*xDist, height-heightPadding-maxHeight*(prevNum/highestNum), widthPadding+(i+1)*xDist, height-heightPadding-maxHeight*(currNum/highestNum), lineColor);
            prevNum = currNum;
            i++;
        }
    }

    private void plotLine(double x1, double y1, double x2, double y2, Color lineColor) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(lineColor);
        gc.strokeLine(x1, y1, x2, y2);
    }

    private float getHighestNum(Collection<Float> data) {
        Iterator<Float> iter = data.iterator();
        float highestNum = iter.next();
        float currNum;
        while (iter.hasNext()) {
            currNum = iter.next();
            if (currNum > highestNum) {
                highestNum = currNum;
            }
        }
        return highestNum;
    }

    public static void main(String[] args) {
        launch(args);
    }

}