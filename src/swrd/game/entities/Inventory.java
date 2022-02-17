package swrd.game.entities;
import java.util.*;
import java.util.stream.Collectors;

import swrd.Main;
import swrd.game.Rarity;
import swrd.game.SWRD;
import swrd.game.collectibles.Item;
import swrd.game.collectibles.weapons.Bow;
public class Inventory extends ArrayList<Item> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3982455091470023702L;
	Entity owner;
	int selectedPrimary;
	int selectedSecondary;
	
	Inventory(Entity owner) {
		this.owner = owner;
//		add(new GoldSword());
		add(Bow.create(Rarity.UNCOMMON));
		selectedPrimary = 0;
		selectedSecondary = 0;
	}
	
	public boolean add(Item item) {
		item.setOwner(this.owner);
		System.out.println("Owner: " + item.getOwner());
		return super.add(item);
	}
	
	public boolean remove(Item item) {
		item.setOwner(null);
		return super.remove(item);
	}
	
	public Item getPrimary() {
		if (this.isEmpty()) return null;
		return this.get(selectedPrimary);
	}
	
	public Item getSecondary() {
		if (this.isEmpty()) return null;
		return this.get(selectedSecondary);
	}
	
	public List<Item> getWeapons() {
		return this.stream().filter(item -> item.getType() == Item.ItemType.WEAPON).collect(Collectors.toList());
	}
	
	public List<Item> getNonWeapons() {
		return this.stream().filter(item -> item.getType() == Item.ItemType.OTHER).collect(Collectors.toList());
	}
	
}
