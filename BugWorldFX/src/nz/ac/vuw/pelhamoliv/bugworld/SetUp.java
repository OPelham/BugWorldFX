package nz.ac.vuw.pelhamoliv.bugworld;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
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
	private int numPreyBug;		//the number of this object to include in simulation


	private int numPredatorBug;
	private int numOfPlant;
	private int numOfObstacle;
	private final ArrayList<WorldObject> allObjectList = new ArrayList<>();	//collection storing all objects in simulation

	//this is the main entry point for the application
	@Override
	public void start(Stage primaryStage) throws Exception {

		//loading in images and setting as image pattern
		Image dirtImage = new Image("/dirtIm.jpg");
		BackgroundImage dirtFill = new BackgroundImage(dirtImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Image plantImage = new Image("/PlantIm.png");		//plant image
		ImagePattern plantFill = new ImagePattern(plantImage);
		Image bugImage = new Image("/PreyBug.png");			//preyBug image
		ImagePattern bugFill = new ImagePattern(bugImage);
		Image predatorBugImage = new Image("/PredatorBugIm.png");			//predatorBug image
		ImagePattern predatorBugFill = new ImagePattern(predatorBugImage);
		Image obstaceImage = new Image("/RockIm.png");			//obstacle image
		ImagePattern obstacleFill = new ImagePattern(obstaceImage);

		//**the following relates to the START SCREEN** the initial screen
		VBox startUp = new VBox(); //layout for start screen

		// nodes of startUp scene
		final Text introText = new Text("BUG WORLD FX");	//title text
		introText.setFont(Font.font(70));
		//used to initiate simulation screen with given objects
		Button beginButton = new Button("Run Simulation");	
		beginButton.setDefaultButton(true);		//default so enter key will act on this button
		beginButton.setAlignment(Pos.CENTER);	
		//this block relates to the input textfields which are used in populating the simulation
		// number of prey bugs input
		Text preyBugPromptText = new Text("Number of prey bugs");
		TextField preyBugTextField = new TextField();
		preyBugTextField.setText("10");
		preyBugTextField.setMaxWidth(100);
		// number of predator bugs input
		Text predatorBugPromptText = new Text("Number of predator bugs");
		TextField predatorBugTextField = new TextField();
		predatorBugTextField.setText("2");
		predatorBugTextField.setMaxWidth(100);
		// number of plants input
		Text plantPromptText = new Text("Number of plants");
		TextField plantTextField = new TextField();
		plantTextField.setText("8");
		plantTextField.setMaxWidth(100);
		// number of plants input
		Text obstaclePromptText = new Text("Number of obstacles");
		TextField obstacleTextField = new TextField();
		obstacleTextField.setText("5");
		obstacleTextField.setMaxWidth(100);

		//adding to startup VBOX
		startUp.setPadding(new Insets(180));
		startUp.getChildren().add(introText);
		startUp.getChildren().add(preyBugPromptText);
		startUp.getChildren().add(preyBugTextField);
		startUp.getChildren().add(predatorBugPromptText);
		startUp.getChildren().add(predatorBugTextField);
		startUp.getChildren().add(plantPromptText);
		startUp.getChildren().add(plantTextField);
		startUp.getChildren().add(obstaclePromptText);
		startUp.getChildren().add(obstacleTextField);
		startUp.getChildren().add(beginButton);
		startUp.setSpacing(10);
		startUp.setAlignment(Pos.TOP_CENTER);

		//creating scene for initial screen, contains startup VBox
		Scene startUpScene = new Scene(startUp, windowWidth, windowHeight);		

		//**the following relates to the SIMULATION SCREEN**
		BorderPane mainSimulationLayout = new BorderPane();		//Main layout (the parent of this scene)
		//the following two are nested within the mainSimulationLayout
		Pane simulationSection = new Pane();					//simulation component
		HBox buttonSection = new HBox();						//for containing buttons

		buttonSection.setAlignment(Pos.CENTER);					//setting position of buttons
		Button pausePlayButton = new Button("Pause");					//a button to toggle play/pause
		Button restartButton = new Button("Restart");					// restarts simulation
		Button returnButton = new Button("Menu");					//returns to start up screen
		//adding buttons to button HBox
		buttonSection.getChildren().add(pausePlayButton);			
		buttonSection.getChildren().add(restartButton);
		buttonSection.getChildren().add(returnButton);
		buttonSection.setStyle("-fx-background-color: #464646");	//sets background colour of button section

		simulationSection.setBackground(new Background(dirtFill));	//changing background of simulation section
		//assigning section of layout to main borderpane 
		mainSimulationLayout.setCenter(simulationSection);
		mainSimulationLayout.setTop(buttonSection);


		//scene for mainSimulationLayout
		Scene simulationScene = new Scene(mainSimulationLayout, windowWidth, windowHeight);

		//this section cycles through frames every 16milliseconds
		KeyFrame frame = new KeyFrame(Duration.millis(16), new EventHandler<ActionEvent>() {

			//updating objects of world each frame
			@Override
			public void handle(ActionEvent event) {
				for (WorldObject c : allObjectList) {
					if(c.isVisible()) {
						if (c instanceof Bug) {		
							((Bug) c).update(allObjectList);	//updates all bug class and its children
						}
						if (c instanceof Plant) {
							((Plant) c).update();
						}
					}
				}
			}
		});
		Timeline timeline = new Timeline(frame);	//this allows for pause/play
		timeline.setCycleCount(javafx.animation.Animation.INDEFINITE);	//no end to timeline
		timeline.play();	

		//action on use of the begin button, will take user inputed number of objects and
		//use these in the populateworld method to create these objects
		//it will then change the stages scene to that of the simulation, show this 
		beginButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				//get users preferd number of objects

				//checks on validity of all text field input and takes input and sets number of objects for each class (in method)
				int preyBugCheck = giveFeedback(preyBugTextField, preyBugPromptText, "Prey bugs: Please Enter a positive integer 0-100", "Number of prey bugs");
				int predatorBugCheck =giveFeedback(predatorBugTextField, predatorBugPromptText, "Predator bugs: Please Enter a positive integer 0-100", "Number of predator bugs");
				int plantCheck = giveFeedback(plantTextField, plantPromptText, "Plants: Please Enter a positive integer 0-100", "Number of plants");
				int obstacleCheck = giveFeedback(obstacleTextField, obstaclePromptText, "Obstacles: Please Enter a positive integer 0-100", "Number of obstacles");
				// all text field are valid go to next scene, else will remain for corrected input
				if(preyBugCheck >0 && predatorBugCheck >0 && plantCheck >0 && obstacleCheck >0) {
					//if all true then change to next scene and populate world
					setNumPreyBug(preyBugCheck);
					setNumPredatorBug(predatorBugCheck);
					setNumOfPlant(plantCheck);
					setNumOfObstacle(obstacleCheck);

					primaryStage.setScene(simulationScene);			//changeing stage to display/run simulation screen
					populateWorld(simulationScene, simulationSection, plantFill, bugFill, obstacleFill, predatorBugFill);		//populating world
					//				primaryStage.show();
					timeline.play();
				}




				//				if(inputCheck(preyBugNumField.getText()) <0) { //if input check method returns -1 then is not valid
				//					preyBugNumText.setText("Prey bugs: Please Enter a positive integer 0-100");
				//					preyBugNumText.setFill(Color.RED);
				//					valid = false;	//to stop change to next scene
				//				} else {
				//					preyBugNumText.setText("Number of prey bugs");
				//					preyBugNumText.setFill(Color.BLACK);
				//					numPreyBug = inputCheck(preyBugNumField.getText());	//add outputs to field numPreyBug for use in populate world method
				//				}
				//
				//				//preator bug check
				//				if(inputCheck(predatorBugNumField.getText()) <0) { //if input check method returns -1 then is not valid
				//					predatorBugNumText.setText("Predator bugs: Please Enter a positive integer 0-100");
				//					predatorBugNumText.setFill(Color.RED);
				//					valid = false;	//to stop change to next scene
				//				} else {
				//					predatorBugNumText.setText("Number of predator bugs");
				//					predatorBugNumText.setFill(Color.BLACK);
				//					numPredatorBug = inputCheck(predatorBugNumField.getText());	//add outputs to field numPreyBug for use in populate world method
				//				}
				//
				//				//plant check
				//				if(inputCheck(plantNumField.getText()) <0) { //if input check method returns -1 then is not valid
				//					plantNumText.setText("Plants: Please Enter a positive integer 0-100");
				//					plantNumText.setFill(Color.RED);
				//					valid = false;	//to stop change to next scene
				//				} else {
				//					plantNumText.setText("Number of plants");
				//					plantNumText.setFill(Color.BLACK);
				//					numOfPlant = inputCheck(plantNumField.getText());	//add outputs to field numPreyBug for use in populate world method
				//				}
				//
				//				//obstacle check
				//				if(inputCheck(obstacleNumField.getText()) <0) { //if input check method returns -1 then is not valid
				//					obstacleNumText.setText("Obstacles: Please Enter a positive integer 0-100");
				//					obstacleNumText.setFill(Color.RED);
				//					valid = false;	//to stop change to next scene
				//				} else {
				//					obstacleNumText.setText("Number of obstacles");
				//					obstacleNumText.setFill(Color.BLACK);
				//					numOfObstacle = inputCheck(obstacleNumField.getText());	//add outputs to field numPreyBug for use in populate world method
				//				}
				//
				//				//change to correct scene for simulation if all input valid
				//				if (valid == true) {
				//					primaryStage.setScene(simulationScene);			//changeing stage to display/run simulation screen
				//					populateWorld(simulationScene, simulationSection, plantFill, bugFill, obstacleFill, predatorBugFill);		//populating world
				//					//				primaryStage.show();
				//					timeline.play();
				//				}

			}
		});

		//Setting actions for buttons at top border of simulation scene
		//these are added here as pause/play requires timeline to be declared/instantiated
		pausePlayButton.setOnAction(new EventHandler<ActionEvent>() {
			int i = 0;	//to keep track of odd/even number of clicks so we can use as a toggle button
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

		//will repopulate world, this will clear old objects and create a new set
		//will use the same user inputs from startup screen
		restartButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				populateWorld(simulationScene, simulationSection, plantFill, bugFill, obstacleFill, predatorBugFill);		//repopulates world on reset
			}
		});

		//this button returns to the startup screen allowing user to choose a different number of objects
		returnButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				primaryStage.setScene(startUpScene);			//changeing stage to startup screen
				timeline.stop();								//stops old simulation from running in background
				//				primaryStage.show();
			}
		});

		//setting up the stage this is what initially runs upon the programme starting
		primaryStage.setTitle("Bug World FX");
		primaryStage.setScene(startUpScene);
		primaryStage.show();

	}


	//This method Populates the world with objects, 
	//takes the stage to make bug which takes this as a parameter in order to know boundaries
	//takes the pane to clear old contents upon restart button action
	public void populateWorld(Scene simulationScene, Pane simulationSection, ImagePattern plantFill, ImagePattern bugFill, ImagePattern obstacleFill, ImagePattern predatorBugFill) {

		allObjectList.clear();			//clears here so that upon reset past list in not carried over
		simulationSection.getChildren().clear();

		// add obstacles to arraylist
		for (int i = 0; i < numOfObstacle; i++) {	//adds number given by user in startup screen
			final Obstacle obstacleToAdd = new Obstacle(simulationScene);
			obstacleToAdd.setFill(obstacleFill);	// adds image fill
			allObjectList.add(obstacleToAdd);
			simulationSection.getChildren().add(obstacleToAdd);		//adds node so will be displayed
		}
		// add plants to arraylist
		for (int i = 0; i < numOfPlant; i++) {		//adds number given by user in startup screen
			final Plant plantToAdd = new Plant(simulationScene);
			plantToAdd.setFill(plantFill);	// adds image fill
			allObjectList.add(plantToAdd);
			simulationSection.getChildren().add(plantToAdd);		//adds node so will be displayed
		}
		//add preybugs to arraylist
		for (int i = 0; i < numPreyBug; i++) {		//adds number given by user in startup screen
			final PreyBug bugToAdd = new PreyBug(simulationScene);
			bugToAdd.setFill(bugFill);	// adds image fill
			allObjectList.add(bugToAdd);
			simulationSection.getChildren().add(bugToAdd);			//adds node so will be displayed
		}
		//add predatorbugs to arraylist
		for (int i = 0; i < numPredatorBug; i++) {		//adds number given by user in startup screen
			final PredatorBug bugToAdd = new PredatorBug(simulationScene);
			bugToAdd.setFill(predatorBugFill);	// adds image fill
			allObjectList.add(bugToAdd);
			simulationSection.getChildren().add(bugToAdd);			//adds node so will be displayed
		}
	}

	//checks that user has inputted an integer if not returns -1
	public int inputCheck(String toCheck) {
		int i = -1;
		try  
		{  
			i = Integer.parseInt(toCheck);	//if string can be made an integer and is less than 100 return it as int
			if (i>100) {
				i = -1;						//otherwise return -1
			} 

		}  
		catch(NumberFormatException nfe)  	//if cant be made an int then return -1
		{  
			return i;  
		}  
		return i;  
	}

	//needs generic class?
	//	public void generateObjects<T extends WorldObject>(int numOfObject, Scene scene,ImagePattern fill){
	//		for (int i = 0; i < numOfObject; i++) {	//adds number given by user in startup screen
	//			final Obstacle obstacleToAdd = new Obstacle(scene);
	//			obstacleToAdd.setFill(obstacleFill);	// adds image fill
	//			allObjectList.add(obstacleToAdd);
	//			simulationSection.getChildren().add(obstacleToAdd);		//adds node so will be displayed
	//		}
	//	}


	//sets error mesages if invald input in textfield, calls input check to test for validity
	public int giveFeedback(TextField fieldname, Text textname, String errorMessage, String defaultMessage) {
		if(inputCheck(fieldname.getText()) <0) { //if input check method returns -1 then is not valid
			textname.setText(errorMessage);
			textname.setFill(Color.RED);
			return -1;
		} else {
			textname.setText(defaultMessage);
			textname.setFill(Color.BLACK);
			numPreyBug = inputCheck(fieldname.getText());	//add outputs to field numPreyBug for use in populate world method
			return numPreyBug;
		}
	}

	public static void main(String[] args) {
		launch(args);

	}

	//accessor methods
	public int getNumPreyBug() {
		return numPreyBug;
	}


	public void setNumPreyBug(int numPreyBug) {
		this.numPreyBug = numPreyBug;
	}


	public int getNumPredatorBug() {
		return numPredatorBug;
	}


	public void setNumPredatorBug(int numPredatorBug) {
		this.numPredatorBug = numPredatorBug;
	}


	public int getNumOfPlant() {
		return numOfPlant;
	}


	public void setNumOfPlant(int numOfPlant) {
		this.numOfPlant = numOfPlant;
	}


	public int getNumOfObstacle() {
		return numOfObstacle;
	}


	public void setNumOfObstacle(int numOfObstacle) {
		this.numOfObstacle = numOfObstacle;
	}

}