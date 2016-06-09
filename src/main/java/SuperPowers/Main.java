package SuperPowers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SuppressWarnings("deprecation")
public class Main extends JavaPlugin implements Listener{
	ConcurrentHashMap<Player, Integer> kills = new ConcurrentHashMap<Player, Integer>();
	public ConcurrentHashMap<Player, User> users = new ConcurrentHashMap<Player, User>();
	ConcurrentHashMap<OfflinePlayer, User> offlineUsers = new ConcurrentHashMap<OfflinePlayer, User>();
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
	public PowerListener listener = new PowerListener(this);
	@SuppressWarnings("null")
	public void onEnable() {
		YamlConfiguration file = new YamlConfiguration();
		try {
			file.load("SuperPowers/src/main/resources/powers.yml");
			int x  = 1;
			while (file.contains(String.valueOf(x))) {
				int y = 1;
				Set<Power> powers = null;
				while (file.contains(String.valueOf(x) + ".powers." + String.valueOf(y))) {
					powers.add(Power.getPower(file.getInt(String.valueOf(x) + ".powers." + String.valueOf(y) + ".type"), 
							file.getInt(String.valueOf(x) + ".powers." + String.valueOf(y) + ".level")));
				}
				offlineUsers.put(getServer().getOfflinePlayer(UUID.fromString
						(file.getString(String.valueOf(x) + ".player"))), 
						new User(getServer().getPlayer(UUID.fromString(file.getString
								(String.valueOf(x) + ".player"))), powers));
				kills.put(getServer().getPlayer(UUID.fromString(file.getString
								(String.valueOf(x) + ".player"))), file.getInt(String.valueOf(x) + ".kills"));
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				for (Player player : getServer().getOnlinePlayers()) {
					player.setTotalExperience(kills.get(player));
					player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 
							40, users.get(player).getPower(Power.STRENGTH).getLevel(), true));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 
							40, users.get(player).getPower(Power.SPEED).getLevel(), true));
					player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 
							40, users.get(player).getPower(Power.JUMP).getLevel(), true));
					player.setMaxHealth(20 + (users.get(player).getPower(Power.HEALTH).getLevel() * 6));
				}
			}
		}, 20L, 20L);
	}
	
	public void onDisable() {
		YamlConfiguration file = new YamlConfiguration();
		int x = 1;
		for (OfflinePlayer user : offlineUsers.keySet()) {
			file.set(String.valueOf(x) + ".player", user.getUniqueId().toString());
			int y = 1;
			for (Power power : offlineUsers.get(user).getPowers()) {
				file.set(String.valueOf(x) + ".powers." + String.valueOf(y) + ".type", power.getId().getId());
				file.set(String.valueOf(x) + ".powers." + String.valueOf(y) + ".level", power.getLevel());
			}
			file.set(String.valueOf(x) + ".kills", kills.get(user.getPlayer()));
		}
		try {
			file.save("SuperPowers/src/main/resources/powers.yml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@EventHandler
	public void onInteract(PlayerInteractEvent evt) {
		if (evt.getItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Defensive")
				&& evt.getItem().getType() == Material.IRON_CHESTPLATE) {
			users.get(evt.getPlayer()).setUpgradeInventory(buildInventory(users.get(evt.getPlayer()), 1));
			evt.getPlayer().openInventory(users.get(evt.getPlayer()).getUpgradeInventory());
		}
		if (evt.getItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Offensive")
				&& evt.getItem().getType() == Material.ARROW) {
			users.get(evt.getPlayer()).setUpgradeInventory(buildInventory(users.get(evt.getPlayer()), 2));
			evt.getPlayer().openInventory(users.get(evt.getPlayer()).getUpgradeInventory());
		}
		if (evt.getItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Buffs")
				&& evt.getItem().getType() == Material.EMERALD) {
			users.get(evt.getPlayer()).setUpgradeInventory(buildInventory(users.get(evt.getPlayer()), 3));
			evt.getPlayer().openInventory(users.get(evt.getPlayer()).getUpgradeInventory());
		}
	}
	@EventHandler
	public void onPlayerClickInv(InventoryClickEvent evt) {
		try {
			User user = users.get((Player) evt.getWhoClicked());
			if (evt.getClickedInventory().equals(user.getUpgradeInventory())) {
				for (Power powe : user.powerSet) {
					for (Power power : powe.getPossibleUpgrades()) {
						if (evt.getCurrentItem() == power.getThumbnail() && 
							evt.getCurrentItem().getItemMeta()
							.equals(power.getThumbnail().getItemMeta())) {
							if (kills.get(evt.getWhoClicked()) >= power.getTeir() * 5) {
								user.addPower(power);
								ItemStack item = power.getThumbnail();
								item.setAmount(1);
								user.getPlayer().getInventory().setItem(power.getPathId() - 1, item);
								kills.put((Player) evt.getWhoClicked(), kills.get(evt.getWhoClicked()) - (power.getTeir() * 5));
							}
							else {
								evt.getWhoClicked().sendMessage(ChatColor.RED + "You do not have enough kill points!");
							}
						}
					}
				}
			}
		}
		catch(ClassCastException e) {
			return;
		}
	}
	public Inventory buildInventory(User user, int type) {
		if (type > 2) {
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
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent evt) {
		for (Power power : users.get(evt.getPlayer()).getPowers()) {
			if (power.getPathId() < 3) {
				evt.getPlayer().getInventory().setItem(power.getPathId() - 1, power.getThumbnail());
			}
		}
		ItemStack d = new ItemStack(Material.IRON_CHESTPLATE);
		ItemMeta dm = d.getItemMeta();
		dm.setDisplayName(ChatColor.BLUE + "Defensive");
		d.setItemMeta(dm);
		ItemStack o = new ItemStack(Material.ARROW);
		ItemMeta om = o.getItemMeta();
		om.setDisplayName(ChatColor.BLUE + "Offensive");
		o.setItemMeta(om);
		ItemStack b = new ItemStack(Material.EMERALD);
		ItemMeta bm = b.getItemMeta();
		bm.setDisplayName(ChatColor.BLUE + "Offensive");
		b.setItemMeta(bm);
		evt.getPlayer().getInventory().setItem(6, d);
		evt.getPlayer().getInventory().setItem(7, o);
		evt.getPlayer().getInventory().setItem(8, b);
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) {
		if (offlineUsers.containsKey(evt.getPlayer())) {
			users.put(evt.getPlayer(), offlineUsers.get(evt.getPlayer()));
		}
		else {
			offlineUsers.put(evt.getPlayer(), new User(evt.getPlayer(), null));
			users.put(evt.getPlayer(), offlineUsers.get(evt.getPlayer()));
			kills.put(evt.getPlayer(), 0);
		}
		for (Power power : users.get(evt.getPlayer()).getPowers()) {
			if (power.getPathId() < 3) {
				evt.getPlayer().getInventory().setItem(power.getPathId() - 1, power.getThumbnail());
			}
		}
		ItemStack d = new ItemStack(Material.IRON_CHESTPLATE);
		ItemMeta dm = d.getItemMeta();
		dm.setDisplayName(ChatColor.BLUE + "Defensive");
		d.setItemMeta(dm);
		ItemStack o = new ItemStack(Material.ARROW);
		ItemMeta om = o.getItemMeta();
		om.setDisplayName(ChatColor.BLUE + "Offensive");
		o.setItemMeta(om);
		ItemStack b = new ItemStack(Material.EMERALD);
		ItemMeta bm = b.getItemMeta();
		bm.setDisplayName(ChatColor.BLUE + "Offensive");
		b.setItemMeta(bm);
		evt.getPlayer().getInventory().setItem(6, d);
		evt.getPlayer().getInventory().setItem(7, o);
		evt.getPlayer().getInventory().setItem(8, b);
	}
}
