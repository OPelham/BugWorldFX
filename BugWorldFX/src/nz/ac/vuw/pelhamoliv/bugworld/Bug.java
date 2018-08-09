package nz.ac.vuw.pelhamoliv.bugworld;

import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Bug extends Circle {
	//fields
	private Stage primaryStage;
	
	
//	float x = (float) (Math.random()*200+1);
//	float y = (float) (Math.random()*200+1);
	
	private float dx = -3.5f; 
	private float dy = -3.5f;
	
	//constructor
	public Bug(Stage primaryStage) {
		super(5);
		this.setCenterX((float) (Math.random()*250+50));
		this.setCenterY((float) (Math.random()*250+50));
		this.primaryStage = primaryStage;
		double randx = Math.random()*2;
		double randy = Math.random()*2;
		if (randx > 1) {
			this.dx = -dx;
		}
		if (randy >1) {
			this.dy = -dy;
		}
		
	}
	
	//a class to choose direction to take
	public void navigation() {
		
	}
	
	
	//a class to move bug randomly
	//there will be a one in 10 chance of changeing direction for each of y and x directions
	public void moveRandomly() {
		double randx = Math.random()*30;
		double randy = Math.random()*25;
		double randSpeed = Math.random()*10;
		
		//direction change
		if (randx < 1) {
			this.dx = -dx;
		}
		if (randy < 1) {
			this.dy = -dy;
		}
		//speed change
		if (randSpeed <1) {
			this.dx += Math.random()-.5;
			this.dy += Math.random()-.5;
		}
	}

	//updatemehtof
	public void update() {
		moveRandomly();
		if(this.getCenterX() + this.getTranslateX() <= this.getRadius() || 		//if the radius moves beyond left boundary
				this.getCenterX() + this.getTranslateX() + this.getRadius() >= primaryStage.getWidth()-20) {	//if the radius moves beyond right boundary
			dx = - dx;
		}

		if(this.getCenterY() + this.getTranslateY() <= this.getRadius() ||			//if the radius moves beyond top boundary
				this.getCenterY() + this.getTranslateY() + this.getRadius() >= primaryStage.getHeight()-45) {	//if the radius moves beyond bottom boundary
			dy = - dy;
		}

		this.setTranslateX(this.getTranslateX() + dx);
		this.setTranslateY(this.getTranslateY() + dy);
	}
}
