package swrd.game.logic;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import swrd.game.SWRD;
import swrd.game.levels.maps.TileData;
import swrd.game.logic.vector.Vector;

public class Position   {
	private double x;
	private double y;
	private double z = 0; //z cannot be negative
	private double rotation;
	private double width = 1; //x 
	private int lookingDirection;
	private double length = 1;  //y
	private double height = 1; //z
	private BufferedImage image;
	private Color color = Color.CYAN;
	private boolean affectedByGravity;
	private double mass; //kg
	private double speed;
	private double velocityAngle;
	private double maxspeed;
	
	
	
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	
	public Position(double x, double y, double rotation, boolean addToGame) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		z = 0;
		mass = 1;
//		maxspeed = 8;
		
		affectedByGravity = true;
		if (addToGame)
			SWRD.game.gameObjects.add(this);
	}
	
	public void tick() {
		if (speed > 0) {
			Vector movementVector = new Vector(speed, velocityAngle);
			movePosition(movementVector.getXComponent(), movementVector.getYComponent());
		}
	}
	
	
	
	public void movePosition(double x, double y) {
		double newX = this.x + x;
		double newY = this.y + y;
		
		Rectangle2D.Double hitbox = getHitBox();
		hitbox.x = newX;
		hitbox.y = newY;
		
		List<TileData> tiles = SWRD.game.getMap().getIntersectingTiles(hitbox);
		Optional<TileData> wallOp = tiles.stream().filter(tileData -> tileData.getTile().isWall()).findFirst();
		if (wallOp.isEmpty()) {
			this.x += x;
			this.y += y;
		} else {
			TileData wall = wallOp.get();
			setToNextBestPosition(newX, newY, wall);
			speed = 0;
		}
	}
	
	private void setToNextBestPosition(double newX, double newY, TileData wall) {
		Line2D.Double line = new Line2D.Double(x, y, newX, newY);
		Rectangle2D.Double wallBounds = new Rectangle2D.Double(wall.getCol(), wall.getRow(), 1, 1);
		PathIterator wallLines = wallBounds.getPathIterator(null);
		List<Point2D.Double> intersections = new ArrayList<>();
		
		while (!wallLines.isDone()) {
			double[] linePoints = new double[4];
			wallLines.currentSegment(linePoints);
			Point2D.Double intersection = findIntersection(line, new Line2D.Double(linePoints[0], linePoints[1], linePoints[2], linePoints[3]));
			intersections.add(intersection);
			wallLines.next();
		}
		
		Point2D.Double intersection = null;
		double leastDistance = Double.MAX_VALUE;
		for (int i = 0; i < intersections.size(); i++) {
			double currentDistance = swrd.Util.getDistance(x, y, intersections.get(i).getX(), intersections.get(i).getY());
			if (currentDistance < leastDistance) {
				intersection = intersections.get(i);
				leastDistance = currentDistance;
			}
		}
		if (intersection == null) 
			return;
		System.out.println(intersection);
		//cases for all sides of rectangle
		if (intersection.getX() == wallBounds.getX()) {
			this.x = intersection.getX() - width;
			this.y = intersection.getY();
		} else if (intersection.getY() == wallBounds.getY()) {
			this.x = intersection.getX();
			this.y = intersection.getY() - height;
		} else {
			this.x = intersection.getX();
			this.y = intersection.getY();
		}
	}
	
	private static Point2D.Double findIntersection(Line2D.Double l1, Line2D.Double l2) {
        double a1 = l1.getY2() - l1.getY1();
        double b1 = l1.getX1() - l1.getX2();
        double c1 = a1 * l1.getX1() + b1 * l1.getY1();
 
        double a2 = l2.getY2() - l2.getY1();
        double b2 = l2.getX1() - l2.getX2();
        double c2 = a2 * l2.getX1() + b2 * l2.getY1();
 
        double delta = a1 * b2 - a2 * b1;
        return new Point2D.Double((b2 * c1 - b1 * c2) / delta, (a1 * c2 - a2 * c1) / delta);
    }
	
	public void moveTo(double x, double y) {
		this.x = x;
		this.y = y;
		
	}
	
	public Rectangle2D.Double getHitBox() {
		return new Rectangle2D.Double(x, y, width, height);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public double getRotation() {
		return rotation;
	}

	public double getWidth() {
		return width;
	}

	public int getLookingDirection() {
		return lookingDirection;
	}

	public double getLength() {
		return length;
	}

	public double getHeight() {
		return height;
	}

	public BufferedImage getImage() {
		return image;
	}

	public Color getColor() {
		return color;
	}

	public boolean isAffectedByGravity() {
		return affectedByGravity;
	}

	public double getMass() {
		return mass;
	}

	public double getSpeed() {
		return speed;
	}

	public double getVelocityAngle() {
		return velocityAngle;
	}

	public double getMaxspeed() {
		return maxspeed;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	
	//needs development
	public void rotate(double d) { 
		double newRotation = this.rotation + d;
		if (newRotation > 360) {
		 	this.rotation = newRotation % 360;
		} else {
			this.rotation = d;
		}
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public void setLookingDirection(int lookingDirection) {
		this.lookingDirection = lookingDirection;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setAffectedByGravity(boolean affectedByGravity) {
		this.affectedByGravity = affectedByGravity;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void setVelocityAngle(double velocityAngle) {
		this.velocityAngle = velocityAngle;
	}

	public void setMaxspeed(double maxspeed) {
		this.maxspeed = maxspeed;
	}
	
	
}
