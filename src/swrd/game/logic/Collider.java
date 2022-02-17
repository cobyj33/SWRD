package swrd.game.logic;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.plugins.tiff.ExifParentTIFFTagSet;

import swrd.Main;
import swrd.game.SWRD;
import swrd.game.entities.Entity;
import swrd.interfaces.Colliding;

public class Collider extends Position implements Colliding {
	int duration;
	int damage;
	boolean multiHit;
	Position parent;
	Entity source;
	List<Entity> hits;
	
	public Collider(double x, double y, int damage, int duration, boolean multiHit) {
		super(x, y, 0, true);
		construct(damage, duration, multiHit, source);
	}
	
	public Collider(double x, double y, int damage, int duration, boolean multiHit, Entity source) {
		super(x, y, 0, true);
		construct(damage, duration, multiHit, source);
	}
	
	private void construct(int damage, int duration, boolean multiHit, Entity source) {
		this.damage = damage;
		this.duration = duration;
		this.multiHit = multiHit;
		this.source = source;
		setColor(Color.RED);
		hits = new ArrayList<>();
		
		if (duration > 0) {
			Main.scheduler.schedule(() -> SWRD.game.gameObjects.remove(this), duration, TimeUnit.MILLISECONDS);
		}
	}
	
	public void setParent(Position parent) {
		
	}
	
	public void setSource(Entity source) {
		this.source = source;
	}
	
	public void tick() {
		super.tick();
//		if (parent != null) {
//			setX(parent.getX());
//			setY(parent.getY());
//			setRotation(parent.getRotation());
//			setWidth(parent.getWidth());
//			setLength(parent.getLength());
//			setHeight(parent.getHeight());
//		}
	}

	@Override
	public void checkCollisions() {
		ArrayList<Entity> entities = SWRD.game.getEntities();
		for (int e = 0; e < entities.size(); e++) {
			if (entities.get(e).getHitBox().intersects(this.getHitBox())) {
				
				if (source != null) {
					if (!source.equals(entities.get(e))) {
						entities.get(e).takeDamage(damage);
					}
				} else {
					entities.get(e).takeDamage(damage);
				}
				
			}
		}
	}
	
	
}
