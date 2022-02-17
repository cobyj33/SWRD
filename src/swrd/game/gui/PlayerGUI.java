package swrd.game.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import swrd.Main;
import swrd.Resources;
import swrd.game.SWRD;
import swrd.game.collectibles.Item;

public class PlayerGUI extends GUI {
	static Rectangle primaryWeaponDisplay;
	static Rectangle secondaryWeaponDisplay;
	static Rectangle healthDisplay;
	static Rectangle staminaDisplay;
	
	public PlayerGUI() {
		setFocusable(false);
		setOpaque(false);
//		setBackground(Color.MAGENTA);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
//		System.out.println("PAINTING PLAYER GUI");
		if (getWidth() != Main.layeredPane.getWidth() || getHeight() != Main.layeredPane.getHeight()) {
			System.out.println("SCALLING PLAYERGUI SIZE");
			setSize(Main.layeredPane.getSize());
			revalidate();
			repaint();
			Main.layeredPane.revalidate();
			Main.layeredPane.repaint();
		}
		
		
		int SQUARESIZE = GUIManager.SQUARESIZE;
		g2D.setPaint(Color.RED);
		secondaryWeaponDisplay = new Rectangle(getWidth() - SQUARESIZE - 10, 10, SQUARESIZE, SQUARESIZE);
		
		
		Item secondary = SWRD.player.getInventory().getSecondary();
		if (secondary != null) {
			GUIManager.centerInBounds(secondaryWeaponDisplay, GUIManager.scaleImage(secondary.getImage(), secondaryWeaponDisplay), g2D);
		}
		
		primaryWeaponDisplay = new Rectangle(secondaryWeaponDisplay.x - secondaryWeaponDisplay.width - 10, secondaryWeaponDisplay.y, SQUARESIZE, SQUARESIZE);
		Item primary = SWRD.player.getInventory().getPrimary();
		if (primary != null) {
			GUIManager.centerInBounds(primaryWeaponDisplay, GUIManager.scaleImage(primary.getImage(), primaryWeaponDisplay), g2D);
		}
		
		healthDisplay = new Rectangle(10, 10, SQUARESIZE * 4, SQUARESIZE / 2);
		Rectangle healthBar = new Rectangle(healthDisplay.x, healthDisplay.y, (int)(healthDisplay.width * ( (double)SWRD.player.health / SWRD.player.maxHealth)), healthDisplay.height);
		g2D.setPaint(Color.RED);
		g2D.draw(healthDisplay);
		g2D.fill(healthBar);
		
		
		staminaDisplay = new Rectangle(healthDisplay.x, healthDisplay.y + healthDisplay.height + 10, SQUARESIZE * 2, SQUARESIZE / 4);
		Rectangle staminaBar = new Rectangle(staminaDisplay.x, staminaDisplay.y, (int)(staminaDisplay.width * ( (double)SWRD.player.stamina / SWRD.player.maxStamina)), staminaDisplay.height);
		g2D.setPaint(Color.GREEN);
		g2D.draw(staminaDisplay);
		g2D.fill(staminaBar);
		
		BufferedImage miniMap = SWRD.game.getMap().getMiniMap();
		Rectangle miniMapBounds = new Rectangle(primaryWeaponDisplay.x - 10 - SQUARESIZE, 10, SQUARESIZE * 1, SQUARESIZE * 1);
		g2D.drawImage(miniMap, miniMapBounds.x, miniMapBounds.y, miniMapBounds.width, miniMapBounds.height, null);
		g2D.setPaint(Color.BLACK);
		g2D.draw(miniMapBounds);
	}
	
	
}
