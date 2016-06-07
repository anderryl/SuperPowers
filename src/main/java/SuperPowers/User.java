package SuperPowers;

import java.util.Set;

import org.bukkit.entity.Player;

public class User {
	Set<Power> powerSet;
	Player player;
	public User(Player player, Set<Power> powers) {
		if (powers.isEmpty()) {
			powerSet.add(Power.BLANK_DEFENSIVE);
			powerSet.add(Power.BLANK_OFFENSIVE);
			powerSet.add(Power.STRENGTH);
			powerSet.add(Power.JUMP);
			powerSet.add(Power.SPEED);
			powerSet.add(Power.HEALTH);
		}
	}
}
