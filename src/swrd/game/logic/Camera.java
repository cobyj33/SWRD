package swrd.game.logic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

import swrd.game.SWRD;
import swrd.game.entities.enemies.Enemy;
import swrd.game.levels.maps.GameMap;
import swrd.game.levels.maps.Tile;


public class Camera extends Position {
	//note, tiles are rendered with game coordinates of width = squaresize and height = squaresize;
	private double zoom;
	private double width;
	private int areaSize;
	public static int SQUARESIZE;
	public int state;
	
	public static final int FOLLOW_PLAYER = 0;
	private int renderOffsetX;
	private int renderOffsetY;
	
	public Camera(double x, double y, double rotation) {
		super(x, y, rotation, false);
		zoom = 1;
		SQUARESIZE = 10;
		areaSize = 12;
		state = FOLLOW_PLAYER;
//		SWRD.game.gameObjects.remove(this);
	}
	
	public void render(JPanel screen, Graphics2D g2D) {
		//calculates square size
		AffineTransform original = g2D.getTransform();
//		double sin = Math.abs(Math.sin(Math.toRadians(rotation))),
//		           cos = Math.abs(Math.cos(Math.toRadians(rotation)));
//		    int w = screen.getWidth();
//		    int h = screen.getHeight();
//		    int neww = (int) Math.floor(w*cos + h*sin),
//		        newh = (int) Math.floor(h*cos + w*sin);
//		
//		g2D.translate((neww-w)/2, (newh-h)/2);
//	    g2D.rotate(Math.toRadians(rotation), w/2, h/2);
		areaSize = (int) (zoom * 12);
		int bounds = screen.getWidth() >= screen.getHeight() ? screen.getWidth() : screen.getHeight();
		SQUARESIZE = bounds / areaSize;
		
		
		
		GameMap gameMap = SWRD.game.getMap();
		
		for (int i = 0; i < gameMap.numOfLayers(); i++) {
			Tile[][] layer = gameMap.getLayer(i);
			int tileRow = (int)this.getY();
			int tileCol = (int)this.getX();
			
			renderOffsetX = (int) ((this.getX() - tileCol) * SQUARESIZE);
			renderOffsetY = (int) ((this.getY() - tileRow) * SQUARESIZE);
	
			tileRow = (int) this.getY();
			for (int row = 0; row < areaSize + 1; row++) {
				tileCol = (int) this.getX();
				for (int col = 0; col < areaSize + 1; col++) {
					
					try {
						Tile current = layer[tileRow][tileCol];
						if (current != null) {
							
							if (current.image != null) {
								g2D.drawImage(current.image, col * SQUARESIZE - renderOffsetX, row * SQUARESIZE - renderOffsetY, SQUARESIZE, SQUARESIZE, null);
							} else {
								g2D.setColor(current.color);
								g2D.fillRect(col * SQUARESIZE - renderOffsetX, row * SQUARESIZE - renderOffsetY, SQUARESIZE, SQUARESIZE); 
							}
							
							
						}
					} catch (IndexOutOfBoundsException e) {
						//System.out.println("OUT OF BOUNDS");
						g2D.setColor(Color.BLACK);
						g2D.fillRect(col * SQUARESIZE - renderOffsetX, row * SQUARESIZE - renderOffsetY, SQUARESIZE, SQUARESIZE);
					}
					
					g2D.setColor(Color.BLACK);
					g2D.drawRect(col * SQUARESIZE - renderOffsetX, row * SQUARESIZE - renderOffsetY, SQUARESIZE, SQUARESIZE);
					
					tileCol++;
				}
				tileRow++;
				}
			}
		
		g2D.setColor(Color.YELLOW);
		List<Position> gameObjects = SWRD.game.gameObjects;
		
		for (int p = 0; p < gameObjects.size(); p++) {
			Position pos = gameObjects.get(p);
			Position cameraPos = getPosOnCamera(pos);
			BufferedImage sprite = pos.getImage();
			
			if (sprite != null) {
				if (pos.getLookingDirection() == Position.LEFT) {
//					System.out.println("Looking left");
					g2D.drawImage(sprite, (int) (cameraPos.getX() * SQUARESIZE) + SQUARESIZE, (int) (cameraPos.getY() * SQUARESIZE) + (int)(SWRD.player.getZ()), -SQUARESIZE, SQUARESIZE, null);
				} else { g2D.drawImage(sprite, (int) (cameraPos.getX() * SQUARESIZE), (int) (cameraPos.getY() * SQUARESIZE) + (int)(SWRD.player.getZ()) , SQUARESIZE, SQUARESIZE, null); }
			} else {
				
				g2D.setPaint(pos.getColor());
				g2D.fillRect((int) (cameraPos.getX() * SQUARESIZE), (int) (cameraPos.getY() * SQUARESIZE) + (int)(cameraPos.getZ()), SQUARESIZE, SQUARESIZE);
			}
		}
		
		
//		Position pos = getPosOnCamera(SWRD.game.getPlayer());
//		BufferedImage playerSprite = SWRD.game.getPlayer().getImage();
//		if (playerSprite != null) {
//			if (SWRD.game.getPlayer().lookingDirection == Position.LEFT) {
////				System.out.println("Looking left");
//				g2D.drawImage(playerSprite, (int) (pos.getX() * SQUARESIZE) + SQUARESIZE, (int) (pos.getY() * SQUARESIZE) + (int)(SWRD.game.getPlayer().getZ()), -SQUARESIZE, SQUARESIZE, null);
//			} else { g2D.drawImage(playerSprite, (int) (pos.getX() * SQUARESIZE), (int) (pos.getY() * SQUARESIZE) + (int)(SWRD.game.getPlayer().getZ()) , SQUARESIZE, SQUARESIZE, null); }
//		} else {
//			g2D.fillRect((int) (pos.getX() * SQUARESIZE), (int) (pos.getY() * SQUARESIZE) + (int)(pos.getZ()), SQUARESIZE, SQUARESIZE);
//		}
		
		
//		g2D.fillRect((int) (SWRD.game.getPlayer().getX() * SQUARESIZE), (int) (SWRD.game.getPlayer().getY() * SQUARESIZE), SQUARESIZE, SQUARESIZE);
//		g2D.setColor(Color.RED);
//		Position pos2 = getPosOnCamera(this);
//		g2D.fillRect((int) (pos2.getX() * SQUARESIZE), (int) (pos2.getY() * SQUARESIZE), SQUARESIZE, SQUARESIZE);
		
		g2D.setTransform(original);
	}
	
	public Position getPosOnCamera(Position pos) {
		return new Position(pos.getX() - this.getX(), pos.getY() - this.getY(), pos.getRotation(), false);
	}
	
	public Point getCoordinatesOnCamera(int row, int col) {
		return new Point( (int) ((col - this.getX()) * SQUARESIZE), (int) ((row - this.getY()) * SQUARESIZE));
	}
	
	public Point getCoordinatesOnCamera(Position pos) {
		return new Point( (int) ((pos.getX() - this.getX()) * SQUARESIZE), (int) ((pos.getY() - this.getY()) * SQUARESIZE));
	}
	
	public Point getCoordinatesOnCamera(double x, double y) {
		return new Point( (int) ((x - this.getX()) * SQUARESIZE), (int) ((y - this.getY()) * SQUARESIZE));
	}
	
	public void zoomOut(double amount) {
		zoom -= amount;
	}
	
	public void zoomIn(double amount) {
		zoom += amount;
	}
	
	public void rotate(double angleInDegrees) {
		setRotation(getRotation() + angleInDegrees);
	}
	
	//in this case, x and y are screen coordinates
	public Tile getTileOnScreenAt(int x, int y) {
		Tile[][] layer = SWRD.game.getMap().getTopLayer();
		System.out.println( (double) renderOffsetX / SQUARESIZE);
//		System.out.println("Camera X: " + this.x);
//		System.out.println("tile on screen: " + ((x + renderOffsetX) / SQUARESIZE) + " " + ((x + renderOffsetX) / SQUARESIZE));
		
		int row = (int) (((y + renderOffsetY) / SQUARESIZE) + this.getY());
		int col = (int) (((x + renderOffsetX) / SQUARESIZE) + this.getX());
		
		try {
			System.out.println("Location: Row: " + row + " Col: " + col);
			return layer[row][col];
		} catch (IndexOutOfBoundsException e) {
//			System.out.println("Selected tile is out of bounds");
		} catch (NullPointerException e1) {
//			System.out.println("Clicked on null tile");
		}
		return Tile.blankTile();
	}
	
	public double[] getScreenLocationInGame(double x, double y) {
		double[] coordinates = new double[2];
		coordinates[0] = ((x + renderOffsetX) / SQUARESIZE) + this.getX();
		coordinates[1] = ((y + renderOffsetY) / SQUARESIZE) + this.getY();
		return coordinates;
	}
	
	public void focusOn(double x, double y) {
//		System.out.println("CAMERA X: " + getX());
//		System.out.println("FOCUS X: " + x);
//		System.out.println(x - (areaSize / 2) + "\n");
		setX( x - areaSize / 2);
		setY( y - areaSize / 2);
	}
	
	
	
	
	
	
}
