package swrd.game.collectibles.weapons;

import java.awt.Point;
import java.awt.image.BufferedImage;

import swrd.Resources;
import swrd.Util;
import swrd.game.Rarity;
import swrd.game.SWRD;
import swrd.game.collectibles.Item;
import swrd.game.collectibles.Item.ItemType;
import swrd.game.entities.Player;
import swrd.game.logic.Collider;

public class Sword extends Item {
	int damage;
	int attackReach;
	
	private Sword(int damage, int attackReach, String name, String desc, BufferedImage image, Rarity rarity) {
		super(name, desc, image, rarity, ItemType.WEAPON);
		this.damage = damage;
		this.attackReach = attackReach;
	}
	
	public static Sword create(Rarity rarity) {
		switch (rarity) {
		case COMMON: return new Sword(3, 1, "Common Sword", "Sharp as a baseball bat", Resources.getImage(Resources.ImageEnum.COMMON_SWORD), Rarity.COMMON);
		case UNCOMMON: return new Sword(8, 1, "Uncommon Sword", "Sharp as a baseball bat", Resources.getImage(Resources.ImageEnum.UNCOMMON_SWORD), Rarity.UNCOMMON);
		case RARE: return new Sword(15, 1, "Rare Sword", "Sharp as a baseball bat", Resources.getImage(Resources.ImageEnum.RARE_SWORD), Rarity.RARE);
		case EXOTIC: return new Sword(30, 1, "Exotic Sword", "Sharp as a baseball bat", Resources.getImage(Resources.ImageEnum.EXOTIC_SWORD), Rarity.EXOTIC);
		case UNTOUCHED: return new Sword(50, 1, "Untouched Sword", "Sharp as a baseball bat", Resources.getImage(Resources.ImageEnum.UNTOUCHED_SWORD), Rarity.UNTOUCHED);
		}
		
		return new Sword(3, 1, "Common Sword", "Sharp as a baseball bat", Resources.getImage(Resources.ImageEnum.COMMON_SWORD), Rarity.COMMON);
	}
	
	public void use() {
//		System.out.println(getOwner());
		if (getOwner() instanceof Player) {
			Point playerCameraPos = SWRD.game.getCamera().getCoordinatesOnCamera(SWRD.player);
			double angleOfAttack = Util.getAngle(playerCameraPos.getX(), playerCameraPos.getY(), SWRD.game.mouseX, SWRD.game.mouseY);
//			System.out.println("Angle of attack: " + angleOfAttack);
			
			double attackX = SWRD.player.getX() + attackReach * Math.cos(Math.toRadians(angleOfAttack));
			double attackY = SWRD.player.getY() + attackReach * -Math.sin(Math.toRadians(angleOfAttack));
			
			Collider attackHitBox = new Collider(attackX, attackY, 1, 200, false, getOwner());
			attackHitBox.setColor(getRarity().getTextColor());
//			attackHitBox.setSource(SWRD.player);
		}
	}
	
	
}
