package cc.isotopestudio.DailyQuest.task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import org.bukkit.scheduler.BukkitRunnable;

import cc.isotopestudio.DailyQuest.DailyQuest;
import cc.isotopestudio.DailyQuest.data.GlobalData;
import cc.isotopestudio.DailyQuest.data.PlayerData;

public class CheckUpdate extends BukkitRunnable {
	private final DailyQuest plugin;
	private final boolean force;

	public CheckUpdate(DailyQuest plugin) {
		this.plugin = plugin;
		this.force = false;
	}

	public CheckUpdate(DailyQuest plugin, boolean force) {
		this.plugin = plugin;
		this.force = force;
	}

	@Override
	public void run() {
		if(force){
			try {
				GlobalData.insertInformation(plugin, PlayerData.statement);
				return;
			} catch (SQLException e) {
			}
		}
		ResultSet res = null;
		try {
			res = PlayerData.statement.executeQuery("select * from global;");
			res.next();
			int random = res.getInt("random");
			if(random != GlobalData.random){
				GlobalData.insertInformation(plugin, PlayerData.statement);
				plugin.getLogger().info("检测到数据库更新，已更新游戏配置");
			}
		} catch (SQLException e1) {
		}
	}

}
