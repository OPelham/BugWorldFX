package nz.ac.vuw.pelhamoliv.bugworld;

import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Bug extends Circle {
	//fields
	private Stage primaryStage;
	private float maxSpeed;
	
	
//	float x = (float) (Math.random()*200+1);
//	float y = (float) (Math.random()*200+1);
	
	private float dx = -3.5f; 
	private float dy = -3.5f;
	
	//constructor
	public Bug(Stage primaryStage) {
		super(4);
		this.maxSpeed = 4.0f;
		this.primaryStage = primaryStage;
		this.setTranslateX(Math.random()* 1200 );
		this.setTranslateY(Math.random()* 800 );
//		
//		this.setCenterX((float) (Math.random()*primaryStage.getMaxWidth()-18));
//		this.setCenterY((float) (Math.random()*primaryStage.getMaxHeight()-45));
		
		
		///the following randominses initial direction
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
	
	
	//a class to move bug randomly (direction and speed)
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
		//capping speed
		if (this.dx > maxSpeed) {
			this.dx = maxSpeed;
		}
		if (this.dy > maxSpeed) {
			this.dy = maxSpeed;
		}
	}
	
	//a method to check if the bug is within bounds -- 
	public void checkInBounds() {
		//four checks of >= border (dont forget radius) l/r/t/b then move to be inside
		//if outside rhs bound move to closest x pos which is valid
		if (this.getCenterX() + this.getTranslateX() + this.getRadius() > (primaryStage.getWidth()-18)) {
			this.setTranslateX((primaryStage.getWidth()-18)-1 - this.getRadius() - this.getCenterX());
			//could also set dx in direction away from wall
		}
		//if outside lhs bound move to closest x pos which is valid
		if (this.getCenterX() + this.getTranslateX() - this.getRadius() < 1) {
			this.setTranslateX(1 + this.getRadius() - this.getCenterX()); //sets CenterX position to radius +1
		}
		//if outside top bound move to closest y pos which is valid
		if (this.getCenterY() + this.getTranslateY() - this.getRadius() < 1) {
			this.setTranslateY(1 + this.getRadius() - this.getCenterY()); //sets CenterX position to radius +1
		}
		//if outside bottom bound move to closest y pos which is valid
		if (this.getCenterY() + this.getTranslateY() + this.getRadius() > (primaryStage.getHeight()-45)) {
			this.setTranslateY((primaryStage.getHeight()-45) - this.getRadius() - this.getCenterY());
		}
		
	}

	//updatmethod
	public void update() {
		moveRandomly();
		
		if(this.getCenterX() + this.getTranslateX() <= this.getRadius() || 		//if the radius moves beyond left boundary
				this.getCenterX() + this.getTranslateX() + this.getRadius() >= primaryStage.getWidth()-18) {	//if the radius moves beyond right boundary
			dx = - dx;
		}
		//this part identifies movement will take them outside boundry right boundry, if so move left
		

		if(this.getCenterY() + this.getTranslateY() <= this.getRadius() ||			//if the radius moves beyond top boundary
				this.getCenterY() + this.getTranslateY() + this.getRadius() >= primaryStage.getHeight()-45) {	//if the radius moves beyond bottom boundary
			dy = - dy;
		}

		this.setTranslateX(this.getTranslateX() + dx);
		this.setTranslateY(this.getTranslateY() + dy);
		checkInBounds();
	}
}
