package swrd.game.levels.maps;

public class TileData {
	int row;
	int col;
	int layer;
	Tile tile;
	
	TileData(int row, int col, int layer, Tile tile) {
		super();
		this.row = row;
		this.col = col;
		this.layer = layer;
		this.tile = tile;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getLayer() {
		return layer;
	}

	public Tile getTile() {
		return tile;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + layer;
		result = prime * result + row;
		result = prime * result + ((tile == null) ? 0 : tile.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TileData other = (TileData) obj;
		if (col != other.col)
			return false;
		if (layer != other.layer)
			return false;
		if (row != other.row)
			return false;
		if (tile == null) {
			if (other.tile != null)
				return false;
		} else if (!tile.equals(other.tile))
			return false;
		return true;
	}
	
	
	
	
	
}
