package swrd.game.logic.vector;

import java.util.Objects;

public class Vector {
	public double amount;
	public double angle;
	
	public Vector(double amount, double angle) {
		super();
		this.amount = amount;
		this.angle = angle;
	}
	
	public double getXComponent() {
		return amount * Math.cos(Math.toRadians(angle));
	}
	
	public double getYComponent() {
		return -amount * Math.sin(Math.toRadians(angle));
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, angle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector other = (Vector) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Double.doubleToLongBits(angle) == Double.doubleToLongBits(other.angle);
	}

	
	
}
