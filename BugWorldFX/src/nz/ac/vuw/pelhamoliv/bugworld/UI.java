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
import javafx.scene.layout.VBox;
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
	private int windowWidth = 400;
	private int windowHeight = 400;
	private final ArrayList<Bug> circleList = new ArrayList<>();
	// float x = 100, y = 100, dx = -1.5f, dy = -1.5f;

	@Override
	public void start(Stage primaryStage) throws Exception {

		Group root = new Group();

		for (int i = 0; i < 20; i++) {
			final Bug b = new Bug(primaryStage);
			circleList.add(b);
			root.getChildren().add(b);
		}

		Scene scene = new Scene(root, windowWidth, windowHeight);

		KeyFrame frame = new KeyFrame(Duration.millis(16), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				for (Bug b : circleList) {
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