package nz.ac.vuw.pelhamoliv.bugworld;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * UI is the class for drawing the userinterface
 * 
 * @author pelhamoliv
 *
 */
public class UI extends Application {

	// fields
	private int windowWidth = 1200;
	private int windowHeight = 800;
	//make a universal arraylist of circe class?
	private final ArrayList<Plant> plantList = new ArrayList<>();
	private final ArrayList<Bug> bugList = new ArrayList<>();
	private final ArrayList<Obstacle> obstacleList = new ArrayList<>();
	// float x = 100, y = 100, dx = -1.5f, dy = -1.5f;

	@Override
	public void start(Stage primaryStage) throws Exception {

		Group root = new Group();

		//add bugs to arraylist
		for (int i = 0; i < 20; i++) {
			final Bug b = new Bug(primaryStage);
			bugList.add(b);
			root.getChildren().add(b);
		}
		//add plants to arraylist
		for (int i = 0; i < 10; i++) {
			final Plant p = new Plant();
			plantList.add(p);
			root.getChildren().add(p);
		}
		//add plants to arraylist
		for (int i = 0; i < 10; i++) {
			final Obstacle o = new Obstacle();
			obstacleList.add(o);
			root.getChildren().add(o);
		}


		Scene scene = new Scene(root, windowWidth, windowHeight);
		scene.setFill(Color.DARKOLIVEGREEN);

		KeyFrame frame = new KeyFrame(Duration.millis(16), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				for (Bug b : bugList) {
					b.update();
				}
			}
		});

		Timeline timeline = new Timeline(frame);
		timeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
		timeline.play();

		primaryStage.setTitle("Bug World FX");
		primaryStage.setScene(scene);
		primaryStage.show();


	}

	public static void main(String[] args) {
		launch(args);

	}

}