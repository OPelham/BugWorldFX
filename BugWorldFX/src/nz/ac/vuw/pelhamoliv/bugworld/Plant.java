package nz.ac.vuw.pelhamoliv.bugworld;


/**plant class that grows and is eaten for food by prey bugs
 * 
 * @author pelhamoliv
 *
 */
public class Plant extends WorldObject{
	//fields
	private int foodQuantity; //this is the value of food this tree represents (how much left to eat)

	//constructor
	public Plant() {
		super(50);		//calls parent constructor with radius 50
		this.setSize((int)(Math.random()*5+1));	//setting size
//		this.setTranslateX(Math.random()* 1100 + 25 );	//set initial location
//		this.setTranslateY(Math.random()* 735 + 25 );
	}

	//accessor methods
	public int getSize() {
		return foodQuantity;
	}
	public void setSize(int size) {
		this.foodQuantity = size;
	}

	//methods
	public void update() {
		die();	//checks of plant will die this round(based on current size
		grow();		//fires method for growth of size 
		checkSize();	//checks if radius needs to update to reflect foodQuantity change
	}

	public void beEaten() {	//if eaten from
		foodQuantity = foodQuantity -2;
	}

	public void grow() {		//grow food quanitity
		double growthChance = Math.random()*200;	//one in 200 chance of growth each update
		if(isVisible() && growthChance < 1) {		//will not grow if dead
			foodQuantity++;
		}
		if(foodQuantity>5) {	//this is the max foodquantity size
			setSize(5);
		}
	}
	
	public void die() {		//move to superclass and make abstract, no as obstacles dont die?
		if (foodQuantity ==0) {
			this.setVisible(false);		//if no food left then set not visible. methods checking objects in world will check if visible before including this object
		}
	}

	public void checkSize() {		//this updates radius incase food quantity has changed (either by growing or being eaten)
		this.setRadius(foodQuantity*10);
	}

}

