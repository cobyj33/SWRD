package swrd.game.gui;

import java.awt.Graphics;
import java.util.Objects;

import javax.swing.JPanel;

public class GUI extends JPanel {
	private boolean displayed;
	private int gameX;
	private int gameY;
	private boolean boundedToGame;
	private boolean boundedToScreen;
	
	private boolean destroyed;
	
	
	GUI() { }
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	public void boundToGame(int x, int y) {
		boundedToGame = true;
		boundedToScreen = false;
	}
	
	public void boundToScreen(int x, int y) {
		boundedToScreen = true;
		boundedToGame = false;
	}

	public boolean isDisplayed() {
		return displayed;
	}

	public int getGameX() {
		return gameX;
	}

	public int getGameY() {
		return gameY;
	}

	public boolean isBoundedToGame() {
		return boundedToGame;
	}

	public boolean isBoundedToScreen() {
		return boundedToScreen;
	}

	public void setDisplayed(boolean displayed) {
		this.displayed = displayed;
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	@Override
	public int hashCode() {
		return Objects.hash(boundedToGame, boundedToScreen, displayed, gameX, gameY);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GUI other = (GUI) obj;
		return boundedToGame == other.boundedToGame && boundedToScreen == other.boundedToScreen
				&& displayed == other.displayed && gameX == other.gameX && gameY == other.gameY;
	}
	
	
}
