package cc.isotopestudio.DailyQuest.listener;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import cc.isotopestudio.DailyQuest.DailyQuest;
import cc.isotopestudio.DailyQuest.data.GlobalData;
import cc.isotopestudio.DailyQuest.data.PlayerData;

public class Stage3Listener extends BukkitRunnable {
	private final DailyQuest plugin;

	public Stage3Listener(DailyQuest plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		List<Player> players = GlobalData.getStage3Location(0).getWorld().getPlayers();
		if (players == null)
			return;
		for (Player player : players) {
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
				PlayerData.setStage(player, 4);
				PlayerData.setStep(player, 0);
				PlayerData.increaseTimes(player, 1);
				player.sendMessage(
						new StringBuilder(DailyQuest.prefix).append(ChatColor.YELLOW).append("任务完成！").toString());
				player.sendMessage(
						new StringBuilder("    ").append(ChatColor.GREEN).append("请输入 /renwu award 领取奖励").toString());
				return;
			} else {
				Location loc = GlobalData.getStage3Location(PlayerData.getStep(player));
				player.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.GREEN)
						.append("第三阶段 已完成: " + PlayerData.getStep(player) + " / 10").toString());
				player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
						.append("下一个地点：X " + loc.getBlockX() + " Z " + loc.getBlockZ()).toString());
				PlayerData.sendDirection(player, plugin);
			}
		}
	}

}
