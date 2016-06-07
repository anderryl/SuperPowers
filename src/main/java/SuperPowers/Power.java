package SuperPowers;

import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Power {
	// defensive path
	TELEPORTATION_4(4, 5, Material.COMPASS, 1),
	DRAIN(1, 5, Material.ENDER_PEARL, 1),
	INVISIBILITY(1, 4, Material.GLASS, 1, TELEPORTATION_4, DRAIN),
	TELEPORTATION_3(3, 4, Material.COMPASS, 1, TELEPORTATION_4, DRAIN),
	FORCEFEILD(1, 3, Material.END_CRYSTAL, 1, TELEPORTATION_3, INVISIBILITY),
	TELEPORTATION_2(2, 3, Material.COMPASS, 1, TELEPORTATION_3, INVISIBILITY),
	TELEPORTATION(1, 2, Material.COMPASS, 1, TELEPORTATION_2, FORCEFEILD),
	BURROW(1, 1, Material.STONE_SPADE, 1, TELEPORTATION),
	MATERIALIZATION(1, 1, Material.STONE, 1, TELEPORTATION),
	//offensive  path
	VELOCITY_MANIPULATION_2(1, 5, Material.SAND, 2),
	FLIGHT_2(1, 5, Material.ELYTRA, 2),
	VELOCITY_MANIPULATION(1, 4, Material.SAND, 2, VELOCITY_MANIPULATION_2),
	FLIGHT(1, 4, Material.ELYTRA, 2, FLIGHT_2),
	ICE_3(3, 3, Material.ICE, 2, VELOCITY_MANIPULATION, FLIGHT),
	LIGHTNING(1, 3, Material.BLAZE_ROD, 2,  VELOCITY_MANIPULATION, FLIGHT),
	FIRE_3(3, 3, Material.BLAZE_POWDER, 2,  VELOCITY_MANIPULATION, FLIGHT),
	ICE_2(2, 2, Material.ICE, 2, ICE_3, LIGHTNING),
	PYRO(1, 2, Material.FLINT_AND_STEEL, 2, ICE_3, LIGHTNING, FIRE_3),
	FIRE_2(2, 2, Material.BLAZE_POWDER, 2, LIGHTNING, FIRE_3),
	ICE(1, 1, Material.ICE, 2, ICE_2, PYRO),
	FIRE(1, 1, Material.BLAZE_POWDER, 2,PYRO, FIRE_2),
	//buffs
		//strength
	STRENGTH_5(5, 5, Material.DIAMOND_SWORD, 3),
	STRENGTH_4(4, 4, Material.DIAMOND_SWORD, 3, STRENGTH_5),
	STRENGTH_3(3, 3, Material.DIAMOND_SWORD, 3, STRENGTH_4),
	STRENGTH_2(2, 2, Material.DIAMOND_SWORD, 3, STRENGTH_3),
	STRENGTH(1, 1, Material.DIAMOND_SWORD, 3, STRENGTH_2),
		//speed
	SPEED_5(5, 5, Material.NETHER_STAR, 4),
	SPEED_4(4, 4, Material.NETHER_STAR, 4, SPEED_5),
	SPEED_3(3, 3, Material.NETHER_STAR, 4, SPEED_4),
	SPEED_2(2, 2, Material.NETHER_STAR, 4, SPEED_3),
	SPEED(1, 1, Material.NETHER_STAR, 4, SPEED_2),
		//jump
	JUMP_5(5, 5, Material.RABBIT_FOOT, 5),
	JUMP_4(4, 4, Material.RABBIT_FOOT, 5, JUMP_5),
	JUMP_3(3, 3, Material.RABBIT_FOOT, 5, JUMP_4),
	JUMP_2(2, 2, Material.RABBIT_FOOT, 5, JUMP_3),
	JUMP(1, 1, Material.RABBIT_FOOT, 5, JUMP_2),
		//health
	HEALTH_5(5, 5, Material.APPLE, 6),
	HEALTH_4(4, 4, Material.APPLE, 6, HEALTH_5),
	HEALTH_3(3, 3, Material.APPLE, 6, HEALTH_4),
	HEALTH_2(2, 2, Material.APPLE, 6, HEALTH_3),
	HEALTH(1, 1, Material.APPLE, 6, HEALTH_2)
	;
	private int teir;
	private int level;
	private Material id;
	private int path;
	private Set<Power> upgrades;
	private Power(int power, int teirId, Material mat, int pathId, Power upgrade1, Power upgrade2, Power upgrade3) {
		level = power;
		teir = teirId;
		id = mat;
		path = pathId;
		if (upgrade1 != null) {
			upgrades.add(upgrade1);
		}
		if (upgrade2 != null) {
			upgrades.add(upgrade2);
		}
		if (upgrade3 != null) {
			upgrades.add(upgrade3);
		}
	}
	private Power(int power, int teirId, Material mat, int pathId, Power upgrade1, Power upgrade2) {
		level = power;
		teir = teirId;
		id = mat;
		path = pathId;
		if (upgrade1 != null) {
			upgrades.add(upgrade1);
		}
		if (upgrade2 != null) {
			upgrades.add(upgrade2);
		}
	}
	
	private Power(int power, int teirId, Material mat, int pathId, Power upgrade1) {
		level = power;
		teir = teirId;
		id = mat;
		path = pathId;
		if (upgrade1 != null) {
			upgrades.add(upgrade1);
		}
	}
	private Power(int power, int teirId, Material mat, int pathId) {
		level = power;
		teir = teirId;
		id = mat;
		path = pathId;
	}
	
	
	public int getTeir() {
		return teir;
	}
	
	public int getLevel() {
		return level;
	}

	public Material getId() {
		return id;
	}
	public int getPathId() {
		return path;
	}
	public Set<Power> getPossibleUpgrades() {
		return upgrades;
	}
	public ItemStack getThumbnail() {
		ItemStack item = new ItemStack(id);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = null;
		switch (id) {
		//defensive 
		case COMPASS:
			meta.setDisplayName("Teleportation " + String.valueOf(level));
			lore.add("Used to teleport up to " + String.valueOf((level * 30)) + " blocks away.");
			lore.add("Good for escaping your enemied!");
			lore.add(ChatColor.RED + "WARNING: DO NOT TELEPORT UP INTO OVERHANGS");
			
		case GLASS:
			meta.setDisplayName("Invisibility");
			lore.add("Turns you invisible until manually disabled.");
			lore.add("Allows for quick ease of movement");
			
		case ENDER_PEARL:
			meta.setDisplayName("Drain");
			lore.add("Gives you the ability to prevent others from using their powers");
			lore.add("With any melee hit, an oppenents powers will be frozen");
			
		case END_CRYSTAL:
			meta.setDisplayName("Forcefeild");
			lore.add("Creates a glass forcefeild that cannot be penetrated");
			lore.add("Forcefeild lasts for 10 seconds");
		
		case STONE_SPADE:
			meta.setDisplayName("Burrow");
			lore.add("Allows you to dig into the ground");
			lore.add("Great for escaping or crushing your enemies");
			
		case STONE:
			meta.setDisplayName("Materialization");
			lore.add("Allows you to place stone blocks");
			lore.add("Rangeis much greater and placing is much faster");
		
		//offensive
		case ELYTRA:
			meta.setDisplayName("Flight "  + String.valueOf(level));
			lore.add("Allows you to fly");
			lore.add("Escape your enemies...");
			lore.add("...or crush them with harsh from above");
			lore.add("Damage is based on velocity");
			
		case SAND:
			meta.setDisplayName("Velocity Manipulation "  + String.valueOf(level));
			lore.add("Instantly change the direction of your momentum");
			lore.add("Sqaush your enemies with crushing blows oput of nowhere");
			lore.add("Damage is based on velocity");
		
		case ICE:
			meta.setDisplayName("Ice "  + String.valueOf(level));
			lore.add("Shoot ice missiles that explode on impact");
			lore.add("Players damaged are given slowness " + String.valueOf(level + 1));
			
		case BLAZE_POWDER:
			meta.setDisplayName("Fire "  + String.valueOf(level));
			lore.add("Shoot fire missilles that explosde on impact");
			lore.add("Players damaged are set on fire for " + String.valueOf((level + 1) * 3) + " seconds");
		
		case BLAZE_ROD:
			meta.setDisplayName("Lightning");
			lore.add("Strike lightning down on your enemies at will");
			lore.add("Sets fire to opponents and sverely damages on a direct hit");
		
		case FLINT_AND_STEEL:
			meta.setDisplayName("Pyro");
			lore.add("Allows you to cause any block to explode");
		
		//buffs
		case DIAMOND_SWORD:
			meta.setDisplayName("Strength " + String.valueOf(level));
			lore.add("Gives you permanent strength " + String.valueOf(level));
		
		case RABBIT_FOOT:
			meta.setDisplayName("Jump " + String.valueOf(level));
			lore.add("Gives you permanent jump " + String.valueOf(level));
			
		case NETHER_STAR:
			meta.setDisplayName("Speed " + String.valueOf(level));
			lore.add("Gives you permanent speed " + String.valueOf((level + 1) * 3));
			
		case APPLE:
			meta.setDisplayName("Health " + String.valueOf(level));
			lore.add("Gives you " + String.valueOf(level * 3) + " more hearts");
		}
		meta.setLore(lore);
		item.setItemMeta(meta);	
		return item;
	}

}
