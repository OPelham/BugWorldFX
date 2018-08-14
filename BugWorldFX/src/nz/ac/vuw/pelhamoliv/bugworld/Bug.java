package nz.ac.vuw.pelhamoliv.bugworld;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;

/**Bug is the superclass of preybug and predator bug
 * holds all common methods of these two
 * Is abstract
 * 
 * @author pelhamoliv
 *
 */

public abstract class Bug extends WorldObject {
	//fields
	private Scene scene;		//for knowing boundaries
	private float maxSpeed;			//max allowed speed 
	private double senseRange;		//range in which bug is aware of other objects
	private int hungerLevel;		//current level of hunger (used to determine if need food and if dies)
	private float dx = -3.5f; 		//how far to move in x plane 
	private float dy = -3.5f;		//how far to move in y plane 
	private List<WorldObject> sensedObjects = new ArrayList<WorldObject>(); //collection of all objects within sense range that are alive

	//constructor
	public Bug(Scene scene) {
		super(10, scene);	//sets Radius calling parent contructor
		this.maxSpeed = 4.0f;
		this.scene = scene;
		this.setHungerLevel(10);
//		this.setTranslateX(Math.random()* 1200 );
//		this.setTranslateY(Math.random()* 800 );
		this.setSenseRange(200);
		this.checkSize();
		///the following randominses initial direction
		double randx = Math.random()*2;
		double randy = Math.random()*2;
		if (randx > 1) {
			this.dx = -dx;
		}
		if (randy >1) {
			this.dy = -dy;
		}
	}

	//accessor methods
	public List<WorldObject> getSensedObjects() {
		return sensedObjects;
	}
	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public float getDy() {
		return dy;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	private double getSenseRange() {
		return senseRange;
	}

	private void setSenseRange(double senseRange) {
		this.senseRange = senseRange;
	}

	public int getHungerLevel() {
		return this.hungerLevel;
	}
	public void setHungerLevel(int hungerLevel) {
		this.hungerLevel = hungerLevel;
	}

	//Methods
	
	//updatmethod
	public void update(ArrayList<WorldObject> allObjectList) {
		makeHungry();	//first change hunger level
		senseArea(allObjectList);	//fill array of objects within sensory range
		decideAction(allObjectList);	//decide if will move randomly or with purpose
		checkInBounds();		//check if in boundaries, if not move to closest available space
		checkReach(allObjectList);		//check if any objects are close enough to interact with (eg eat or be eaten)
		if (hungerLevel == 0) {		//move if starvation then die (kept this check sepaerate so can die from being contacted by predator)
			die(allObjectList);
		}
		checkSize();		//update radius to reflect hunger level
	}

	public void makeHungry() {		//chance of increasing hunger level
		double hungerOdds = Math.random()*100;
		if(hungerOdds<1) {
			hungerLevel--;
		}
	}

	//a class to move bug randomly (direction and speed)
	public void moveRandomly(ArrayList<WorldObject> allObjectList) {
		//		this.setFill(Color.BLACK);	//debugging
		double randx = Math.random()*40;	//for use in calculating odds of direction change
		double randy = Math.random()*40;
		double randSpeed = Math.random()*10;	//for use in changing speed

		//direction change
		if (randx < 1) {
			this.dx = -dx;
		}
		if (randy < 1) {
			this.dy = -dy;
		}
		//speed change
		if (randSpeed <1) {	
			this.dx += Math.random()-.5;
			this.dy += Math.random()-.5;
		}
		//capping speed
		if (this.dx > maxSpeed) {
			this.dx = maxSpeed;
		}
		if (this.dy > maxSpeed) {
			this.dy = maxSpeed;
		}

		//now given this speed and direction, move
		moveBug(allObjectList);

	}

	public void moveBug(ArrayList<WorldObject> allObjectList) {
		//movement
		//first check if collision on move if none move
		double potnX = this.getTranslateX() + dx;	//potential move location
		double potnY = this.getTranslateY() + dy;
		if(!(this.checkCollisions( potnX, potnY, allObjectList))) {	//if no collision at potential location move here, else no movement
			this.setTranslateX(this.getTranslateX() + dx);
			this.setTranslateY(this.getTranslateY() + dy);
		}
	}

	//a method to check if the bug is within bounds of scene 
	public void checkInBounds() {
		//four checks of >= border (dont forget radius) l/r/t/b then move to be inside
		//if outside rhs bound move to closest x pos which is valid
		if (this.getCenterX() + this.getTranslateX() + this.getRadius() > (scene.getWidth())) {
			this.setTranslateX((scene.getWidth())-1 - this.getRadius() - this.getCenterX());
			//could also set dx in direction away from wall
		}
		//if outside lhs bound move to closest x pos which is valid
		if (this.getCenterX() + this.getTranslateX() - this.getRadius() < 1) {
			this.setTranslateX(1 + this.getRadius() - this.getCenterX()); //sets CenterX position to radius +1
		}
		//if outside top bound move to closest y pos which is valid
		if (this.getCenterY() + this.getTranslateY() - this.getRadius() < 1) {
			this.setTranslateY(1 + this.getRadius() - this.getCenterY()); //sets CenterX position to radius +1
		}
		//if outside bottom bound move to closest y pos which is valid
		if (this.getCenterY() + this.getTranslateY() + this.getRadius() > (scene.getHeight()) - 25) { //-25 for top border pane
			this.setTranslateY((scene.getHeight()) - this.getRadius() - this.getCenterY() -25);
		}

	}

	//finds all objects with in senserange
	//and decides which type of movement is required?
	public void senseArea(ArrayList<WorldObject> allObjectList) { 
		//first check all objects to see which are in range
		sensedObjects.clear();	//clear incase prev obj have left range
		for (WorldObject d: allObjectList) {
			if(this!=d) {
				// check the distances for each and compare to sense range
				checkRange(d);
			}
		}
	}

	public void checkRange(WorldObject d) { // needs refining
		//could refine this to take parameter of range so that can be used in other areas

		//use pythagoras for determining if collision
		double deltaX = (this.getTranslateX()) - (d.getTranslateX()) ;
		double deltaY = (this.getTranslateY()) - (d.getTranslateY());
		//now see if distance between 2 is less than the two radii + sense range
		double distance = (Math.sqrt ((deltaX * deltaX) + (deltaY * deltaY)) ) - this.getRadius() - d.getRadius();
		double minDistance =  this.getSenseRange() + d.getRadius() + this.getRadius() ;
		if(distance < minDistance) {
			sensedObjects.add(d);
		}
	}

	public abstract void decideAction(ArrayList<WorldObject> allObjectList);
//	{
//		//need to sort by porximity
//		//insert others as add more classes
//		if(getHungerLevel()<4) {		//if hunger level less than 4 search for food
//			if (sensedObjects.isEmpty()) {
//				moveRandomly(allObjectList);
//			} else if(sensedFoodBoolean()){
//				for (WorldObject w: sensedObjects) {
//					if (w instanceof Plant && w.isVisible()) {
//						moveToward(w, allObjectList);
//						break;
//					}
//
//				}
//			} else {
//				moveRandomly(allObjectList);
//			}
//		} else {
//			moveRandomly(allObjectList);
//		}
//

//	}

	public abstract boolean sensedFoodBoolean(); 
//	{
//		for (WorldObject w: sensedObjects) {
//			if (w instanceof Plant && w.isVisible()) {
//				return true;
//			}
//		}
//		return false;
//	}

	//SHould try more than just altering direction?
	public void moveToward(WorldObject w, ArrayList<WorldObject> allObjectList) {
		//get position and move toward object
		//		this.setFill(Color.RED);	//debugging
		double relXPos = (w.getTranslateX() - this.getTranslateX()); //establish if target on right or left of bug
		double relYPos = (w.getTranslateY() - this.getTranslateY());
		//		

		if(relXPos > 0) {	//if object is to the right
			setDx(maxSpeed);	//set movement to max to right
			//			
		}

		if(relXPos < 0) {	//object is to the left
			setDx(-maxSpeed);	//set movement to max to left
		}

		if(relYPos > 0) {	//if object is to the right
			setDy(maxSpeed);	//set movement to max to right
		}

		if(relYPos < 0) {	//object is to the left
			setDy(-maxSpeed);	//set movement to max to left
		}

		//		
		//capping speed
		if (this.dx > maxSpeed) {
			this.dx = maxSpeed;
		}
		if (this.dy > maxSpeed) {
			this.dy = maxSpeed;
		}

		moveBug(allObjectList);	
		eat(allObjectList);

	}

	public abstract void eat(ArrayList<WorldObject> allObjectList); 
//	{
//		checkReach(allObjectList);
//		for (WorldObject w: this.getReachableObjects()) {
//			if(w instanceof Plant) {
//				((Plant) w).beEaten();
//				hungerLevel += 4;
//			}
//		}
//	}

	public void die(ArrayList<WorldObject> allObjectList) {
		this.setVisible(false);
		
	}

	public void checkSize() {
		this.setRadius(hungerLevel+10);
	}



}
