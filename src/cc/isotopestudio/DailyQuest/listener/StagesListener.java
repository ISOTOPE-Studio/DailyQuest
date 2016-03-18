package cc.isotopestudio.DailyQuest.listener;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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
		System.out.print("1");
		if (!(e.getEntity() instanceof LivingEntity))
			return;
		LivingEntity dead = e.getEntity();
		if (!(dead.getKiller() instanceof Player))
			return;
		Player player = (Player) dead.getKiller();
		if (!dead.getType().equals(GlobalData.getStage1Type())) {
			return;
		}
		if (PlayerData.getStage(player) != 1) {
			return;
		}
		System.out.print("3");
		PlayerData.increaseStep(player, 1);
		if (PlayerData.getStep(player) >= GlobalData.getStage1Limit()) {
			PlayerData.setStage(player, 2);
			PlayerData.setStep(player, 0);
			player.sendMessage("第二阶段");
			return;
		} else {
			player.sendMessage("Progress: " + PlayerData.getStep(player) + "/" + GlobalData.getStage1Limit());
		}
	}

	@EventHandler
	public void onStage2(BlockBreakEvent e) {
		Player player = e.getPlayer();
		System.out.print("1");
		System.out.print(e.getBlock().getType().toString());
		System.out.print(GlobalData.getStage2Type().toString());
		if (!e.getBlock().getType().equals(GlobalData.getStage2Type())) {
			return;
		}
		if (PlayerData.getStage(player) != 2) {
			return;
		}
		PlayerData.increaseStep(player, 1);
		if (PlayerData.getStep(player) >= GlobalData.getStage2Limit()) {
			PlayerData.setStage(player, 3);
			PlayerData.setStep(player, 0);
			player.sendMessage("第三阶段");
			return;
		} else {
			player.sendMessage("Progress: " + PlayerData.getStep(player) + "/" + GlobalData.getStage2Limit());
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
		player.sendMessage("LOC");
		player.sendMessage(playerLoc.getBlockX() + "x/x" + requiredLoc.getBlockX());
		player.sendMessage(playerLoc.getBlockZ() + "z/z" + requiredLoc.getBlockZ());
		if (Math.abs(playerLoc.getBlockX() - requiredLoc.getBlockX())<=10)
			return;
		if (Math.abs(playerLoc.getBlockZ() - requiredLoc.getBlockZ())<=10)
			return;
		PlayerData.increaseStep(player, 1);
		if (PlayerData.getStep(player) >= 10) {
			PlayerData.setStage(player, 0);
			PlayerData.setStep(player, 0);
			PlayerData.increaseTimes(player, 1);
			player.sendMessage("完成");
			PlayerData.sendReward(player);
			return;
		} else {
			player.sendMessage("Progress: " + PlayerData.getStep(player) + "/10");
		}
	}

}
