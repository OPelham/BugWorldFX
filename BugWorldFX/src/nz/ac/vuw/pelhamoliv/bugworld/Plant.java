package nz.ac.vuw.pelhamoliv.bugworld;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Plant extends WorldObject{
	//fields
	private int size;
	
	public Plant() {
		super(50);
		this.setSize((int)(Math.random()*5+1));
		this.setFill(Color.LAWNGREEN);
		this.setTranslateX(Math.random()* 1100 + 25 );
		this.setTranslateY(Math.random()* 735 + 25 );
	}

	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public void update() {
		die();
		double growthChance = Math.random()*200;	//move to method
		if(isVisible() && growthChance < 1) {
			size++;
		}
		if(size>5) {
			setSize(5);
		}
		checkSize();
	}
	
	public void beEaten() {
		size = size -2;
	}
	
	public void die() {		//move to superclass and make abstract?
		if (size ==0) {
			this.setVisible(false);
		}
	}
	
	public void checkSize() {
		this.setRadius(size*10);
	}
	
//	public void update(ArrayList<WorldObject> allObjectList) {
//		checkReach(allObjectList);
//		interactWithWorld();
//	}
//
//	public void interactWithWorld() {
//		for (WorldObject w: getReachableObjects()) {
//			if(w instanceof Bug) {
//				size--;
//			}
//		}
//	}
}

