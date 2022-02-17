package swrd.game.entities;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import swrd.Main;
import swrd.Resources;
import swrd.game.SWRD;
import swrd.game.collectibles.Item;
import swrd.game.gui.CollectableGUI;
import swrd.game.gui.InventoryGUI;
import swrd.game.levels.maps.Tile;
import swrd.game.levels.maps.TileData;

public class Player extends Entity {
	Set<TileData> collectiblesAround;
	//states: walking, jumping, crouching, 
	
	//stats
	public int enemiesKilled, distanceWalked, damageTaken, damagedealt;
	public double attackReach = 1; //move to weapon details
	Inventory inventory;
	
	public Player(int x, int y, int rotation) {
		super(x, y, rotation);
		setImage(Resources.getImage(Resources.ImageEnum.CHARACTER));
		setMass(70);
		maxHealth = 20;
		health = 10;
		maxStamina = 50;
		stamina = 30;
		inventory = new Inventory(this);
		collectiblesAround = new HashSet<TileData>();
		
		walkSpeed = 8;
		maxSpeed = 16;
		sprintSpeed = 12;
		setSpeed(walkSpeed);
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public void interact() {
		System.out.println("INTERACT");
		Optional<TileData> selectedItem = collectiblesAround.stream().findFirst();
		selectedItem.ifPresent(itemData -> {
			System.out.println("ITEM PICKED UP");
			Item item = itemData.getTile().getCollectible();
			inventory.add(item);
			itemData.getTile().setCollectible(null);
			SWRD.game.getGuiManager().removeCollectableGUI(item);
		});
	}
	
	public void move() {
		if (sprinting) {
			
		}
	}
	
	public void jump() {
		System.out.println("jump");
		super.jump();
	}
	
	public void crouch() {
		crouching = true;
		System.out.println("crouch");
		setSpeed( walkSpeed * 0.25 );
	}
	
	public void roll() {
		rolling = true;
		System.out.println("roll");
		setSpeed( walkSpeed * 2 );
		Main.scheduler.schedule(() -> {
			if (rolling == true && getSpeed() == walkSpeed * 2) {
				setSpeed(walkSpeed);
				System.out.println("Speed reduced");
			}
			rolling = false;
		}, 1000, TimeUnit.MILLISECONDS);
	}
	
	public void attack() {
		usePrimary();
	}
	
	public void usePrimary() {
		Item primary = inventory.getPrimary();
		if (primary != null)
			primary.use();
	}
	
	public void useSecondary() {
		Item secondary = inventory.getSecondary();
		if (secondary != null)
			secondary.use();
	}
	
	public void defend() {
		System.out.println("defend");
		setSpeed(walkSpeed * 0.5);
	}
	
	public void sprint() {
		sprinting = true;
		System.out.println("Sprinting");
		setSpeed(sprintSpeed);
	}
	
	public void walk() {
		System.out.println("Walking");
		setSpeed(walkSpeed);
	}
	
	public double getMovementSpeed() {
//		System.out.println("PLAYER SPEED: " + getSpeed());
		return getSpeed() * (SWRD.DELAY / 1000.);
	}
	
	@Override
	public void movePosition(double x, double y) {
		double previousSpeed = getSpeed();
		super.movePosition(x, y);
		setSpeed(previousSpeed);
	}
	
	public void tick() {
		
		List<TileData> itemsAround = SWRD.game.getMap().getTilesAround((int)getX(), (int)getY()).stream()
				.filter(tileData -> tileData.getTile().getCollectible() != null)
				.collect(Collectors.toList());
		
		itemsAround.stream().forEach(tileData -> {
			if (!collectiblesAround.contains(tileData)) {
				SWRD.game.getGuiManager().requestCollectableGUI(tileData);
				collectiblesAround.add(tileData);
			}
		});
		
		collectiblesAround = collectiblesAround.stream().filter(prev -> itemsAround.contains(prev)).collect(Collectors.toSet());
		
	}
	
	public void showInventory() {
		SWRD.game.getGuiManager().requestInventoryGUI(inventory);;
	}
	
}
