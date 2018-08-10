package nz.ac.vuw.pelhamoliv.bugworld;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Obstacle extends Circle{
	//fields
	
	public Obstacle() {
		super(10);
		this.setFill(Color.GREY);
		this.setTranslateX(Math.random()* 1200 );
		this.setTranslateY(Math.random()* 800 );
	}

}
