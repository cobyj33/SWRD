package swrd.game.logic.vector;

public class VelocityVector extends Vector {
	
	int duration;
	int accelerationTime;
	
	public VelocityVector(double amount, double angle, int duration) {
		super(amount, angle);
		this.duration = duration;
		accelerationTime = duration / 10;
	}

}
