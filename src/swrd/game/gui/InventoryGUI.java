package swrd.game.gui;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import swrd.Main;
import swrd.Util;
import swrd.game.Rarity;
import swrd.game.collectibles.Item;
import swrd.game.collectibles.weapons.Sword;
import swrd.game.entities.Inventory;

public class InventoryGUI extends GUI {
	Inventory inventory;
	JPanel weaponView;
	JPanel itemView;
	JPanel popUpViewer;
	JPanel statView;
	JPanel taskView;
	
	InventoryGUI(Inventory inventory) {
		this.inventory = inventory;
		setLayout(new GridBagLayout());
		setOpaque(false);
		GridBagConstraints constraints = Util.getDefaultConstraints();
		
		
		statView = new JPanel();
		add(statView, constraints);
		
		constraints.gridx = 1;
		popUpViewer = new JPanel();
		popUpViewer.setLayout(new BorderLayout());
		popUpViewer.setOpaque(false);
		add(popUpViewer, constraints);
		
		constraints.gridx = 2;
		taskView = new JPanel();
		add(taskView, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		weaponView = new ItemView(inventory.getWeapons());
		add(weaponView, constraints);
		
		constraints.gridx = 1;
		JPanel divider = new JPanel();
		add(divider, constraints);
		
		itemView = new ItemView(inventory.getNonWeapons());
		constraints.gridx = 2;
		add(itemView, constraints);
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		java.awt.Composite originalComposite = g2D.getComposite();
		g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g2D.setPaint(Color.BLACK);
		g2D.fillRect(0, 0, getWidth(), getHeight());
		g2D.setComposite(originalComposite);
		super.paintComponent(g2D);
		setSize(Main.layeredPane.getWidth() * 2 / 3, Main.layeredPane.getHeight() * 2 / 3);
		setLocation(Main.layeredPane.getWidth() / 2 - this.getWidth() / 2, Main.layeredPane.getHeight() / 2 - this.getHeight() / 2);
	}

	
	class ItemView extends JPanel {
		List<JLabel> itemLabels;
		List<Item> items;
		int defaultRows = 5;
		int cols = 5;
		
		ItemView(List<Item> items) {
			this.items = items;
			setOpaque(false);
			int rows = defaultRows + (items.size() / 5 + 1) / defaultRows;
			System.out.println(rows);
			setLayout(new GridLayout(rows, cols));
			for (int i = 0; i < items.size(); i++) {
				JLabel itemView = GUIResources.createImageLabel(items.get(i).getImage());
				itemView.addMouseListener(new LabelActions(this, i));
				add(itemView);
			}
			
			for (int i = 0; i < rows * cols - items.size(); i++) {
				add(Box.createRigidArea(new Dimension(1, 1)));
			}
		}
	}
	
	class InfoView extends JPanel {
		Item displayItem;
		JLabel itemName, itemRarity, itemDesc, itemImage;
		
		InfoView(Item item) {
			displayItem = item;
			setLayout(new GridBagLayout());
			setOpaque(false);
			GridBagConstraints constraints = Util.getDefaultConstraints();
			itemName = GUIResources.createLabel(item.getName(), 1);
			itemDesc = GUIResources.createLabel(item.getDesc(), 1);
			itemRarity = GUIResources.createLabel(item.getRarity().toString(), 1);
			itemRarity.setForeground(item.getRarity().getTextColor());
			itemImage = GUIResources.createImageLabel(item.getImage());
			
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
			add(itemDesc, constraints);
			revalidate(); repaint();
		}
	}
	
	class LabelActions extends MouseAdapter {
		ItemView window;
		int itemIndex;
		LabelActions(ItemView window, int itemIndex) { this.window = window; this.itemIndex = itemIndex; } 
		
		public void mouseEntered(MouseEvent e) {
			popUpViewer.add(new InfoView(window.items.get(itemIndex)), BorderLayout.CENTER);
			popUpViewer.revalidate(); popUpViewer.repaint();
		}
		
		public void mouseExited(MouseEvent e) {
			popUpViewer.removeAll();
			popUpViewer.revalidate(); popUpViewer.repaint();
		}
		
		public void mouseReleased(MouseEvent e) {
			
		}
		
		public void mousePressed(MouseEvent e) {
			
		}
	}
	
	class StatView extends JPanel {
		
	}
	
	
}
