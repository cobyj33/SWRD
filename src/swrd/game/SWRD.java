package swrd.game;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;

import swrd.Main;
import swrd.game.entities.Entity;
import swrd.game.entities.Player;
import swrd.game.entities.enemies.Enemy;
import swrd.game.gui.GUIManager;
import swrd.game.gui.InventoryGUI;
import swrd.game.levels.LevelLoader;
import swrd.game.levels.maps.GameMap;
import swrd.game.levels.maps.Tile;
import swrd.game.logic.Camera;
import swrd.game.logic.KeyTracker;
import swrd.game.logic.Position;
import swrd.interfaces.Colliding;

public class SWRD {
	KeyTracker tracker;
	public static SWRD game;
	static boolean initialized;
	private GameMap gameMap;
	public static Player player;
	private Camera camera;
	private GUIManager guiManager;
	public static final int DELAY = 17;
	
	public int mouseX;
	public int mouseY;
	
	
	Tile selectedTile;
	
	public List<Position> gameObjects;
//	List<KeyBinding> keyBindings;
	
	public SWRD() {
		if (!initialized) {
			initialized = true;
			game = this;
			System.out.println("Game initialized");
			gameObjects = new ArrayList<>();
			tracker = new KeyTracker();
			camera = new Camera(0, 0, 0);
			gameMap = LevelLoader.getMap(LevelLoader.FLOORMAP);
			player = new Player(10, 10, 0);
			camera.focusOn(player.getX(), player.getY());
			guiManager = new GUIManager(Main.layeredPane);
		
			tracker.addBinding(KeyEvent.VK_W, null, () -> movePlayer(0, -player.getMovementSpeed()), null);
			tracker.addBinding(KeyEvent.VK_A, null, () -> { movePlayer(-player.getMovementSpeed(), 0); player.setLookingDirection(Position.LEFT); }, null);
			tracker.addBinding(KeyEvent.VK_S, null, () -> movePlayer(0, player.getMovementSpeed()), null);
			tracker.addBinding(KeyEvent.VK_D, null, () -> { movePlayer(player.getMovementSpeed(), 0); player.setLookingDirection(Position.RIGHT); } , null);
			tracker.addBinding(KeyEvent.VK_MINUS, null, () -> camera.zoomIn(0.05), null);
			tracker.addBinding(KeyEvent.VK_O, null, () -> camera.zoomOut(0.05), null);
			
			//walking and sprinting
			tracker.addBinding(KeyEvent.VK_SHIFT, () -> player.sprint(), null, () -> player.walk());
			tracker.addBinding(KeyEvent.VK_SPACE, () -> player.jump(), null, null);
			tracker.addBinding(KeyEvent.VK_CONTROL, () -> player.crouch(), null, null);
			tracker.addBinding(KeyEvent.VK_R, () -> player.roll(), null, null);
			tracker.addBinding(KeyTracker.LEFT_CLICK, () -> player.attack(), null, null);
			tracker.addBinding(KeyEvent.VK_E, () -> player.interact(), null, null);
			
			tracker.addBinding(KeyEvent.VK_I, () -> guiManager.requestInventoryGUI(player.getInventory()), null, () -> guiManager.removeInventoryGUI());
			tracker.addBinding(KeyEvent.VK_TAB, () -> guiManager.requestInventoryGUI(player.getInventory()), null, () -> guiManager.removeInventoryGUI());
			new Enemy(0, 0, 0);
			new Enemy(12, 12, 0);
			
//			
//			tracker.addBinding(KeyEvent.VK_Q, null, () -> camera.rotate(1), null);
//			
//			tracker.addBinding(KeyEvent.VK_E, null, () -> camera.rotate(-1), null);
			
//			Main.scheduler.schedule(() -> {
//				tracker.addAction(() -> System.out.println("pressed M"), KeyEvent.VK_M, KeyTracker.ONPRESS);
//			}, 5, java.util.concurrent.TimeUnit.SECONDS);
			
		}
	}
	
	public void iterateGame() {
		tracker.processActions();
		for (int p = 0; p < gameObjects.size(); p++) {
			Position pos = gameObjects.get(p);
			pos.tick();
			if (pos instanceof Colliding) {
				( (Colliding) pos).checkCollisions();
			}
		}
		
		camera.focusOn(player.getX(), player.getY());
	}
	
	public void keyPressed(KeyEvent e) {
		tracker.pressKey(e.getKeyCode());
	}
	
	public void keyReleased(KeyEvent e) {
		tracker.releaseKey(e.getKeyCode());
	}
	
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			tracker.pressKey(KeyTracker.LEFT_CLICK);
		} else if (SwingUtilities.isRightMouseButton(e)) {
			tracker.pressKey(KeyTracker.RIGHT_CLICK);
		}
		
		Tile tile = camera.getTileOnScreenAt(e.getX(), e.getY());
		if (tile == null) { return; }
		
		if (tile == selectedTile) {
			selectedTile.color = Color.LIGHT_GRAY;
			selectedTile = null;
			return;
		}
		
		tile.color = Color.blue;
		if (selectedTile != null) {
			selectedTile.color = Color.LIGHT_GRAY;
		}
		selectedTile = tile;
	}
	
	public void mouseReleased(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			tracker.releaseKey(KeyTracker.LEFT_CLICK);
		} else if (SwingUtilities.isRightMouseButton(e)) {
			tracker.releaseKey(KeyTracker.RIGHT_CLICK);
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	public void movePlayer(double x, double y) {
		player.movePosition(x, y);
//		System.out.println(player.getX() + " " + player.getY());
//		System.out.println("Camera position: " + camera.getX() + " " + camera.getY());
		if (camera.state == Camera.FOLLOW_PLAYER) {
			camera.focusOn(player.getX(), player.getY());
//			camera.movePosition(x, y);
		}
	}
	
	public ArrayList<Entity> getEntities() {
		return new ArrayList<Entity>( gameObjects.stream().filter(x -> x instanceof Entity).map(x -> (Entity)x).collect(Collectors.toList()) );
	}

	public GameMap getMap() {
		return gameMap;
	}

	public Camera getCamera() {
		return camera;
	}


	public GUIManager getGuiManager() {
		return guiManager;
	}
	
	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}
	
	public double[] getMousePositionInGame() {
		return camera.getScreenLocationInGame(mouseX, mouseY);
	}

}
