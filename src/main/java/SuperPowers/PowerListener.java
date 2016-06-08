package SuperPowers;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

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
	public void onInteract(PlayerInteractEvent event) {
		User user = main.users.get(event.getPlayer());
		if (event.getItem().isSimilar(Power.FIRE.getThumbnail())) {
			if (user.hasPowerType(Power.FIRE)) {
				main.fire(event.getPlayer(), user.getPower(Power.FIRE).getLevel());
			}
		}
		if (event.getItem().isSimilar(Power.ICE.getThumbnail())) {
			if (user.hasPowerType(Power.ICE)) {
				main.ice(event.getPlayer(), user.getPower(Power.ICE).getLevel());
			}
		}
		if (event.getItem().isSimilar(Power.PYRO.getThumbnail())) {
			if (user.hasPowerType(Power.PYRO)) {
				algorithms.pyro(user.getPlayer());
			}
		}
		if (event.getItem().isSimilar(Power.LIGHTNING.getThumbnail())) {
			if (user.hasPowerType(Power.LIGHTNING)) {
				algorithms.lightning(user.getPlayer());
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
			}
		}
		if (event.getItem().isSimilar(Power.VELOCITY_MANIPULATION.getThumbnail())) {
			if (user.hasPowerType(Power.VELOCITY_MANIPULATION)) {
				algorithms.velocity(user.getPlayer(), user.getPower(Power.VELOCITY_MANIPULATION).getLevel());
			}
		}
		if (event.getItem().isSimilar(Power.INVISIBILITY.getThumbnail())) {
			if (user.hasPowerType(Power.INVISIBILITY)) {
				algorithms.invisibility(user.getPlayer());
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
}
