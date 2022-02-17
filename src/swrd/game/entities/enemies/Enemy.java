package swrd.game.entities.enemies;

import swrd.game.logic.Camera;
import swrd.game.logic.vector.Vector;
import swrd.interfaces.Colliding;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import swrd.Resources;
import swrd.Util;
import swrd.game.SWRD;
import swrd.game.entities.Entity;

public class Enemy extends Entity implements Colliding {
	
	public int state;
	public static final int FOLLOW = 0;
	public Path2D view;
	public int viewDistance;
	public int FOV = 60;
	public int rotationSpeedPerSecond = 360; //per second
	
	public Enemy(int x, int y, int rotation) {
		super(x, y, rotation);
		health = 10;
		maxSpeed = 16;
		walkSpeed = 8;
		
		attack = 1;
		setImage(Resources.getImage(Resources.ImageEnum.CHARACTER));
		setRotation(0);
		state = FOLLOW;
		viewDistance = 5;
		view = new Path2D.Double();
	}
	
	public void tick() {
		constructView();
		double angleToPlayer = Util.getAngle(this.getX(), this.getY(), SWRD.player.getX(), SWRD.player.getY());
//		System.out.println("ENEMY TICK");
		if (state == FOLLOW) {
//			if (Util.getDistance(SWRD.player.x, SWRD.player.y, this.x, this.y) < 5) {
//				double angle = Util.getAngle(this.x, this.y, SWRD.player.x, SWRD.player.y);
//				Vector movement = new Vector(moveSpeed * (SWRD.DELAY / 1000.), angle);
//				this.moveTo(this.x + movement.getXComponent(), this.y + movement.getYComponent());
//			}
			
			if (view.contains(SWRD.player.getX(), SWRD.player.getY())) {
//				System.out.println("PLAYER IN VIEW");
				Vector movement = new Vector(walkSpeed * (SWRD.DELAY / 1000.), angleToPlayer);
				this.moveTo(this.getX() + movement.getXComponent(), this.getY() + movement.getYComponent());
				
				double maxRotation  = rotationSpeedPerSecond * (SWRD.DELAY / 1000.);
//				System.out.println("MAX ROTATION: " + maxRotation);
				rotate( Math.min(maxRotation, angleToPlayer));
//				System.out.println(" ENEMY ROTATION: " + getRotation());
			}
		}
		

	}
	
	private void constructView() {
		view.reset();
		Vector center = new Vector(viewDistance, getRotation());
		Vector leftSize = new Vector(viewDistance, getRotation() - FOV);
		Vector rightSize = new Vector(viewDistance, getRotation() + FOV);
		
		view.moveTo(getX(), getY());
		view.lineTo(getX() + leftSize.getXComponent(), getY() + leftSize.getYComponent());
		view.lineTo(getX() + center.getXComponent(), getY() + center.getYComponent());
		view.lineTo(getX() + rightSize.getXComponent(), getY() + rightSize.getYComponent());
		view.closePath();
	}

	@Override
	public void checkCollisions() {
		Rectangle2D.Double hitbox = this.getHitBox();
		Rectangle2D.Double playerHitbox = SWRD.player.getHitBox();
		if (hitbox.intersects(playerHitbox)) {
			SWRD.player.takeDamage(this.attack);
		}
		
	}
	
	
}
