package nz.ac.vuw.pelhamoliv.bugworld;

import javafx.scene.Scene;

/**This class creates an obstable
 * 
 * @author pelhamoliv
 *
 */
public class Obstacle extends WorldObject{
	//constructor
	public Obstacle(Scene scene) {
		super(Math.random()*50+10, scene);	//set radius

	}

}
