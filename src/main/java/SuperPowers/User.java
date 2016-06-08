package SuperPowers;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class User {
	Set<Power> powerSet;
	Player player;
	public User(Player user, Set<Power> powers) {
		if (powers.isEmpty()) {
			powerSet.add(Power.BLANK_DEFENSIVE);
			powerSet.add(Power.BLANK_OFFENSIVE);
			powerSet.add(Power.STRENGTH);
			powerSet.add(Power.JUMP);
			powerSet.add(Power.SPEED);
			powerSet.add(Power.HEALTH);
		}
		else {
			powerSet = powers;
		}
		player = user;
	}
	
	public Player getPlayer() {
		return player;
	}
	public Set<Power> getPowers() {
		return powerSet;
	}
	public Boolean hasPower(Power power) {
		if (powerSet.contains(power)) {
			return true;
		}
		else {
			return false;
		}
	}
	public Power getPower(Power type){
		for (Power power : powerSet) {
			if (power.getId() == type.getId()) {
				return power;
			}
		}
		return null;
	}
	public Boolean hasPowerType(Power match) {
		for (Power power : powerSet) {
			if (power.getId() == match.getId()) {
				return true;
			}
		}
		return false;
	}
	public void addPower(Power power) {
		for (Power c : powerSet) {
			if (c.getPathId() == power.getPathId()) {
				powerSet.remove(c);
			}
		}
		powerSet.add(power);
	}
	
}
