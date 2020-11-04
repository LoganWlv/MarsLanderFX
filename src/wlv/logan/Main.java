package wlv.logan;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import wlv.logan.genetic.Genetic;

public class Main extends Application {

    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 800;

    public static final double WIDTH_RATIO = (double) GamePane.MARS_WIDTH / WINDOW_WIDTH;
    public static final double HEIGHT_RATIO = (double) GamePane.MARS_HEIGHT / WINDOW_HEIGHT;

    Genetic genetic = new Genetic();

    @Override
    public void start(Stage primaryStage) throws Exception {
        GamePane gamePane = new GamePane();
        gamePane.onStartClick(mouseEvent -> genetic.genetic(gamePane));

        primaryStage.setTitle("Mars Lander");
        primaryStage.setScene(new Scene(gamePane, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
