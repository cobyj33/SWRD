package swrd.game.levels.maps;

import java.awt.Color;
import java.awt.image.BufferedImage;

import swrd.game.collectibles.Item;

public class Tile {
	public Color color;
	public BufferedImage image;
	public String name;
	
	private Item collectible;
	boolean itemHidden;
	boolean isWall;
	Runnable onCollect;
	
	
	public Tile(String name, BufferedImage image, Color color) {
		this.color = color;
		this.image = image;
		this.name = name;
		itemHidden = false;
		onCollect = () -> {};
	}
	
	public static Tile blankTile() {
		return new Tile("null", null, Color.BLACK);
	}
	
	public void setEventOnCollect(Runnable event) {
		onCollect = event;
	}

	public Item getCollectible() {
		return collectible;
	}

	public void setCollectible(Item collectible) {
		this.collectible = collectible;
	}

	public boolean isItemHidden() {
		return itemHidden;
	}
	
	public void setItemHidden(boolean hidden) {
		itemHidden = hidden;
	}
	
	public boolean isWall() {
		return isWall;
	}
	
	public void setWall(boolean bool) {
		isWall = bool;
	}
	
	
}
