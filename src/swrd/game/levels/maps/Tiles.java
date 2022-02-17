package swrd.game.levels.maps;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import swrd.Resources;
import swrd.game.Rarity;
import swrd.game.collectibles.Item;
import swrd.game.collectibles.weapons.Sword;

public class Tiles {
	public static Map<TileEnum, Tile> tiles;
	
	public static enum TileEnum {
		FLOOR, CHEST, WALL;
	}
	
	
	public static void init() {
		tiles = new EnumMap<>(TileEnum.class);
		Tile floorTile = new Tile("Floor", Resources.getImage(Resources.ImageEnum.FLOOR), Color.lightGray);
		tiles.put(TileEnum.FLOOR, floorTile);
		
		Tile chestTile = new Tile("Chest", null, new java.awt.Color(150, 75, 0));
		BufferedImage yellow = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = yellow.createGraphics();
		g.setPaint(Color.YELLOW);
		g.fillRect(0, 0, yellow.getWidth(), yellow.getHeight());
//		chestTile.setCollectible(Item.create("Gold", "Pretty but Worthless", yellow));
		chestTile.setCollectible(Sword.create(Rarity.RARE));
		chestTile.itemHidden = true;
		
		
		tiles.put(TileEnum.CHEST, chestTile);
		
		Tile wall = new Tile("Wall", Resources.getImage(Resources.ImageEnum.WALL), java.awt.Color.LIGHT_GRAY);
		wall.setWall(true);
		tiles.put(TileEnum.WALL, wall);
	}
	
	public static Tile getTile(TileEnum choice) {
		return tiles.get(choice);
	}
	
	public static Tile createTile(TileEnum choice) {
		Tile toCopy = tiles.get(choice);
		Tile tile = new Tile(toCopy.name, toCopy.image, toCopy.color);
		tile.setCollectible(toCopy.getCollectible());
		tile.setItemHidden( toCopy.isItemHidden() );
		return tile;
	}
}
