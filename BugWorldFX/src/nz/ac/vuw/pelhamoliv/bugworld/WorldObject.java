package nz.ac.vuw.pelhamoliv.bugworld;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javafx.collections.transformation.SortedList;
import javafx.scene.shape.Circle;

public class WorldObject extends Circle{
	private List<WorldObject> reachableObjects = new ArrayList<WorldObject>(); //any objects in range
	//need way of sorting this by distance
	

	public WorldObject() {
		super();
	}

	public WorldObject(double radius) {
		super();
		this.setRadius(radius);

	}

//	public boolean conductCollision(WorldObject o) {
//		//use pythagoras for determining if collision
//		double deltaX = o.getCenterX() + o.getTranslateX() - this.getCenterX() + this.getTranslateX();
//		double deltaY = o.getCenterY() + o.getTranslateY() - this.getCenterY() + this.getTranslateY();
//		//now see if distance between 2 is less than the two radii
//		double distance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
//		double minDistance = o.getRadius() + this.getRadius();
//		return(distance < minDistance);
//	}

	public boolean conductCollision(double potnX, double potnY, WorldObject o) {
		//use pythagoras for determining if collision
		double deltaX = o.getCenterX() + o.getTranslateX() - potnX;
		double deltaY = o.getCenterY() + o.getTranslateY() - potnY;
		//now see if distance between 2 is less than the two radii
		double distance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
		double minDistance = o.getRadius() + this.getRadius();
		return(distance < minDistance);
	}
	
	public boolean checkCollisions(double potnX, double potnY, ArrayList<WorldObject> allObjectList) {

		for (WorldObject d: allObjectList) {
			if(this!=d) {
				if(this.conductCollision(potnX, potnY, d)) {
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
