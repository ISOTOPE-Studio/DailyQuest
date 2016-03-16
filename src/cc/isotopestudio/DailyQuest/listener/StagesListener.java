package cc.isotopestudio.DailyQuest.listener;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import cc.isotopestudio.DailyQuest.DailyQuest;
import cc.isotopestudio.DailyQuest.data.GlobalData;
import cc.isotopestudio.DailyQuest.data.PlayerData;

public class StagesListener implements Listener {

	private final DailyQuest plugin;

	public StagesListener(DailyQuest plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onStage1(EntityDeathEvent e) {
		Entity dead = e.getEntity();
		if (!(dead.getLastDamageCause() instanceof Player)) {
			return;
		}
		Player player = (Player) dead.getLastDamageCause();
		if (!dead.equals(GlobalData.getStage1Type())) {
			return;
		}
		if (PlayerData.getStage(player) != 1) {
			return;
		}
		PlayerData.increaseStep(player, 1);
		if (PlayerData.getStep(player) > GlobalData.getStage1Limit()) {
			PlayerData.setStage(player, 2);
			player.sendMessage("第二阶段");
			return;
		}
	}

	@EventHandler
	public void onStage2(BlockBreakEvent e) {
		Player player = e.getPlayer();
		if (!e.getBlock().getType().equals(GlobalData.getStage2Type())) {
			return;
		}
		if (PlayerData.getStage(player) != 2) {
			return;
		}
		PlayerData.increaseStep(player, 1);
		if (PlayerData.getStep(player) > GlobalData.getStage2Limit()) {
			PlayerData.setStage(player, 2);
			player.sendMessage("第三阶段");
			return;
		}
	}

	@EventHandler
	public void onStage3(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		if (PlayerData.getStage(player) != 3) {
			return;
		}
		Location playerLoc = player.getLocation();
		Location requiredLoc = GlobalData.getStage3Location(PlayerData.getStep(player));
		if (playerLoc.getBlockX() != requiredLoc.getBlockX())
			return;
		if (playerLoc.getBlockY() != requiredLoc.getBlockY())
			return;
		PlayerData.increaseStep(player, 1);
		if (PlayerData.getStep(player) > 10) {
			PlayerData.setStage(player, 4);
			player.sendMessage("完成");
			PlayerData.sendReward(player);
			return;
		}
	}

}
