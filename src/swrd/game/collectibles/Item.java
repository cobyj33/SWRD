package swrd.game.collectibles;

import java.awt.image.BufferedImage;
import java.util.Objects;

import swrd.game.Rarity;
import swrd.game.entities.Entity;

public abstract class Item {
	private String name;
	private String desc;
	private Rarity rarity;
	private ItemType type;
	private BufferedImage image;
	private Entity owner;
	private int uses;
	private int maxUses;
	
	protected Item(String name, String desc, BufferedImage image, Rarity rarity, ItemType type) {
		this.name = name;
		this.desc = desc;
		this.image = image;
		this.rarity = rarity;
		this.type = type;
//		rarity = Rarity.COMMON;
//		type = ItemType.OTHER;
	}
	
	public static enum ItemType {
		WEAPON, OTHER;
	}
	
//	public static Item create(String name, String desc, BufferedImage image) {
//		return new Item(name, desc, image);
//	}
	
	public abstract void use();

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public BufferedImage getImage() {
		return image.getSubimage(0, 0, image.getWidth(), image.getHeight());
	}

	public Entity getOwner() {
		return owner;
	}

	public int getUses() {
		return uses;
	}

	public int getMaxUses() {
		return maxUses;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void setOwner(Entity owner) {
		this.owner = owner;
	}

	public void setUses(int uses) {
		this.uses = uses;
	}

	public void setMaxUses(int maxUses) {
		this.maxUses = maxUses;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}
	
	public ItemType getType() {
		return type;
	}
	
//	public static Item createCopy(Item item) {
//		Item newItem = new Item(item.getName(), item.getDesc(), item.getImage());
//		newItem.setRarity(item.getRarity());
//		return newItem;
//	}

	@Override
	public int hashCode() {
		return Objects.hash(desc, maxUses, name, rarity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(desc, other.desc) && maxUses == other.maxUses && Objects.equals(name, other.name)
				&& rarity == other.rarity;
	}
	
	
	
	
	
}
