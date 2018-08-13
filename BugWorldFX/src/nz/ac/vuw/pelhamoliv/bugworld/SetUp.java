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
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
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
	private int numOfBug;
	private int numOfPlant;
	private int numOfObstacle;
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
		beginButton.setDefaultButton(true);
		beginButton.setAlignment(Pos.CENTER);	//currently not working
		Text bugNumText = new Text("Number of bugs");
		TextField bugNumField = new TextField();
		bugNumField.setMaxWidth(100);
		Text plantNumText = new Text("Number of plants");
		TextField plantNumField = new TextField();
		plantNumField.setMaxWidth(100);
		Text obstacleNumText = new Text("Number of obstacles");
		TextField obstacleNumField = new TextField();
		obstacleNumField.setMaxWidth(100);

		startUp.setPadding(new Insets(180));
		startUp.getChildren().add(intro);
		startUp.getChildren().add(bugNumText);
		startUp.getChildren().add(bugNumField);
		startUp.getChildren().add(plantNumText);
		startUp.getChildren().add(plantNumField);
		startUp.getChildren().add(obstacleNumText);
		startUp.getChildren().add(obstacleNumField);
		startUp.getChildren().add(beginButton);
		startUp.setSpacing(10);
		startUp.setAlignment(Pos.TOP_CENTER);

		Scene startUpScene = new Scene(startUp, windowWidth, windowHeight);		//start up scene

		//**the following relates to the SIMULATION SCREEN**
		BorderPane mainSimulationLayout = new BorderPane();		//Main layout
		Pane simulationSection = new Pane();			//simulation component
		HBox buttonSection = new HBox();				//for containing buttons

		buttonSection.setAlignment(Pos.CENTER);			//setting position of buttons
		Button pausePlay = new Button("Pause");
		Button restart = new Button("Restart");
		Button returnbtn = new Button("Menu");
		buttonSection.getChildren().add(pausePlay);
		buttonSection.getChildren().add(restart);
		buttonSection.getChildren().add(returnbtn);
		buttonSection.setStyle("-fx-background-color: #464646");

		simulationSection.setStyle("-fx-background-color: #ccd3e0");	//changing background of simulation section
		//assigning section of layout to main borderpane 
		mainSimulationLayout.setCenter(simulationSection);
		mainSimulationLayout.setTop(buttonSection);

		//scene for mainSimulationLayout
		Scene scene = new Scene(mainSimulationLayout, windowWidth, windowHeight);

		//textfield actions



		//button in main scene set to start simulation screen


		KeyFrame frame = new KeyFrame(Duration.millis(16), new EventHandler<ActionEvent>() {

			//updating objects of world each frame
			@Override
			public void handle(ActionEvent event) {
				for (WorldObject c : allObjectList) {
					if(c.isVisible()) {
						if (c instanceof Bug) {
							((Bug) c).update(allObjectList);
						}
						if (c instanceof Plant) {
							((Plant) c).update();
						}
					}
				}
			}
		});
		Timeline timeline = new Timeline(frame);
		timeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
		timeline.play();

		beginButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				numOfBug = inputCheck(bugNumField.getText());	//add outputs and reset for invalid
				numOfPlant = inputCheck(plantNumField.getText());
				numOfObstacle = inputCheck(obstacleNumField.getText());

				primaryStage.setScene(scene);			//changeing stage to display/run simulation screen
				populateWorld(primaryStage, simulationSection);		//populating world
				primaryStage.show();
				timeline.play();

			}
		});


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

		returnbtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				primaryStage.setScene(startUpScene);			//changeing stage to display/run simulation screen
				timeline.stop();
				primaryStage.show();
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
		for (int i = 0; i < numOfBug; i++) {
			final Bug bugToAdd = new Bug(primaryStage);
			allObjectList.add(bugToAdd);
			simulationSection.getChildren().add(bugToAdd);			//adds node so will be displayed
		}
		// add plants to arraylist
		for (int i = 0; i < numOfPlant; i++) {
			final Plant plantToAdd = new Plant();
			allObjectList.add(plantToAdd);
			simulationSection.getChildren().add(plantToAdd);		//adds node so will be displayed
		}
		// add obstacles to arraylist
		for (int i = 0; i < numOfObstacle; i++) {
			final Obstacle obstacleToAdd = new Obstacle();
			allObjectList.add(obstacleToAdd);
			simulationSection.getChildren().add(obstacleToAdd);		//adds node so will be displayed
		}
	}

	public int inputCheck(String toCheck) {
		int i = -1;
		try  
		{  
			i = Integer.parseInt(toCheck);
		}  
		catch(NumberFormatException nfe)  
		{  
			return i;  
		}  
		return i;  
	}

	public static void main(String[] args) {
		launch(args);

	}

}