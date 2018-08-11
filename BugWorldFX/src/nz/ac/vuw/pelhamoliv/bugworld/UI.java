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
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
	// make a universal arraylist of circe class?
	private final ArrayList<WorldObject> allObjectList = new ArrayList<>();
	// float x = 100, y = 100, dx = -1.5f, dy = -1.5f;

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane bp = new BorderPane();
		Pane root = new Pane();

		populateWorld(primaryStage, root);

		HBox hb = new HBox();
		Button pausePlay = new Button("Pause");
		Button restart = new Button("Restart");
		hb.getChildren().add(pausePlay);
		hb.getChildren().add(restart);
		hb.setStyle("-fx-background-color: #464646");
		root.setStyle("-fx-background-color: #558000");
		bp.setCenter(root);
		bp.setTop(hb);
		Scene scene = new Scene(bp, windowWidth, windowHeight);
		//		scene.setFill(Color.DARKOLIVEGREEN);

		KeyFrame frame = new KeyFrame(Duration.millis(16), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				for (WorldObject c : allObjectList) {
					if (c instanceof Bug) {
						((Bug) c).update(allObjectList);
					}
				}
			}
		});

		Timeline timeline = new Timeline(frame);
		timeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
		timeline.play();

		pausePlay.setOnAction(new EventHandler<ActionEvent>() {
			int i = 0;
			@Override
			public void handle(ActionEvent event) {

				if(i%2 == 0) {
					timeline.pause();
				} else {
					timeline.play();
				}
				i++;
			}
		});
		
		restart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				
				populateWorld(primaryStage, root);
			}
		});

		primaryStage.setTitle("Bug World FX");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	// public boolean checkCollisions(WorldObject c) {
	//
	// for (WorldObject d: allObjectList) {
	// if(c!=d) {
	// if(c.conductCollision(d)) {
	// return true;
	// }
	// }
	// }
	//
	// return false;
	// }

	public void populateWorld(Stage primaryStage, Pane root) {
		// add bugs to arraylist
		allObjectList.clear();
		root.getChildren().clear();
		for (int i = 0; i < 10; i++) {
			final Bug b = new Bug(primaryStage);
			// bugList.add(b);
			allObjectList.add(b);
			root.getChildren().add(b);
		}
		// add plants to arraylist
		for (int i = 0; i < 1; i++) {
			final Plant p = new Plant();
			// plantList.add(p);
			allObjectList.add(p);
			root.getChildren().add(p);
		}
		// add plants to arraylist
		for (int i = 0; i < 10; i++) {
			final Obstacle o = new Obstacle();
			// obstacleList.add(o);
			allObjectList.add(o);
			root.getChildren().add(o);
		}
	}

	public static void main(String[] args) {
		launch(args);

	}

}