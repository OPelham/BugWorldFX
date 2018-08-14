package nz.ac.vuw.pelhamoliv.bugworld;

import java.util.ArrayList;

import javafx.stage.Stage;

public class PreyBug extends Bug{

	public PreyBug(Stage primaryStage) {
		super(primaryStage);
		// TODO Auto-generated constructor stub
	}
	//this overrides bugs method - should i make bug abstract?
	public void decideAction(ArrayList<WorldObject> allObjectList) {
		//need to sort by porximity
		//insert others as add more classes
		if(getHungerLevel()<4) {		//if hunger level less than 4 search for food
			if (getSensedObjects().isEmpty()) {
				moveRandomly(allObjectList);
			} else if(sensedFoodBoolean()){
				for (WorldObject w: getSensedObjects()) {
					if (w instanceof Plant && w.isVisible()) {
						moveToward(w, allObjectList);
						break;
					}

				}
			} else {
				moveRandomly(allObjectList);
			}
		} else {
			moveRandomly(allObjectList);
		}
	}
	
	public boolean sensedFoodBoolean() {
		for (WorldObject w: getSensedObjects()) {
			if (w instanceof Plant && w.isVisible()) {
				return true;
			}
		}
		return false;
	}
	
	public void eat(ArrayList<WorldObject> allObjectList) {
		checkReach(allObjectList);
		for (WorldObject w: this.getReachableObjects()) {
			if(w instanceof Plant) {
				((Plant) w).beEaten();
				setHungerLevel(getHungerLevel()+4);
			}
		}
	}
	
	//will kill bug
	public void beEaten() {
		setHungerLevel(0);;
	}
	
	

}
