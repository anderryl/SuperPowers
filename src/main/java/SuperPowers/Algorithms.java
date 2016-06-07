package SuperPowers;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class Algorithms {
	
	ConcurrentHashMap<Player, HashMap<Location, Material>> forcefeilds = new ConcurrentHashMap<Player, HashMap<Location, Material>>();
	ConcurrentHashMap<Player, HashMap<Location, Integer>> materials = new ConcurrentHashMap<Player, HashMap<Location, Integer>>();
	
	public void invisibility(Player player) {
		if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
			return;
		}
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000, 1, false, false));
	}
	
	public void teleportation(Player player, int level) {		
		Double cx = Math.cos(player.getLocation().getYaw());
		Double cy = Math.sin(player.getLocation().getPitch());
		Double cz = Math.sin(player.getLocation().getYaw());
		Block b1 = null;
		Block b2 = null;
		for (int t = 0; t < level * 30; t +=0.25) {
			Location loc = player.getLocation().clone().add(cx*t, cy*t, cz*t);
			if (b2.equals(loc.getBlock())) {
				b1 = b2;
			}
			b2 = loc.getBlock();
			if (b2.getType() != Material.AIR) {
				break;
			}
		}
		int x = b2.getFace(b1).getModX() + b2.getX();
		int y = b2.getFace(b1).getModY() + b2.getZ();
		int z = b2.getFace(b1).getModZ() + b2.getZ();
		player.teleport(new Location(player.getWorld()
				, x, y, z), TeleportCause.PLUGIN);
	}
	
	public void flight(Player player) {
		Double x = Math.cos(player.getLocation().getPitch()) * player.getFlySpeed();
		Double y = Math.sin(player.getLocation().getPitch()) * player.getFlySpeed();
		Double z = Math.sin(player.getLocation().getYaw()) * player.getFlySpeed();
		player.setVelocity(new Vector(x, y, z));
	}
	
	public HashMap<Location, Material> forcefeildCreate(Player player)  {
		HashMap<Location, Material> map = new HashMap<Location, Material>();
		for (int pitch = 0; pitch < 361; pitch+=10) {
			for (int yaw = 0; yaw < 361; yaw+= 10) {
				int x = (int) Math.round(Math.cos(yaw) * 5 + player.getLocation().getX());
				int y = (int) Math.round(Math.sin(pitch)* 5 + player.getLocation().getY());
				int z = (int) Math.round(Math.sin(yaw)* 5 + player.getLocation().getZ());
				Location loc = new Location(player.getWorld(), x, y, z);
				map.put(loc, loc.getBlock().getType());
				loc.getBlock().setType(Material.GLASS);
			}
		}
		return map;
	}
	
	public void forcefeild(Player player) {
		forcefeildReset(forcefeilds.get(player));
		forcefeildCreate(player);
	}
	
	public void forcefeildReset(HashMap<Location, Material> map) {
		for (Location loc : map.keySet()) {
			loc.getBlock().setType(map.get(loc));
		}
	}
	public void lightning(Player player) {
		BlockIterator bi = new BlockIterator(player, 100);
		Block target = null;
		while (bi.hasNext()) {
			target = bi.next();
		}
		player.getWorld().strikeLightningEffect(target.getLocation());
		player.getWorld().createExplosion(target.getLocation().getX(), 
				target.getLocation().getY(), target.getLocation().getZ(),
				4, true, false);
	}
	
	@SuppressWarnings("deprecation")
	public Runnable pyro(final Player player) {
		BlockIterator bi = new BlockIterator(player, 5);
		Block target = null;
		while (bi.hasNext()) {
			target = bi.next();
		}
		int mat = target.getTypeId();
		Byte data = target.getData();
		target.setType(Material.TNT);
		target.setTypeIdAndData(mat, data, true);
		return new Runnable() {
			public void run() {
				BlockIterator bi = new BlockIterator(player, 5);
				Block t = null;
				while (bi.hasNext()) {
					t = bi.next();
				}
				t.getWorld().createExplosion(t.getX(), t.getY(), t.getZ(),4, true, false);
			}	
		};
	}
	
	public void materialization(Player player) {
		Double cx = Math.cos(player.getLocation().getYaw());
		Double cy = Math.sin(player.getLocation().getPitch());
		Double cz = Math.sin(player.getLocation().getYaw());
		Block b1 = null;
		Block b2 = null;
		for (int t = 0; t < 8; t +=0.25) {
			Location loc = player.getLocation().clone().add(cx*t, cy*t, cz*t);
			if (b2.equals(loc.getBlock())) {
				b1 = b2;
			}
			b2 = loc.getBlock();
			if (b2.getType() != Material.AIR) {
				break;
			}
		}
		materials.get(player).put(b1.getLocation(), 1);
		b1.setType(Material.STONE);
		for (Location loc : materials.get(player).keySet()) {
			materials.get(player).put(loc, materials.get(player).get(loc) + 1);
			if (materials.get(player).get(loc) > 100 && loc.getBlock().getType() == Material.STONE) {
				loc.getBlock().setType(Material.AIR);
				materials.get(player).remove(loc);
			}
		}
	}
}
