package swrd.game.collectibles.weapons;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import swrd.Resources;
import swrd.Util;
import swrd.game.Rarity;
import swrd.game.SWRD;
import swrd.game.collectibles.Item;
import swrd.game.entities.Entity;
import swrd.game.logic.Collider;
import swrd.game.logic.Position;

public class Bow extends Item {
	BufferedImage arrowImage;
	int shotDistance;
	int damage;
	int attackTime;
	
	private Bow(int damage, int shotDistance, String name, String desc, BufferedImage image, BufferedImage arrowImage, Rarity rarity) {
		super(name, desc, image, rarity, ItemType.WEAPON);
		this.damage = damage;
		this.shotDistance = shotDistance;
		attackTime = 200;
		this.arrowImage = arrowImage;
	}
	
	public static Bow create(Rarity rarity) {
		switch (rarity) {
		case COMMON: return new Bow(5, 5, "Common Bow", "Only good for Rabbits", Resources.getImage(Resources.ImageEnum.COMMON_BOW), Resources.getImage(Resources.ImageEnum.COMMON_ARROW), Rarity.COMMON);
		case UNCOMMON: return new Bow(10, 5, "Uncommon Bow", "Might kill a deer or two", Resources.getImage(Resources.ImageEnum.UNCOMMON_BOW), Resources.getImage(Resources.ImageEnum.UNCOMMON_ARROW), Rarity.UNCOMMON);
		case RARE: return new Bow(20, 5, "Rare Bow", "Bears Beware", Resources.getImage(Resources.ImageEnum.RARE_BOW), Resources.getImage(Resources.ImageEnum.RARE_ARROW), Rarity.RARE);
		case EXOTIC: return new Bow(40, 5, "Exotic Bow", "", Resources.getImage(Resources.ImageEnum.EXOTIC_BOW), Resources.getImage(Resources.ImageEnum.EXOTIC_ARROW), Rarity.EXOTIC);
		case UNTOUCHED: return new Bow(120, 2, "Untouched Bow", "", Resources.getImage(Resources.ImageEnum.UNTOUCHED_BOW), Resources.getImage(Resources.ImageEnum.UNTOUCHED_ARROW), Rarity.UNTOUCHED);
		}
		
		return new Bow(5, 5, "Common Bow", "Only good for Rabbits", Resources.getImage(Resources.ImageEnum.COMMON_BOW), Resources.getImage(Resources.ImageEnum.COMMON_ARROW), Rarity.COMMON);
		
	}
	
	public void use() {
		Entity owner = getOwner();
		double startingX = owner.getX();
		double startingY = owner.getY();
		
		double[] mouseGameCoordinates = SWRD.game.getMousePositionInGame();
		System.out.println(mouseGameCoordinates[0] + " " + mouseGameCoordinates[1]);
		double shotAngle = Util.getAngle(startingX, startingY, mouseGameCoordinates[0], mouseGameCoordinates[1]);
//		double shotAngle = Util.getAngle(mouseGameCoordinates[0], mouseGameCoordinates[1], startingX, startingY);
		
		Collider arrow = new Collider(startingX, startingY, damage, attackTime, false, getOwner());
		arrow.setLookingDirection(Position.RIGHT);
		arrow.setImage(Util.rotateImage(arrowImage, shotAngle - 45));
		arrow.setSpeed(2);
		arrow.setVelocityAngle(shotAngle);
	}
	
	
	
}
