package swrd;

import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.image.BufferedImage;

public class Util {
	
	public static double getAngle(double x1, double y1, double x2, double y2) {
		double addedAngle = 0;
		try {
			addedAngle = Math.atan2(Math.abs(x2 - x1), Math.abs(y2 - y1));
		} catch (ArithmeticException e) {
			System.out.println("Divide by zero");
		}
		double angle = 0;
		
		if (x2 > x1 && y2 < y1) { //q1
			angle = Math.PI / 2 - addedAngle;
		} else if (x2 < x1 && y2 < y1) { //q2
			angle = addedAngle + Math.PI / 2;
		} else if (x2 < x1 && y2 > y1) { //q3
			angle = Math.PI * 3 / 2 - addedAngle;
		} else if (x2 > x1 && y2 > y1) { //q4
			angle = addedAngle + Math.PI * 3 / 2;
		} else {
			angle = addedAngle;
		}
		
		return Math.toDegrees(angle);
	}
	
	public static double getDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt( Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)  );
	}
	
	public static boolean inRange(double test, double least, double most) {
		if (test >= least && test <= most) {
			return true;
		} return false;
	}
	
	public static java.awt.GridBagConstraints getDefaultConstraints() {
		java.awt.GridBagConstraints constraints = new java.awt.GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new java.awt.Insets(1, 1, 1, 1);
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.gridx = 0;
		constraints.gridy = 0;
		return constraints;
	}
	
	public static BufferedImage rotateImage(BufferedImage bimg, Double angle) {
		angle = -angle;
	    double sin = Math.abs(Math.sin(Math.toRadians(angle))),
	           cos = Math.abs(Math.cos(Math.toRadians(angle)));
	    int w = bimg.getWidth();
	    int h = bimg.getHeight();
	    int neww = (int) Math.floor(w*cos + h*sin),
	        newh = (int) Math.floor(h*cos + w*sin);
	    BufferedImage rotated = new BufferedImage(neww, newh, bimg.getType());
	    Graphics2D graphic = rotated.createGraphics();
	    graphic.translate((neww-w)/2, (newh-h)/2);
	    graphic.rotate(Math.toRadians(angle), w/2, h/2);
	    graphic.drawRenderedImage(bimg, null);
	    graphic.dispose();
	    return rotated;
	}
	
	
}
