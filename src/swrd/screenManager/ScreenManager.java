package swrd.screenManager;

import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import swrd.game.SWRD;

public class ScreenManager {
	JPanel displayPanel;
	public static final int MENUSCREEN = 0,
			GAMESCREEN = 1;
	HashMap<Integer, JPanel> screens;
	int currentScreen;
	
	public ScreenManager(JPanel displayPanel) {
		screens = new HashMap<>();
		this.displayPanel = displayPanel;
		
//		Main.gameThread.execute(() -> new SWRD());
		new SWRD();
		
		screens.put(MENUSCREEN, new MenuScreen(this));
		screens.put(GAMESCREEN, new GameScreen(this));
		
		//starting screen
		currentScreen = MENUSCREEN;
		displayPanel.add(screens.get(currentScreen));
		displayPanel.revalidate(); displayPanel.repaint();
		
//		displayPanel.setFocusable(true);
//		displayPanel.requestFocus();
//		displayPanel.addKeyListener(new KeyAdapter() {
//			public void keyPressed(KeyEvent e) {
//				int keyCode = e.getKeyCode();
//				switch (keyCode) {
//				case KeyEvent.VK_0: switchScreen(MENUSCREEN); break;
//				case KeyEvent.VK_1: switchScreen(GAMESCREEN); break;
//				}
//			}
//		});
		
		
		System.out.println(screens);
	}
	
	public void switchScreen(int screenID) {
		SwingUtilities.invokeLater( () -> {
			
			if (screens.containsKey(screenID)) {
			
			System.out.println("Switching screens");
			displayPanel.remove(screens.get(currentScreen));
			displayPanel.add(screens.get(screenID));
			displayPanel.revalidate(); displayPanel.repaint();
			currentScreen = screenID;
			
			
		} else {
			System.out.println("[ScreenManager]: Invalid Screen");
		}
			
		});
		
	}
	
	
	
}
