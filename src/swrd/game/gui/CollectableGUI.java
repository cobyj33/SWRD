package swrd.game.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import swrd.Main;
import swrd.Util;
import swrd.game.SWRD;
import swrd.game.collectibles.Item;
import swrd.game.levels.maps.Tile;
import swrd.game.logic.Camera;

public class CollectableGUI extends GUI {
	int tileRow;
	int tileCol;
	Item collectible;
	
	//normal
	JLabel itemImage;
	JLabel itemName;
	JLabel keyPrompt;
	
	//detailed (normal + detailed)
	JLabel itemRarity;
	JScrollPane descriptionScrollPane;
	JLabel itemDescription;
	boolean expanded;
	boolean hidden;
	
	Mouse mouse;
	
	
	CollectableGUI(int row, int col, Tile itemTile) {
		this.collectible = itemTile.getCollectible();
		this.tileRow = row;
		this.tileCol = col;
		setOpaque(false);
		hidden = itemTile.isItemHidden();
//		System.out.println(hidden);
		hidden = false;
		
		itemImage = GUIResources.createImageLabel(collectible.getImage());
		itemName = GUIResources.createLabel(collectible.getName(), 1);
//		itemName.setFont(getFont().deriveFont(8f));
//		itemName.setOpaque(true);
//		itemName.setBackground(Color.MAGENTA);
		
		keyPrompt = GUIResources.createLabel("E", 1);
//		keyPrompt.setFont(getFont().deriveFont(8f));
//		keyPrompt.setOpaque(true);
		
		itemRarity = GUIResources.createLabel(collectible.getRarity().getName(), 1);
		itemRarity.setForeground(collectible.getRarity().getTextColor());
		itemRarity.setFocusable(false);
		
//		itemDescription = GUIResources.createTextArea(collectible.getDesc());
//		itemDescription.setEditable(false);
//		itemDescription.setEnabled(false);
//		itemDescription.setFocusable(false);
//		itemDescription.setRequestFocusEnabled(false);
//		itemDescription.addMouseListener(mouse);
//		itemDescription.addMouseMotionListener(mouse);
		
		itemDescription = GUIResources.createLabel(collectible.getDesc(), 1);
		itemDescription.setBackground(Color.BLACK);
		itemDescription.setOpaque(true);
//		itemDescription.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		
		mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
		setLayout(new GridBagLayout());
		
		if (hidden) { showHidden(); } else { showDefault(); }
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		java.awt.Composite originalComposite = g2D.getComposite();
		g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g2D.setPaint(Color.BLACK);
		g2D.fillRect(0, 0, getWidth(), getHeight());
		g2D.setComposite(originalComposite);
		super.paintComponent(g2D);
//		System.out.println("Painting collectible GUI");
		
		if (!expanded || hidden) {
			setSize(new Dimension(Camera.SQUARESIZE, Camera.SQUARESIZE));
			setLocation(SWRD.game.getCamera().getCoordinatesOnCamera(tileRow, tileCol));
		} else {
			setSize(new Dimension(Camera.SQUARESIZE * 2, Camera.SQUARESIZE * 2));
			setLocation(SWRD.game.getCamera().getCoordinatesOnCamera(tileRow - 0.5, tileCol - 0.5));
		}
		
		swrd.game.entities.Player player = SWRD.player;
		if (Util.getDistance(tileCol, tileRow, player.getX(), player.getY()) > 3) {
			GUIManager.instance.removeGUI(this);
		}
		Main.layeredPane.revalidate();
		Main.layeredPane.repaint();
	}
	
	public void showHidden() {
		this.removeAll();
		revalidate();
		hidden = true;
		GridBagConstraints constraints = Util.getDefaultConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		add(keyPrompt, constraints);
		revalidate(); repaint();
	}
	
	public void showDetailed() {
		expanded = true;
		this.removeAll();
		revalidate();
		GridBagConstraints constraints = Util.getDefaultConstraints();
		
		constraints.insets.set(5, 5, 5, 5);
		constraints.gridwidth = 2;
		constraints.gridheight = 2;
		add(itemImage, constraints);
		
		constraints.gridx = 2;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		add(itemName, constraints);
		
		constraints.gridy = 1;
		add(itemRarity, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 4;
		constraints.gridheight = 2;
		add(itemDescription, constraints);
		revalidate(); repaint();
	}
	
	public void showDefault() {
		expanded = false;
		this.removeAll();
		revalidate();
		setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		GridBagConstraints constraints = Util.getDefaultConstraints();
		constraints.gridwidth = 3;
		constraints.gridheight = 3;
		add(itemImage, constraints);
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weighty = 1;
		constraints.weightx = 1;
		constraints.gridy = 3;
		constraints.gridheight = 1;
		constraints.gridwidth = 3;
		constraints.anchor = GridBagConstraints.CENTER;
		add(itemName, constraints);
		System.out.println("Item name: " + itemName.getText());
//		
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.gridx = 1;
		add(keyPrompt, constraints);
		revalidate(); repaint();
	}
	
	public Item getCollectible() { 
		return collectible;
	}

	@Override
	public int hashCode() {
		return Objects.hash(collectible, tileCol, tileRow);
	}
	
	public Item getItem() {
		return collectible;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CollectableGUI other = (CollectableGUI) obj;
		return Objects.equals(collectible, other.collectible) && tileCol == other.tileCol && tileRow == other.tileRow;
	}
	
	class Mouse extends MouseAdapter {
		Mouse() {}
		
		public void mouseEntered(MouseEvent e) {
			if (!hidden)
				showDetailed();
		}
		
		public void mouseExited(MouseEvent e) {
			if (!CollectableGUI.this.contains(e.getPoint())) {
				if (hidden) { showHidden(); } else { showDefault(); }
			}
		}
		
		public void mouseMoved(MouseEvent e) {
			mouseExited(e);
		}
	}
	
	
}
