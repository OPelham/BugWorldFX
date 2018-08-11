package nz.ac.vuw.pelhamoliv.bugworld;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Plant extends WorldObject{
	//fields
	private int size;
	
	public Plant() {
		super(10);
		this.size = (int)(Math.random()*10+1);
		this.setFill(Color.LAWNGREEN);
		this.setTranslateX(Math.random()* 1150 + 25 );
		this.setTranslateY(Math.random()* 750 + 25 );
	}

}
