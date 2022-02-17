package swrd;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import swrd.game.levels.LevelLoader;
import swrd.game.levels.maps.Tiles;
import swrd.screenManager.ScreenManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
	public static JFrame gameFrame;
	public static JPanel gameHolder;
	public static JLayeredPane layeredPane;
	public static int resolutionX = 800;
	public static int resolutionY = 800;
	
	public static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
	public static final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	public static final ExecutorService gameThread = Executors.newSingleThreadExecutor();
	
	public static void main(String[] args) {
		Resources.init();
		Tiles.init();
		LevelLoader.init();
		
		gameFrame = new JFrame();
		gameFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
		gameFrame.getContentPane().setBackground(Color.BLACK);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setSize(resolutionX, resolutionY);
		
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(resolutionX, resolutionY));
		layeredPane.setSize(layeredPane.getPreferredSize());
		
		
		gameHolder = new JPanel();
		gameHolder.setLayout(new BorderLayout());
		gameHolder.setPreferredSize(layeredPane.getSize());
		gameHolder.setSize(layeredPane.getSize());
		
		new ScreenManager(gameHolder);
		
		layeredPane.add(gameHolder);
		gameFrame.add(layeredPane);
		gameFrame.pack();
		gameFrame.setVisible(true);
	}
	
	public static void addToLayeredPane(JComponent component, Integer zIndex) {
		boolean inPane = Arrays.stream(layeredPane.getComponents()).anyMatch(c -> c.equals(component));
		if (!inPane) {
		layeredPane.add(component, zIndex);
		layeredPane.revalidate(); layeredPane.repaint();
		} else {
			System.out.println("already in pane");
		}
		
	}
	
	public static void removeFromLayeredPane(JComponent component) {
		layeredPane.remove(component);
		layeredPane.revalidate(); layeredPane.repaint();
	}
	
	public static void clearLayeredPane() {
		Arrays.stream(layeredPane.getComponents()).forEach(component -> {
			if (component.equals(gameHolder)) { return; }
			layeredPane.remove(component);
		});
		layeredPane.revalidate(); layeredPane.repaint();
	}
	
	public static void scheduleOnGameThread(Runnable runnable, int time) {
		scheduler.schedule(() -> gameThread.execute(runnable), time, TimeUnit.MILLISECONDS);
	}
	
	public static void LoopOnGameThread(Runnable runnable, int loopTime, int cancelTime) {
		Future<?> task = scheduler.scheduleAtFixedRate(() -> gameThread.execute(runnable), 0, loopTime, TimeUnit.MILLISECONDS);
		scheduler.schedule( () -> gameThread.execute(() -> task.cancel(false)), cancelTime, TimeUnit.MILLISECONDS);
	}

}
