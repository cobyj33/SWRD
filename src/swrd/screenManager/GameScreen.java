package swrd.screenManager;

import java.awt.Color;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import swrd.Main;
import swrd.game.SWRD;
import swrd.game.logic.Camera;

public class GameScreen extends JPanel implements KeyListener, MouseListener, MouseMotionListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9055709983854844962L;
	public static GameScreen screen;
	private static boolean initialized;
	javax.swing.Timer gameloop;
	
	GameScreen(ScreenManager manager) {
		if (!initialized) {
		initialized = true;
		screen = this;
		setPreferredSize(manager.displayPanel.getSize());
		setBackground(Color.BLACK);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		gameloop = new javax.swing.Timer(SWRD.DELAY, l -> { 
			Main.gameThread.execute( () -> SWRD.game.iterateGame() );
			repaint();
			} );
		
		addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorAdded(AncestorEvent event) { requestFocus(); gameloop.restart(); }

			@Override
			public void ancestorRemoved(AncestorEvent event) { gameloop.stop(); }

			@Override
			public void ancestorMoved(AncestorEvent event) { }
			
		});
		
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		}
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//System.out.println("Rendering once more");
		Graphics2D g2D = (Graphics2D) g;
		SWRD.game.getCamera().render(this, g2D);
		SWRD.game.getGuiManager().render();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Main.gameThread.execute(() -> SWRD.game.mouseMoved(e));
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Main.gameThread.execute(() -> SWRD.game.mousePressed(e));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Main.gameThread.execute(() -> SWRD.game.mouseReleased(e));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
//		System.out.println("Key pressed: " + e.getKeyChar());
		Main.gameThread.execute(() -> SWRD.game.keyPressed(e));
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Main.gameThread.execute(() -> SWRD.game.keyReleased(e));
		
	}
	
	
}
