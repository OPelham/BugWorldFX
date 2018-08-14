package nz.ac.vuw.pelhamoliv.bugworld;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Circle;
import javafx.scene.Scene;

/**WorldObject class is the parent class for all objects used in the simulation
 * it includes - collision methods
 * 			   - checkReach (eat or be eaten)
 * 
 * @author pelhamoliv
 *
 */
public class WorldObject extends Circle{
	//fields
	private List<WorldObject> reachableObjects = new ArrayList<WorldObject>(); //any objects in range

	//constructors
	public WorldObject(double radius, Scene scene) {
		super();
		this.setRadius(radius);
		//could modify following to take scene
		//following sets position between bounds of scene (with radius in bounds)
		this.setTranslateX(Math.random()* (scene.getWidth()-this.getRadius()*2) + this.getRadius() );	//set location
		this.setTranslateY(Math.random()* ((scene.getHeight()-25) - this.getRadius()*2) + this.getRadius() );	//set location
	}
	
	//accessor methods
	public List<WorldObject> getReachableObjects() {
		return reachableObjects;
	}
	
	//could make the following two methods into one method

	//establishes weather a collsion has occured given potential move coordinates
	//put in this class for the potential of checking for validity of position during initial spawn
	//ie all classes will need to check
	public boolean conductCollision(double potnX, double potnY, WorldObject o) {
		double deltaX = o.getCenterX() + o.getTranslateX() - potnX;	//calculates difference on x plane of the two objects
		double deltaY = o.getCenterY() + o.getTranslateY() - potnY; //calculates difference on x plane of the two objects
		//now see if distance between 2 is less than the two radii ie there is a collision
		double distance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY)); //the straight line distance between two object centers
		double minDistance = o.getRadius() + this.getRadius(); //smallest valid distance between these objects is the sum of their respective radii
		return(distance < minDistance);		//return true if a collision
	}

	//goes through all other world objects in world and sees if this object will collide with each
	//returns true if collides with first checked
	//need to find way of sorting by distance
	public boolean checkCollisions(double potnX, double potnY, ArrayList<WorldObject> allObjectList) {
		for (WorldObject d: allObjectList) {
			if(this!=d && d.isVisible()) {	//not dead
				if(this.conductCollision(potnX, potnY, d)) { //see if this object collides with 
					return true;
				}
			}
		}
		return false;
	}

	//checks within interactable area for interactable objects eg for eating
	//this range is 2 wider than objects radius
	public void checkReach(ArrayList<WorldObject> allObjectList) {
		reachableObjects.clear();	//clears those in range from last move
		for(WorldObject d: allObjectList) {
			if(d.isVisible()) {	//not dead
				//use pythagoras to establish if within range
				double deltaX = (this.getTranslateX()) - (d.getTranslateX()) ;	//gets x component of distance
				double deltaY = (this.getTranslateY()) - (d.getTranslateY());	//gets y component of distance
				//now use pythagoras to see actual distance
				double distance = (Math.sqrt ((deltaX * deltaX) + (deltaY * deltaY)) ) - this.getRadius() - d.getRadius();
				//now see if distance between 2 is less than the two radii + sense range
				double minDistance =  2 + d.getRadius() + this.getRadius() ;
				if(distance < minDistance) {
					reachableObjects.add(d);	//if true add to reachable object collection
				}
			}
		}
	}

}
