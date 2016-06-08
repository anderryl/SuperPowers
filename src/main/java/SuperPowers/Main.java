package SuperPowers;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SuppressWarnings("deprecation")
public class Main extends JavaPlugin implements Listener{
	ConcurrentHashMap<Player, Integer> kills = new ConcurrentHashMap<Player, Integer>();
	public void ice(Player player, final int level) {
		Double cx = Math.cos(player.getLocation().getYaw());
		Double cy = Math.sin(player.getLocation().getPitch());
		Double cz = Math.sin(player.getLocation().getYaw());
		Block b1 = null;
		Block b2 = null;
		Long l = 1L;
		final UUID id = player.getUniqueId();
		for (int t = 0; t < 8; t +=0.25) {
			final Location loc = player.getLocation().clone().add(cx*t, cy*t, cz*t);
			getServer().getScheduler().scheduleAsyncDelayedTask(this, new Runnable() {
				public void run() {
					loc.getWorld().spawnParticle(Particle.WATER_BUBBLE, loc, 10);
					if (loc.getBlock().getType() != Material.AIR) {
						loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 6, true, false);
						Entity m = loc.getWorld().spawnEntity(loc, EntityType.UNKNOWN);
						for (Entity e : m.getNearbyEntities(4, 4, 4)) {
							if (e instanceof Player) {
								Player p = (Player) e;
								p.damage(level * 4);
								if (p.getHealth() == 0) {
									kills.put(getServer().getPlayer(id), kills.get(getServer().getPlayer(id)) + 1);
								}
								p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, ((level + 1) * 3) * 20, level + 1));	
							}
						}
						m.remove();
					}
				}
			}, l);
			if (!b2.equals(loc.getBlock())) {
				b1 = b2;
				final Location loc2 = b1.getLocation().clone();
				getServer().getScheduler().scheduleAsyncDelayedTask(this, new Runnable() {
					public void run() {
						loc2.getWorld().spawnParticle(Particle.WATER_BUBBLE, loc, 10);
					}
				}, l);
			}
			b2 = loc.getBlock();
			if (b2.getType() != Material.AIR) {
				break;
			}
			l ++;
		}
	}
	public void fire(Player player, final int level) {
		Double cx = Math.cos(player.getLocation().getYaw());
		Double cy = Math.sin(player.getLocation().getPitch());
		Double cz = Math.sin(player.getLocation().getYaw());
		Block b1 = null;
		Block b2 = null;
		Long l = 1L;
		for (int t = 0; t < 8; t +=0.25) {
			final Location loc = player.getLocation().clone().add(cx*t, cy*t, cz*t);
			final UUID id = player.getUniqueId();
			getServer().getScheduler().scheduleAsyncDelayedTask(this, new Runnable() {
			
				public void run() {
					loc.getWorld().spawnParticle(Particle.LAVA, loc, 10);
					if (loc.getBlock().getType() != Material.AIR) {
						loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 6, true, false);
						Entity m = loc.getWorld().spawnEntity(loc, EntityType.UNKNOWN);
						for (Entity e : m.getNearbyEntities(4, 4, 4)) {
							if (e instanceof Player) {
								Player p = (Player) e;
								p.damage(level * 4);
								if (p.getHealth() == 0) {
									kills.put(getServer().getPlayer(id), kills.get(getServer().getPlayer(id)) + 1);
								}
								p.setFireTicks(((level + 1) * 3) * 20);
								
							}
						}
						m.remove();
					}
				}
			}, l);
			if (!b2.equals(loc.getBlock())) {
				b1 = b2;
				final Location loc2 = b1.getLocation().clone();
				getServer().getScheduler().scheduleAsyncDelayedTask(this, new Runnable() {
					public void run() {
						loc2.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc, 1);
					}
				}, l);
			}
			b2 = loc.getBlock();
			if (b2.getType() != Material.AIR) {
				break;
			}
			l ++;
		}
	}
	
	public void onEnable() {
	}
	
	public void onDisable() {
	}
	@EventHandler
	public void onPlayerClickInv(InventoryClickEvent evt) {
		
	}
	public Inventory buildInventory(User user, int type) {
		if (type == 3) {
			Inventory inv = getServer().createInventory(null, InventoryType.PLAYER, "Buffs");
			Power u = (Power) user.getPower(Power.STRENGTH).getPossibleUpgrades().toArray()[0];
			inv.setItem(19, u.getThumbnail());
			u = (Power) user.getPower(Power.SPEED).getPossibleUpgrades().toArray()[0];
			inv.setItem(21, u.getThumbnail());
			u = (Power) user.getPower(Power.JUMP).getPossibleUpgrades().toArray()[0];
			inv.setItem(23, u.getThumbnail());
			u = (Power) user.getPower(Power.HEALTH).getPossibleUpgrades().toArray()[0];
			inv.setItem(25, u.getThumbnail());
			return inv;
		}
		Power power = null;
		for (Power powe : user.getPowers()) {
			if (powe.getPathId() == type) {
				power = powe;
				break;
			}
		}
		if (type == 1) {
			Inventory inv = getServer().createInventory(null, InventoryType.PLAYER, "Defensive");
			int slotC = 0;
			int slotI = (int) (18 + ((power.getPossibleUpgrades().toArray().length) * (power.getPossibleUpgrades().toArray().length)) / 4.5);
			for (Power powe : power.getPossibleUpgrades()) {
				slotC = (int) Math.floor(6 / power.getPossibleUpgrades().toArray().length);
				slotI += slotC;
				inv.setItem(slotI, powe.getThumbnail());
				return inv;
			}
			return inv;
		}
		Inventory inv = getServer().createInventory(null, InventoryType.PLAYER, "Offensive");
		int slotC = 0;
		int slotI = (int) (18 + ((power.getPossibleUpgrades().toArray().length) 
				* (power.getPossibleUpgrades().toArray().length)) / 4.5);
		for (Power powe : power.getPossibleUpgrades()) {
			slotC = (int) Math.floor(6 / power.getPossibleUpgrades().toArray().length);
			slotI += slotC;
			inv.setItem(slotI, powe.getThumbnail());
			return inv;
		}
		return inv;
	}
}
