package nz.ac.vuw.pelhamoliv.bugworld;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Plant extends WorldObject{
	//fields
	private int size;
	
	public Plant() {
		super(10);
		this.setSize((int)(Math.random()*10+10));
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
		size++;
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

