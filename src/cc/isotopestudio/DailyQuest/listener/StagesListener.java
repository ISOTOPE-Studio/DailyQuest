package cc.isotopestudio.DailyQuest.listener;

import org.bukkit.ChatColor;
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
		PlayerData.increaseStep(player, 1);
		if (PlayerData.getStep(player) >= GlobalData.getStage1Limit()) {
			PlayerData.setStage(player, 2);
			PlayerData.setStep(player, 0);
			player.sendMessage(new StringBuilder("    ").append(ChatColor.BLUE).append("任务进行到第二阶段").toString());
			player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
					.append("挖 " + GlobalData.getStage2Limit() + " 个" + GlobalData.getStage2Type().toString())
					.toString());

			return;
		} else {
			player.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.GREEN)
					.append("第一阶段 已完成: " + PlayerData.getStep(player) + " / " + GlobalData.getStage1Limit())
					.toString());
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
		if (PlayerData.getStep(player) >= GlobalData.getStage2Limit()) {
			PlayerData.setStage(player, 3);
			PlayerData.setStep(player, 0);
			player.sendMessage(new StringBuilder("    ").append(ChatColor.BLUE).append("任务进行到第三阶段").toString());
			player.sendMessage(new StringBuilder("    ").append(ChatColor.RED).append("到达 10 个指定地点").toString());
			Location loc = GlobalData.getStage3Location(PlayerData.getStep(player));
			player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
					.append("下一个地点：X " + loc.getBlockX() + " Z " + loc.getBlockZ()).toString());

			return;
		} else {
			player.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.GREEN)
					.append("第二阶段 已完成: " + PlayerData.getStep(player) + " / " + GlobalData.getStage2Limit())
					.toString());
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
		if (Math.abs(playerLoc.getBlockX() - requiredLoc.getBlockX()) >= 10)
			return;
		if (Math.abs(playerLoc.getBlockZ() - requiredLoc.getBlockZ()) >= 10)
			return;
		PlayerData.increaseStep(player, 1);
		if (PlayerData.getStep(player) >= 10) {
			PlayerData.setStage(player, 0);
			PlayerData.setStep(player, 0);
			PlayerData.increaseTimes(player, 1);
			player.sendMessage(
					new StringBuilder(DailyQuest.prefix).append(ChatColor.YELLOW).append("任务完成！").toString());

			PlayerData.sendReward(player);
			return;
		} else {
			Location loc = GlobalData.getStage3Location(PlayerData.getStep(player));
			player.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.GREEN)
					.append("第三阶段 已完成: " + PlayerData.getStep(player) + " / 10").toString());
			player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
					.append("下一个地点：X " + loc.getBlockX() + " Z " + loc.getBlockZ()).toString());
		}
	}

}
