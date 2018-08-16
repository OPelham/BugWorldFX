package nz.ac.vuw.pelhamoliv.bugworld;

import javafx.scene.shape.Circle;
import javafx.scene.Scene;

/**WorldObject class is the parent class for all objects used in the simulation
 * it includes constructor to set radius and to randomise location based on scene size
 * 
 * @author pelhamoliv
 *
 */
public class WorldObject extends Circle{
	
	//constructors
	public WorldObject(double radius, Scene scene) {
		super();
		this.setRadius(radius);
		//could modify following to take scene
		//following sets position between bounds of scene (with radius in bounds)
		this.setTranslateX(Math.random()* (scene.getWidth()-this.getRadius()*2) + this.getRadius() );	//set location
		this.setTranslateY(Math.random()* ((scene.getHeight()-25) - this.getRadius()*2) + this.getRadius() );	//set location
	}
}
