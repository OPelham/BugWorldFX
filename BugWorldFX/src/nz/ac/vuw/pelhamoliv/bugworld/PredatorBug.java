package nz.ac.vuw.pelhamoliv.bugworld;

import java.util.ArrayList;

import javafx.stage.Stage;

public class PredatorBug extends Bug{

	public PredatorBug(Stage primaryStage) {
		super(primaryStage);
		// TODO Auto-generated constructor stub
	}

	//this overrides bugs method - should i make bug abstract?
	public void decideAction(ArrayList<WorldObject> allObjectList) {
		if(getHungerLevel()<4) {		//if hunger level less than 4 search for food
			if (getSensedObjects().isEmpty()) {
				moveRandomly(allObjectList);
			} else if(sensedFoodBoolean()){
				for (WorldObject w: getSensedObjects()) {
					if (w instanceof PreyBug && w.isVisible()) {
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
	
	//overrides bug method 
	public boolean sensedFoodBoolean() {
		for (WorldObject w: getSensedObjects()) {
			if (w instanceof PreyBug && w.isVisible()) {
				return true;
			}
		}
		return false;
	}
	//overrides bug method
	public void eat(ArrayList<WorldObject> allObjectList) {
		checkReach(allObjectList);
		for (WorldObject w: this.getReachableObjects()) {
			if(w instanceof PreyBug) {
				((PreyBug) w).beEaten();
				setHungerLevel(getHungerLevel()+4);
			}
		}
	}
	
	public void checkSize() {
		this.setRadius(getHungerLevel()*2+5);
	}


}
