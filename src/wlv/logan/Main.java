package wlv.logan;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import wlv.logan.genetic.Genetic;

public class Main extends Application {

    Genetic genetic = new Genetic();

    @Override
    public void start(Stage primaryStage) throws Exception {
        GamePane gamePane = new GamePane();
        gamePane.onStartClick(mouseEvent -> genetic.genetic(gamePane));

        primaryStage.setTitle("Mars Lander");
        primaryStage.setScene(new Scene(gamePane, 1200, 800));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
