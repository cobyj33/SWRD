package swrd.game.entities;

import java.util.concurrent.TimeUnit;

import swrd.Main;
import swrd.game.SWRD;
import swrd.game.logic.Position;

public class Entity extends Position {
	
	public static final int INVULNERABILITY_WINDOW = 300;
	protected boolean moving, jumping, crouching, sprinting, rolling;
	
	public boolean vulnerable = true;
	
	public int health, maxHealth, attack, defense, walkSpeed, sprintSpeed, stamina, maxStamina, jumpHeight, jumpTime, maxSpeed; 
	
	protected Entity(int x, int y, int rotation) {
		super(x, y, rotation, true);
	}
	
	public void jump() {
		
	}
	
	public void takeDamage(int damage) {
		System.out.println("Health: " + health);
		if (vulnerable) {
			health -= damage;
			vulnerable = false;
			Main.scheduler.schedule(() -> vulnerable = true, INVULNERABILITY_WINDOW, TimeUnit.MILLISECONDS);
		}
		
		if (health <= 0) {
			SWRD.game.gameObjects.remove(this);
		}
	}
	
	@Override
	public void setSpeed(double speed) {
		System.out.println("Entity.setspeed() " + speed);
		super.setSpeed( Math.min(speed, maxSpeed) );
	}
	
}
