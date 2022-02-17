package swrd.game.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import swrd.Main;
import swrd.Resources;
import swrd.game.SWRD;
import swrd.game.collectibles.Item;
import swrd.game.entities.Inventory;
import swrd.game.levels.maps.Tile;
import swrd.game.levels.maps.TileData;
import swrd.game.logic.Camera;
import swrd.interfaces.Renderable;
import swrd.screenManager.GameScreen;

public class GUIManager {
	Set<GUI> currentGUIs;
	static GUIManager instance;
	public static int SQUARESIZE = 10;
	private PlayerGUI playerGUI;
	Integer layer;
	JLayeredPane screen;
	
	public GUIManager(JLayeredPane screen) {
		instance = this;
		currentGUIs = new LinkedHashSet<>();
		playerGUI = new PlayerGUI();
		addGUI(playerGUI);
		layer = 1;
		this.screen = screen;
	}
	
	public void render() {
		int bounds = screen.getWidth() >= screen.getHeight() ? screen.getWidth() : screen.getHeight();
		SQUARESIZE = bounds / 10;
//		currentGUIS.stream().forEach(r -> r.render(screen, g2D));
		
//		System.out.println(Thread.currentThread().getName());
		Iterator<GUI> currentGUIsIterator = currentGUIs.iterator();
		while (currentGUIsIterator.hasNext()) {
			GUI current = currentGUIsIterator.next();
			current.repaint();
			if (!current.isDisplayed() && !current.isDestroyed()) {
				System.out.println("ADDED GUI TO LAYEREDPANE");
				current.setSize(1, 1);
				Main.addToLayeredPane(current, layer);
				current.setDisplayed(true);
				layer++;
			}
		}
		
	}
	
	public void addGUI(GUI gui) {
		System.out.println("ADDING GUI");
		currentGUIs.add(gui);
	}
	
	public void removeGUI(GUI gui) {
		SwingUtilities.invokeLater(() -> {
			currentGUIs.remove(gui);
			Main.removeFromLayeredPane(gui);
			System.out.println("REMOVED FROM LAYERED PANE");
			gui.setDisplayed(false);
			gui.setDestroyed(true);
		});
	}
	
	public JLayeredPane getLayeredPane() {
		return screen;
	}
	
	public static BufferedImage scaleImage(BufferedImage image, Rectangle bounds) {
		double scale = 1;
		double scaleX = bounds.getWidth() / image.getWidth();
		double scaleY = bounds.getHeight() / image.getHeight();
		scale = Math.min(scaleX, scaleY);
		
		Image newImage = image.getScaledInstance((int) (image.getWidth() * scale), (int) (image.getHeight() * scale), BufferedImage.TYPE_INT_RGB);
		BufferedImage finalImage = new BufferedImage(newImage.getWidth(null), newImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2D = finalImage.createGraphics();
		g2D.drawImage(newImage, 0, 0, finalImage.getWidth(), finalImage.getHeight(), null);
		return finalImage;
	}
	
	public static void centerInBounds(Rectangle bounds, BufferedImage image, Graphics2D g2D) {
		g2D.drawImage(image, bounds.x + (bounds.width / 2) - (image.getWidth() / 2), bounds.y + (bounds.height / 2) - (image.getHeight() / 2), image.getWidth(), image.getHeight(), null);
	}
	
	public void requestCollectableGUI(TileData tileData) {
		Iterator<GUI> currentGUIsIterator = currentGUIs.iterator();
		while (currentGUIsIterator.hasNext()) {
			GUI current = currentGUIsIterator.next();
			if (current instanceof CollectableGUI) {
				if (( (CollectableGUI) current).getItem().equals(tileData.getTile().getCollectible())) {
//					System.out.println("Found matching collectable GUI");
					return;
				}
			}
		}
		
		addGUI(new CollectableGUI(tileData.getRow(), tileData.getCol(), tileData.getTile()));
	}
	
	public void removeCollectableGUI(Item collectible) {
		Iterator<GUI> currentGUIsIterator = currentGUIs.iterator();
		while (currentGUIsIterator.hasNext()) {
			GUI current = currentGUIsIterator.next();
			if (current instanceof CollectableGUI) {
				if ( ((CollectableGUI) current).getCollectible().equals(collectible) ) {
					removeGUI(current);
					return;
				}
			}
		}
		
	}
	
	public void requestInventoryGUI(Inventory inventory) {
		System.out.println("INVENTORY REQUESTED");
		if (this.currentGUIs.stream().filter(gui -> gui instanceof InventoryGUI).count() == 0)
			this.addGUI(new InventoryGUI(inventory));
	}
	
	public void removeInventoryGUI() {
		SwingUtilities.invokeLater( () -> {
			Iterator<GUI> currentGUIsIterator = currentGUIs.iterator();
			while (currentGUIsIterator.hasNext()) {
				GUI current = currentGUIsIterator.next();
				if (current instanceof InventoryGUI) {
					removeGUI(current);
					return;
				}
			}
		});
	}
	
	
}
