package nz.ac.vuw.pelhamoliv.bugworld;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javafx.collections.transformation.SortedList;
import javafx.scene.shape.Circle;

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
	//need way of sorting this by distance
	
	//constructors
	public WorldObject() {
		super();
	}
	public WorldObject(double radius) {
		super();
		this.setRadius(radius);
	}

	//establishes weather a collsion has occured given potential move coordinates
	//potentially come back and relate this to eating/being eaten or move to bug class as these move...
	//use pythagoras for determining if collision
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
			if(this!=d) {
				if(this.conductCollision(potnX, potnY, d)) { //see if this object collides with 
					return true;
				}
			}
		}
		return false;
	}

	
	
	//eat of be eaten
	public void checkReach() {
		
	}


}
