package nz.ac.vuw.pelhamoliv.bugworld;

import javafx.scene.shape.Circle;

public class Plant extends Circle{
	//fields
	private int size;
	
	public Plant() {
		super(5);
		this.size = (int)(Math.random()*10+1);
	}

}
