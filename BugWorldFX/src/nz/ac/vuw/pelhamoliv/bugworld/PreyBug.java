package nz.ac.vuw.pelhamoliv.bugworld;

import java.util.ArrayList;

import javafx.scene.Scene;
/**prey bug eats plants, eaten by predaor bugs
 * 
 * 
 * @author pelhamoliv
 *
 */

public class PreyBug extends Bug{

	public PreyBug(Scene scene) {
		super(scene);
		//could change so could feed in max speed etc as parameters to make customisable subtypes
	}
	//this overrides bugs method 
	public void decideAction(ArrayList<WorldObject> allObjectList) {
		//could to sort by porximity
		if(getHungerLevel()<4) {		//if hunger level less than 4 search for food
			if (getSensedObjects().isEmpty()) {		//if no objects in range, move randomly
				moveRandomly(allObjectList);
			} else if(sensedFoodBoolean()){			//if sense food in range move toward it
				for (WorldObject w: getSensedObjects()) {
					if (w instanceof Plant && w.isVisible()) {
						moveToward(w, allObjectList);
						break;
					}
				}
//			} else if (sensedPredatorBoolean()) {
//				for (WorldObject w: getSensedObjects()) {
//					if (w instanceof PredatorBug && w.isVisible()) {
//						moveAwayFrom(w, allObjectList);
//						break;
//					}
//				}
//				
			} else {		//if sense objects but none are food, move randomly
				moveRandomly(allObjectList);
			}
		} else {		//if hungerlevel more than 4 move randomly
			moveRandomly(allObjectList);
		}
	}

	public boolean sensedFoodBoolean() {		//go through sensed objects array and, if any are food return true
		for (WorldObject w: getSensedObjects()) {
			if (w instanceof Plant && w.isVisible()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean sensedPredatorBoolean() {		//go through sensed objects array and, if any are predators return true
		for (WorldObject w: getSensedObjects()) {
			if (w instanceof PredatorBug && w.isVisible()) {
				return true;
			}
		}
		return false;
	}

	public void eat(ArrayList<WorldObject> allObjectList) {		//if within-reach-collection contains food eat it
		checkReach(allObjectList);
		for (WorldObject w: this.getReachableObjects()) {
			if(w instanceof Plant) {
				((Plant) w).beEaten();	//make plant be eaten too
				setHungerLevel(getHungerLevel()+4);		//reduce hunger
			}
		}
	}

	//moves direction away from target
	public void moveAwayFrom(WorldObject w, ArrayList<WorldObject> allObjectList) {
		//get position and move toward object
		//		this.setFill(Color.RED);	//debugging
		double relXPos = (w.getTranslateX() - this.getTranslateX()); //establish if target on right or left of bug
		double relYPos = (w.getTranslateY() - this.getTranslateY());
		//		

		if(relXPos < 0) {	//if object is to the right
			setDx(getMaxSpeed());	//set movement to max to right
			//			
		}

		if(relXPos > 0) {	//object is to the left
			setDx(-getMaxSpeed());	//set movement to max to left
		}

		if(relYPos < 0) {	//if object is to the right
			setDy(getMaxSpeed());	//set movement to max to right
		}

		if(relYPos > 0) {	//object is to the left
			setDy(-getMaxSpeed());	//set movement to max to left
		}

		//		
		//capping speed
		if (getDx() > getMaxSpeed()) {
			setDx(getMaxSpeed());
		}
		if (getDy() > getMaxSpeed()) {
			setDy(getMaxSpeed());
		}

		moveBug(allObjectList);	
		eat(allObjectList);

	}

	//will kill bug
	public void beEaten() {
		setHungerLevel(0);
		setVisible(false);
	}



}
