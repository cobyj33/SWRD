package swrd.game.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class GUIResources {
	private GUIResources() {}
	
	public static JButton createButton() {
		return new GUIButton();
	}
	
	public static JButton createButton(String text) {
		return new GUIButton(text);
	}
	
	public static JLabel createImageLabel(BufferedImage image) {
		return new ImageLabel(image);
	}
	
	public static JTextPane createTextArea() {
		return new GUITextArea();
	}
	
	public static JTextPane createTextArea(String text) {
		return new GUITextArea(text);
	}
	
	public static TextLabel createLabel() {
		return new TextLabel();
	}
	
	public static TextLabel createLabel(String text) {
		return new TextLabel(text);
	}
	
	public static TextLabel createLabel(String text, float minTextSize) {
		TextLabel label = new TextLabel(text);
		label.setPreferredFontSize(minTextSize);
		return label;
	}
	
	static class GUIButton extends JButton {
		
		GUIButton() {
			
		}
		
		GUIButton(String text) {
			setText(text);
		}
	}
	
	static class ImageLabel extends JLabel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		BufferedImage image;
		
		public ImageLabel(BufferedImage image) {
			setOpaque(false);
			this.image = image;
			if (image == null) { return; }
		}
		
		public void setImage(BufferedImage image) {
			this.image = image;
			repaint();
		}
		
//		protected void paintComponent(Graphics g) {
//			int centerX = getWidth() / 2;
//			int centerY = getHeight() / 2;
//			Graphics2D g2D = (Graphics2D) g;
//			int bounds = Math.min(image.getWidth(), image.getHeight());
//			if (image != null) {
//				if (getWidth() > getHeight()) {
//					g2D.drawImage(image, centerX - getHeight() / 2, 0, getHeight(), getHeight(), null);
//				} else {
//					g2D.drawImage(image, 0, centerY - getWidth() / 2, getWidth(), getWidth(), null);
//				}
//			}
//		}
		
		
		protected void paintComponent(Graphics g) {
			int centerX = getWidth() / 2;
			int centerY = getHeight() / 2;
			Graphics2D g2D = (Graphics2D) g;
			if (image != null) {
				if (getWidth() > getHeight()) {
					g2D.drawImage(image, centerX - getHeight() / 2, 0, getHeight(), getHeight(), null);
				} else {
					g2D.drawImage(image, 0, centerY - getWidth() / 2, getWidth(), getWidth(), null);
				}
			}
		}
		
	}
	
	static class GUITextArea extends JTextPane {
		GUITextArea() {
			construct();
		}
		
		GUITextArea(String text) {
			setText(text);
			construct();
		}
		
		private void construct() {
//			setBackground(Color.BLACK);
//			setFont(font);
//			setForeground(Color.WHITE);
//			setAlignmentX(JTextArea.CENTER_ALIGNMENT);
//			setFocusable(false);
////			textPane.setLineWrap(true);
////			textPane.setWrapStyleWord(true);
//			setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
			StyledDocument doc = getStyledDocument();;
			SimpleAttributeSet center = new SimpleAttributeSet();
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
			doc.setParagraphAttributes(0, doc.getLength(), center, false);
		}
	}

	static class TextLabel extends JLabel {
		String text;
		Font font;
		float preferredFontSize;
		private static final float minFontSize = 12;
		
		TextLabel() {
			construct();
		}
		
		TextLabel(String text) {
			System.out.println("TextLabel created");
			this.text = text;
			construct();
		}
		
		private void construct() {
			font = new Font("Times New Roman", Font.BOLD, 1);
			repaint();
			preferredFontSize = minFontSize;
			
			addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					font = font.deriveFont(1f);
					repaint();
				}
			});
		}
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2D = (Graphics2D) g;
			g2D.setFont(font);
			FontMetrics metrics = g2D.getFontMetrics();
			
			while (metrics.stringWidth(text) < getWidth() && metrics.getHeight() < getHeight() / 2) {
				g2D.setFont(g2D.getFont().deriveFont( g2D.getFont().getSize() + 1f ));
				metrics = g2D.getFontMetrics();
			}
			
			g2D.setFont(g2D.getFont().deriveFont( g2D.getFont().getSize() - 1f ));
			metrics = g2D.getFontMetrics();
			
			if (g2D.getFont().getSize() < preferredFontSize) {
				System.out.println("Getting lines");
				g2D.setFont(g2D.getFont().deriveFont(preferredFontSize));
				metrics = g2D.getFontMetrics();
				String[] lines = getPartitionedString(text, g2D);
				System.out.println("num of lines: " + lines.length);
				int lineHeight = getHeight() / 2 - lines.length * metrics.getHeight() / 4;
				
				for (String line : lines) {
					g2D.drawString(line, (getWidth() / 2) - (metrics.stringWidth(line) / 2), lineHeight);
					lineHeight += metrics.getHeight();
				}
				
				
			} else {
				g2D.drawString(text, (getWidth() / 2) - (metrics.stringWidth(text) / 2), getHeight() / 2 + (metrics.getHeight() / 4));
			}
			
		}
		
		private String[] getPartitionedString(String str, Graphics2D g2D) {
			FontMetrics metrics = g2D.getFontMetrics();
			int numOfPartitions = metrics.stringWidth(str) / getWidth();
			if (numOfPartitions == 0) return new String[] {str};
			
			String resultingString = new String(str);
			System.out.println("Num of partitions: " + numOfPartitions);
			
			int[] partitionAreas = new int[numOfPartitions];
			for (int i = 1; i <= numOfPartitions; i++) {
				int cut = resultingString.length() / (numOfPartitions + 1) * i;
				System.out.println("Cut at: " + cut);
				partitionAreas[i - 1] = findNearestSpace(str, cut);
				System.out.println("Partition at: " + partitionAreas[i - 1]);
			}
			
			for (int i = 0; i < partitionAreas.length; i++) {
				if (partitionAreas[i] == -1) continue;
				resultingString = resultingString.substring(0, partitionAreas[i]) + "\n" + resultingString.substring(partitionAreas[i] + 1, resultingString.length());
			}
			
			List<String> lineList = new ArrayList<>(Arrays.asList(resultingString.split("\n")));
			for (int i = 0; i < lineList.size(); i++) {
				String current = lineList.get(i);
				if (metrics.stringWidth(current) > g2D.getClipBounds().width) {
					lineList.set(i, current.substring(0, current.length() / 2) + "-");
					lineList.add(i + 1, current.substring(current.length() / 2 + 1, current.length()) );
					i++;
				}
			}
			return lineList.toArray(String[]::new);
		}
		
		private int findNearestSpace(String str, int start) {
			if (start >= str.length() || start < 0) return -1;
			int distanceFromEnd = Math.min(start, str.length() - start);
			for (int i = 0; i < distanceFromEnd; i++) {
				if (str.charAt(start + i) == ' ') {
//					System.out.println("Nearest space at: " + (start + i));
					return start + i;
				} else if (str.charAt(start - i) == ' ') {
//					System.out.println("Nearest space at: " + (start - i));
					return start - i;
				}
			}
			return -1;
		}

		public String getLabelText() {
			return text;
		}

		public float getPreferredFontSize() {
			return preferredFontSize;
		}

		public void setLabelText(String text) {
			this.text = text;
		}

		public void setPreferredFontSize(float preferredFontSize) {
			this.preferredFontSize = preferredFontSize;
			repaint();
		}
		
		public static enum Alignment {
			CENTER, LEFT, RIGHT, TOP, BOTTOM;
		}
	}
	
	public static void setTextLabelPreferredSize(JLabel label, float fontSize) {
		if (label instanceof TextLabel) {
			( (TextLabel) label).setPreferredFontSize(fontSize);
		}
	}
}
