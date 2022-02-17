package swrd.screenManager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import swrd.Resources;

public class MenuScreen extends JPanel implements ActionListener {
	JLabel title;
	JButton toGame;
	FallingStars starAnimation;
	
	
	MenuScreen(ScreenManager manager) {
		setPreferredSize(manager.displayPanel.getSize());
		setBackground(Color.BLACK);
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		
		title = new JLabel("S   W   R   D");
		title.setFont(Resources.getFont(Resources.FontEnum.DEFAULTFONT).deriveFont(20f));
		title.setForeground(Color.WHITE);
		title.setHorizontalAlignment(JLabel.CENTER);
		constraints.gridwidth = 4;
		add(title, constraints);
		
		constraints.gridy = 1;
		toGame = new GUIButton("Enter Game");
		toGame.addActionListener(l -> manager.switchScreen(ScreenManager.GAMESCREEN));
		add(toGame, constraints);
		
		starAnimation = new FallingStars(this);
		starAnimation.starColor = Color.green;
		starAnimation.numOfStars = 50;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		
		starAnimation.animate(g2D);
	}
	
	class GUIButton extends JButton {
		
		GUIButton(String text) {
			setFont(Resources.getFont(Resources.FontEnum.DEFAULTFONT).deriveFont(12f));
			setText(text);
			setBackground(Color.BLACK);
			setForeground(Color.WHITE);
			setFocusable(false);
			setBorder(BorderFactory.createEmptyBorder());
			setContentAreaFilled(false);
			addActionListener(MenuScreen.this);
		}
	}


	
}
