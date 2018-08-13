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
 * SetUp is the class for drawing the user-interface and initializing the world
 * 
 * @author pelhamoliv
 *
 */
public class SetUp extends Application {

	// fields
	private int windowWidth = 1200;
	private int windowHeight = 800;
	private final ArrayList<WorldObject> allObjectList = new ArrayList<>();

	//this is the main entry point for the application
	@Override
	public void start(Stage primaryStage) throws Exception {

		//**the following relates to the START SCREEN**
		VBox startUp = new VBox(); 

		// nodes of startUp scene
		final Text intro = new Text("BUG WORLD FX");
		intro.setFont(Font.font(70));
		Button beginButton = new Button("Run Simulation");
		beginButton.setAlignment(Pos.CENTER);	//currently not working

		startUp.setPadding(new Insets(200));
		startUp.getChildren().add(intro);
		startUp.getChildren().add(beginButton);
		startUp.setAlignment(Pos.TOP_CENTER);

		Scene startUpScene = new Scene(startUp, windowWidth, windowHeight);		//start up scene

		//**the following relates to the SIMULATION SCREEN**
		BorderPane mainSimulationLayout = new BorderPane();		//Main layout
		Pane simulationSection = new Pane();			//simulation component
		HBox buttonSection = new HBox();				//for containing buttons

		buttonSection.setAlignment(Pos.CENTER);			//setting position of buttons
		Button pausePlay = new Button("Pause");
		Button restart = new Button("Restart");
		buttonSection.getChildren().add(pausePlay);
		buttonSection.getChildren().add(restart);
		buttonSection.setStyle("-fx-background-color: #464646");

		simulationSection.setStyle("-fx-background-color: #558000");	//changing background of simulation section
		//assigning section of layout to main borderpane 
		mainSimulationLayout.setCenter(simulationSection);
		mainSimulationLayout.setTop(buttonSection);

		//scene for mainSimulationLayout
		Scene scene = new Scene(mainSimulationLayout, windowWidth, windowHeight);

		//button in main scene set to start simulation screen
		beginButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				primaryStage.setScene(scene);			//changeing stage to display/run simulation screen
				populateWorld(primaryStage, simulationSection);		//populating world
				primaryStage.show();
			}

		});
		KeyFrame frame = new KeyFrame(Duration.millis(16), new EventHandler<ActionEvent>() {

			//updating objects of world each frame
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

		//Setting actions for buttons at top border
		//these are added here as pause/play requires timeline to be declared/instantiated
		pausePlay.setOnAction(new EventHandler<ActionEvent>() {
			int i = 0;
			@Override
			public void handle(ActionEvent event) {

				if(i%2 == 0) {
					timeline.pause();		//on first press will pause
				} else {
					timeline.play();		//every second press will play
				}
				i++;
			}
		});

		restart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				populateWorld(primaryStage, simulationSection);		//repopulates world on reset
			}
		});

		//setting up the stage
		primaryStage.setTitle("Bug World FX");
		primaryStage.setScene(startUpScene);
		primaryStage.show();

	}


	//This method Populates the world with objects
	public void populateWorld(Stage primaryStage, Pane simulationSection) {
		// add bugs to arraylist
		allObjectList.clear();			//clears here so that upon reset past list in not carried over
		simulationSection.getChildren().clear();
		//add bugs to arraylist
		for (int i = 0; i < 10; i++) {
			final Bug bugToAdd = new Bug(primaryStage);
			allObjectList.add(bugToAdd);
			simulationSection.getChildren().add(bugToAdd);			//adds node so will be displayed
		}
		// add plants to arraylist
		for (int i = 0; i < 10; i++) {
			final Plant plantToAdd = new Plant();
			allObjectList.add(plantToAdd);
			simulationSection.getChildren().add(plantToAdd);		//adds node so will be displayed
		}
		// add obstacles to arraylist
		for (int i = 0; i < 10; i++) {
			final Obstacle obstacleToAdd = new Obstacle();
			allObjectList.add(obstacleToAdd);
			simulationSection.getChildren().add(obstacleToAdd);		//adds node so will be displayed
		}
	}

	public static void main(String[] args) {
		launch(args);

	}

}