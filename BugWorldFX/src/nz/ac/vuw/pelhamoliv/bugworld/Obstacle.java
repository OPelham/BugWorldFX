package nz.ac.vuw.pelhamoliv.bugworld;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Obstacle extends WorldObject{
	//fields
	
	public Obstacle() {
		super(Math.random()*50+10);
		this.setTranslateX(Math.random()* 1140 + 28 );
		this.setTranslateY(Math.random()* 700 + 35);
	}

}
