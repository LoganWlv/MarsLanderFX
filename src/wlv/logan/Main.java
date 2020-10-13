package wlv.logan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static wlv.logan.MarsFloor.FIRST_MAP_LINES;
import static wlv.logan.MarsFloor.TEST_MAP;

public class Main extends Application {

    public final Double MARS_GRAVITY = 3.711d;
    public final Line WINNING_AREA = TEST_MAP.getKey();

    @Override
    public void start(Stage primaryStage) throws Exception {
        GamePane gamePane = new GamePane();


        primaryStage.setTitle("Mars Lander");
        primaryStage.setScene(new Scene(gamePane, 1200, 800));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
