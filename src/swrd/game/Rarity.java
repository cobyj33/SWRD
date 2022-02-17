package swrd.game;

import java.awt.Color;

public enum Rarity {
	COMMON("Common", Color.WHITE), UNCOMMON("Uncommon", Color.GREEN), RARE("Rare", Color.BLUE), EXOTIC("Exotic", Color.YELLOW), UNTOUCHED("Untouched", Color.RED);
	
	private String name;
	private Color textColor;
	
	Rarity(String name, Color textColor) {
		this.name = name;
		this.textColor = textColor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
	
	
}
