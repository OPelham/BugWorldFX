package nz.ac.vuw.pelhamoliv.bugworld;

import java.util.ArrayList;

import javafx.stage.Stage;

/**predator bug eats preybugs,
 * 
 * @author pelhamoliv
 *
 */
public class PredatorBug extends Bug{

	public PredatorBug(Stage primaryStage) {
		super(primaryStage);
		//could change so could feed in max speed etc as parameters to make customisable subtypes
	}

	//this overrides bugs method
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
	public boolean sensedFoodBoolean() {	//go through sensed objects array and, if any are food return true
		for (WorldObject w: getSensedObjects()) {
			if (w instanceof PreyBug && w.isVisible()) {
				return true;
			}
		}
		return false;
	}
	//overrides bug method
	public void eat(ArrayList<WorldObject> allObjectList) {		//if within-reach-collection contains food eat it
		checkReach(allObjectList);
		for (WorldObject w: this.getReachableObjects()) {
			if(w instanceof PreyBug) {
				((PreyBug) w).beEaten();
				setHungerLevel(getHungerLevel()+4);
			}
		}
	}
	
	//overrides bug method, align radius with hungerlevel
	public void checkSize() {
		this.setRadius(getHungerLevel()*2+10);
	}


}
