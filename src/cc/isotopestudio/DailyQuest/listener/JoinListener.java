package cc.isotopestudio.DailyQuest.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import cc.isotopestudio.DailyQuest.DailyQuest;
import cc.isotopestudio.DailyQuest.data.PlayerData;

public class JoinListener implements Listener {
	private final DailyQuest plugin;

	public JoinListener(DailyQuest plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		if (PlayerData.getStage(player) == 0 && PlayerData.canAccept(player)) {
			new BukkitRunnable() {
				@Override
				public void run() {
					player.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.GREEN)
							.append("输入指令 /renwu accept 接受本日任务！完成任务即可获得丰厚奖励！").toString());
				}
			}.runTaskLater(plugin, 20);

		}
	}

}
