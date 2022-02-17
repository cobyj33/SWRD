package swrd.game.levels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import swrd.game.levels.maps.GameMap;
import swrd.game.levels.maps.Tile;
import swrd.game.levels.maps.Tiles;

public class LevelLoader {
	public static int FLOORMAP; 
	public static HashMap<Integer, GameMap> gameMaps;
	
	public static void init() {
		gameMaps = new HashMap<>();
		
		Tile[][] floorLayer = new Tile[20][20];
		for (int row = 0; row < floorLayer.length; row++) {
			for (int col = 0; col < floorLayer[row].length; col++) {
				floorLayer[row][col] = Tiles.getTile(Tiles.TileEnum.FLOOR);
			}
		}
		
//		GameMap floorMap = new GameMap();
//		floorMap.addLayer(floorLayer);
		
		Tile[][] onTop = new Tile[20][20];
		onTop[10][10] = Tiles.getTile(Tiles.TileEnum.CHEST);
		
		for (int row = 0; row < onTop.length; row++) {
			for (int col = 0; col < onTop[row].length; col++) {
				if (row == 0 || col == 0 || row == onTop.length - 1 || col == onTop[row].length - 1) {
					onTop[row][col] = Tiles.getTile(Tiles.TileEnum.WALL);
				}
			}
		}
		
//		floorMap.addLayer(onTop);
		GameMap floorMap = new GameMap(floorLayer, onTop);
		gameMaps.put(FLOORMAP, floorMap);
	}
	
	public static void loadLevel() {
		
	}
	
	public static GameMap getMap(int choice) {
		return gameMaps.get(choice);
	}
	
}
