package swrd.game.levels.maps;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import swrd.Resources;

/* notes
 * 
 * All layer widths must be the same
 * 
 */
public class GameMap {
	BufferedImage miniMap;
	private List<Tile[][]> layers;
	String name;
	private List<TileData> collectibleLocations;
	
	public GameMap(Tile[][]... layers) {
		this.layers = new ArrayList<>();
		for (Tile[][] layer : layers) {
			this.addLayer(layer);
		}
		findCollectibleLocations();
		createMiniMap();
	}
	
	private void findCollectibleLocations() {
		
		
	}
	
	public BufferedImage getMiniMap() {
		return miniMap;
	}
	
	private void createMiniMap() {
		miniMap = new BufferedImage(getMapWidth() * Resources.SPRITESIZE, getMapHeight() * Resources.SPRITESIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D mapGraphics = miniMap.createGraphics();
		for (int l = 0; l < layers.size(); l++) {
			for (int row = 0; row < layers.get(l).length; row++) {
				for (int col = 0; col < layers.get(l)[row].length; col++) {
					mapGraphics.drawImage(getHighestTileAt(row, col).image, row * Resources.SPRITESIZE, col * Resources.SPRITESIZE, Resources.SPRITESIZE, Resources.SPRITESIZE, null);
				}
			}
		}
		mapGraphics.dispose();
	}
	
	public int getMapWidth() {
		return layers.get(0)[0].length;
	}
	
	public int getMapHeight() {
		return layers.get(0).length;
	}
	
	public List<Tile> listOfTiles() {
		return layers.stream().flatMap(Arrays::stream).flatMap(Arrays::stream).collect(Collectors.toList());
	}
	
	private Tile getHighestTileAt(int row, int col) {
		for (int l = layers.size() - 1; l >= 0; l--) {
			if (layers.get(l)[row][col] != null)
				return layers.get(l)[row][col];
		}
		return Tile.blankTile();
	}
	
	public void addLayer(Tile[][] layer) {
		layers.add(layer);
	}
	
	public Tile[][] getLayer(int index) {
		return layers.get(index);
	}
	
	public Tile[][] getTopLayer() {
		return layers.get( layers.size() - 1 );
	}
	
	public int numOfLayers() {
		return layers.size();
	}
	
	public List<Tile[][]> getLayers() {
		return layers;
	}
	
//	public List<TileData> getTilesAt(int row, int col) {
//		
//	}
	
	public List<TileData> getIntersectingTiles(Rectangle2D.Double hitbox) {
		List<TileData> tiles = new ArrayList<>();
		for (int l = 0; l < layers.size(); l++) {
			Tile[][] currentLayer = layers.get(l);
			
			for (int row = (int)hitbox.y; row <= (int) (hitbox.y + hitbox.height); row++) {
				for (int col = (int)hitbox.x; col <= (int) (hitbox.x + hitbox.width); col++) {
					if (row < 0 || col < 0 || row >= currentLayer.length || col >= currentLayer[row].length || currentLayer[row][col] == null)
						continue;
					
					tiles.add(new TileData(row, col, l, currentLayer[row][col]));
				}
			}
			
		}
		return tiles;
	}
	
	public List<TileData> getTilesAround(int row, int col) {
		List<TileData> tilesAround = new ArrayList<>();
		
		for (int l = 0; l < layers.size(); l++) {
			Tile[][] layer = layers.get(l);
			
			for (int r = row - 1; r <= row + 1; r++) {
				for (int c = col - 1; c <= col + 1; c++) {
					
					try {
						if (layer[r][c] != null)
							tilesAround.add(new TileData(r, c, l, layer[r][c]));
					} catch (ArrayIndexOutOfBoundsException e) {
						continue;
					}
					
				}
			}
		}
		
		
		return tilesAround;
	}
	
	
}
