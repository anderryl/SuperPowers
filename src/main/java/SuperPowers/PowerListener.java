package SuperPowers;

import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.Vector;

public class PowerListener implements Listener{
	Main main;
	Algorithms algorithms;
	public PowerListener(Main m) {
		main = m;
		algorithms = new Algorithms();
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent evt) {
		evt.setCancelled(true);
	}
	@EventHandler
	public void onBlockBurn(BlockBurnEvent evt) {
		evt.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent evt) {
		evt.setCancelled(true);
	}
	@EventHandler
	public void onPlayerHit(PlayerDropItemEvent evt) {
		evt.setCancelled(true);
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		User user = main.users.get(event.getPlayer());
		if (event.getItem().isSimilar(Power.FIRE.getThumbnail())) {
			if (user.hasPowerType(Power.FIRE)) {
				main.fire(event.getPlayer(), user.getPower(Power.FIRE).getLevel());
				event.setCancelled(true);
				return;
			}
		}
		if (event.getItem().isSimilar(Power.ICE.getThumbnail())) {
			if (user.hasPowerType(Power.ICE)) {
				main.ice(event.getPlayer(), user.getPower(Power.ICE).getLevel());
				event.setCancelled(true);
				return;
			}
		}
		if (event.getItem().isSimilar(Power.LIGHTNING.getThumbnail())) {
			if (user.hasPowerType(Power.LIGHTNING)) {
				algorithms.lightning(user.getPlayer());
				event.setCancelled(true);
				return;
			}
		}
		if (event.getItem().isSimilar(Power.FLIGHT.getThumbnail())) {
			if (user.hasPowerType(Power.FLIGHT)) {
				if (user.getPower(Power.FLIGHT).getLevel() == 2) {
					user.getPlayer().setAllowFlight(true);
					user.getPlayer().setFlySpeed(0.1f);
				}
				else {
					user.getPlayer().setAllowFlight(true);
					user.getPlayer().setFlySpeed(0.5f);
				}
				flight(user.getPlayer());
				event.setCancelled(true);
			}
		}
		if (event.getItem().isSimilar(Power.VELOCITY_MANIPULATION.getThumbnail())) {
			if (user.hasPowerType(Power.VELOCITY_MANIPULATION)) {
				algorithms.velocity(user.getPlayer(), user.getPower(Power.VELOCITY_MANIPULATION).getLevel());
				event.setCancelled(true);
				return;
			}
		}
		if (event.getItem().isSimilar(Power.INVISIBILITY.getThumbnail())) {
			if (user.hasPowerType(Power.INVISIBILITY)) {
				algorithms.invisibility(user.getPlayer());
				event.setCancelled(true);
				return;
			}
		}
		if (event.getItem().isSimilar(Power.PYRO.getThumbnail())) {
			if (user.hasPowerType(Power.PYRO)) {
				main.getServer().getScheduler().scheduleAsyncDelayedTask(main ,algorithms.pyro(user.getPlayer()), 100L);
				event.setCancelled(true);
				return;
			}
		}
		if (event.getItem().isSimilar(Power.FORCEFEILD.getThumbnail())) {
			if (user.hasPowerType(Power.FORCEFEILD)) {
				algorithms.forcefeild(user.getPlayer());
				forcefeild(user.getPlayer());
				event.setCancelled(true);
			}
		}
		if (event.getItem().isSimilar(Power.LIGHTNING.getThumbnail())) {
			if (user.hasPowerType(Power.LIGHTNING)) {
				algorithms.lightning(user.getPlayer());
				event.setCancelled(true);
				return;
			}
		}
		if (event.getItem().isSimilar(Power.TELEPORTATION.getThumbnail())) {
			if (user.hasPowerType(Power.TELEPORTATION)) {
				algorithms.teleportation(user.getPlayer(), user.getPower(Power.TELEPORTATION).getLevel());
				event.setCancelled(true);
				return;
			}
		}
		if (event.getItem().isSimilar(Power.BURROW.getThumbnail())) {
			if (user.hasPowerType(Power.BURROW)) {
				algorithms.burrow(user.getPlayer());
				event.setCancelled(true);
				return;
			}
		}
		if (event.getItem().isSimilar(Power.MATERIALIZATION.getThumbnail())) {
			if (user.hasPowerType(Power.MATERIALIZATION)) {
				algorithms.materialization(user.getPlayer());
				event.setCancelled(true);
				return;
			}
		}
	}
	@SuppressWarnings("deprecation")
	public void flight(Player plyer) {
		final UUID id = plyer.getUniqueId();
		main.getServer().getScheduler().scheduleAsyncDelayedTask(main, new Runnable() {

			@Override
			public void run() {
				Player player = main.getServer().getPlayer(id);
				if (player.getItemInHand().getType() == Material.ELYTRA) {
					algorithms.flight(player);
					flight(player);
				}
			}
			
		}, 1L);
	}
	@SuppressWarnings("deprecation")
	public void forcefeild(Player plyer) {
		final UUID id = plyer.getUniqueId();
		main.getServer().getScheduler().scheduleAsyncDelayedTask(main, new Runnable() {

			@Override
			public void run() {
				Player player = main.getServer().getPlayer(id);
				if (player.getItemInHand().getType() == Material.END_CRYSTAL) {
					algorithms.forcefeild(player);
					forcefeild(player);
				}
			}
			
		}, 4L);
	}
	@EventHandler
	public void onPlayerdamage(EntityDamageByEntityEvent evt) {
		if (evt.getEntity() instanceof Player && evt.getDamager() instanceof Player) {
			Player player = (Player) evt.getDamager();
			Player damagee = (Player) evt.getEntity();
			
			if (main.users.get(player).hasPowerType(Power.FLIGHT) || 
					main.users.get(player).hasPowerType(Power.VELOCITY_MANIPULATION)) {
				if (main.users.get(player).hasPowerType(Power.FLIGHT)) {
					evt.setDamage(player.getVelocity().distance(new Vector())
							* main.users.get(player).getPower(Power.FLIGHT).getLevel() + evt.getDamage());
				}
				else {
					evt.setDamage(player.getVelocity().distance(new Vector())
							* main.users.get(player).getPower(Power.VELOCITY_MANIPULATION).getLevel() + evt.getDamage());
				}
			}
		}
	}
	// coming soon
	/*@SuppressWarnings("null");
	@EventHandler
	public void onPlayerMoveItem(InventoryClickEvent evt) {
		if (evt.getWhoClicked() instanceof Player) {
			Player player = (Player) evt.getWhoClicked();
			evt.setCancelled(true);
			if  (evt.getCurrentItem().getItemMeta() instanceof LeatherArmorMeta) {
				ItemStack item = evt.getCurrentItem();
				LeatherArmorMeta meta = (LeatherArmorMeta)  item.getItemMeta();
				Color[] colors = null;
				colors[0] = Color.AQUA;
				colors[1] = Color.BLACK;
				colors[2] = Color.BLUE;
				colors[3] = Color.FUCHSIA;
				colors[4] = Color.GRAY;
				colors[5] = Color.GREEN;
				colors[6] = Color.LIME;
				colors[7] = Color.MAROON;
				colors[8] = Color.NAVY;
				colors[9] = Color.OLIVE;
				colors[10] = Color.ORANGE;
				colors[11] = Color.PURPLE;
				colors[12] = Color.RED;
				colors[13] = Color.SILVER;
				colors[14] = Color.TEAL;
				colors[15] = Color.WHITE;
				colors[16] = Color.YELLOW;
				int x = 0;
				for (Color color : colors) {
					if (color == meta.getColor()) {
						break;
					}
					x ++;
				}
				if (evt.getClick().isLeftClick()) {
					x ++;
				}
				if (evt.getClick().isRightClick()) {
					x --;
				}
				meta.setColor(colors[x]);
				item.setItemMeta(meta);
			}
		}
	}
	*/
}
