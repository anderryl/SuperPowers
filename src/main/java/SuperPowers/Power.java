package SuperPowers;

import java.util.Set;

import org.bukkit.Material;

public enum Power {
	// defensive path
	TELEPORTATION_4(4, 5, Material.COMPASS, 1, null, null, null),
	DRAIN(1, 5, Material.ENDER_PEARL, 1, null, null, null),
	INVISIBILITY(1, 4, Material.GLASS, 1, TELEPORTATION_4, DRAIN, null),
	TELEPORTATION_3(3, 4, Material.COMPASS, 1, TELEPORTATION_4, DRAIN, null),
	FORCEFEILD(1, 3, Material.END_CRYSTAL, 1, TELEPORTATION_3, INVISIBILITY, null),
	TELEPORTATION_2(2, 3, Material.COMPASS, 1, TELEPORTATION_3, INVISIBILITY, null),
	TELEPORTATION(1, 2, Material.COMPASS, 1, TELEPORTATION_2, FORCEFEILD, null),
	BURROW(1, 1, Material.STONE_SPADE, 1, TELEPORTATION, null, null),
	MATERIALIZATION(1, 1, Material.STONE, 1, TELEPORTATION, null, null),
	//offensive 
	VELOCITY_MANIPULATION(1, 4, Material.SAND, 2, null, null, null),
	FLIGHT(1, 4, Material.ELYTRA, 2, null, null, null),
	ICE_3(3, 3, Material.ICE, 2, VELOCITY_MANIPULATION, FLIGHT, null),
	LIGHTNING(1, 3, Material.BLAZE_ROD, 2,  VELOCITY_MANIPULATION, FLIGHT, null),
	FIRE_3(3, 3, Material.BLAZE_POWDER, 2,  VELOCITY_MANIPULATION, FLIGHT, null),
	ICE_2(2, 2, Material.ICE, 2, ICE_3, LIGHTNING, FIRE_3),
	PYRO(1, 2, Material.FLINT_AND_STEEL, 2, ICE_3, LIGHTNING, FIRE_3),
	FIRE_2(2, 2, Material.BLAZE_POWDER, 2, ICE_3, LIGHTNING, FIRE_3),
	ICE(1, 1, Material.ICE, 2, ICE_2, PYRO, FIRE_2),
	FIRE(1, 1, Material.BLAZE_POWDER, 2, ICE_2, PYRO, FIRE_2),
	//buffs
		//strength
	STRENGTH_5(5, 5, Material.DIAMOND_SWORD, 3, null, null, null),
	STRENGTH_4(4, 4, Material.DIAMOND_SWORD, 3, STRENGTH_5, null, null),
	STRENGTH_3(3, 3, Material.DIAMOND_SWORD, 3, STRENGTH_4, null, null),
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
	HEALTH_5(5, 5, Material.MUSHROOM_SOUP, 6),
	HEALTH_4(4, 4, Material.MUSHROOM_SOUP, 6, HEALTH_5),
	HEALTH_3(3, 3, Material.MUSHROOM_SOUP, 6, HEALTH_4),
	HEALTH_2(2, 2, Material.MUSHROOM_SOUP, 6, HEALTH_3),
	HEALTH(1, 1, Material.MUSHROOM_SOUP, 6, HEALTH_2)
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

}
