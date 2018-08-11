package nz.ac.vuw.pelhamoliv.bugworld;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Obstacle extends WorldObject{
	//fields
	
	public Obstacle() {
		super(30);
		this.setFill(Color.GREY);
		this.setTranslateX(Math.random()* 1100 + 25 );
		this.setTranslateY(Math.random()* 720 + 35);
	}

}
