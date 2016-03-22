package cc.isotopestudio.DailyQuest.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import cc.isotopestudio.DailyQuest.DailyQuest;

public class PlayerData {

	public static Statement statement;

	public static int getStage(Player player) {
		ResultSet res;
		try {
			res = statement.executeQuery("select * from players where name=\"" + player.getName().toLowerCase() + "\";");
			if (res.next()) {
				return res.getInt("stage");
			} else
				return 0;
		} catch (SQLException e) {
			return 0;
		}
	}

	public static int getStep(Player player) {
		ResultSet res;
		try {
			res = statement.executeQuery("select * from players where name=\"" + player.getName().toLowerCase() + "\";");
			if (res.next()) {
				return res.getInt("step");
			} else
				return 0;
		} catch (SQLException e) {
			return 0;
		}
	}

	public static int getTimes(Player player) {
		ResultSet res;
		try {
			res = statement.executeQuery("select * from players where name=\"" + player.getName().toLowerCase() + "\";");
			if (res.next()) {
				return res.getInt("times");
			} else
				return 0;
		} catch (SQLException e) {
			return 0;
		}
	}

	public static void setStage(Player player, int i) {
		ResultSet res;
		try {
			res = statement.executeQuery("select * from players where name=\"" + player.getName().toLowerCase() + "\";");
			if (res.next())
				statement.executeUpdate("update players set stage=" + i + " where name=\"" + player.getName().toLowerCase() + "\";");
			else
				statement.executeUpdate("insert into players values(\"" + player.getName().toLowerCase() + "\"," + i + ",0,0);");
		} catch (SQLException e) {
			System.out.print("设置Stage出错！");
			return;
		}
	}

	public static void setStep(Player player, int i) {
		try {
			statement.executeUpdate("update players set step=" + i + " where name=\"" + player.getName().toLowerCase() + "\";");
		} catch (SQLException e) {
			System.out.print("设置Step出错！");
			return;
		}
	}

	public static void increaseStep(Player player, int i) {
		try {
			statement.executeUpdate("update players set step=step+" + i + " where name=\"" + player.getName().toLowerCase() + "\";");
		} catch (SQLException e) {
			System.out.print("设置Step出错！");
			return;
		}
	}

	public static void increaseTimes(Player player, int i) {
		try {
			statement
					.executeUpdate("update players set times=times+" + i + " where name=\"" + player.getName().toLowerCase() + "\";");
		} catch (SQLException e) {
			System.out.print("设置Step出错！");
			return;
		}
	}

	public static int getLimit(Player player) {
		int limit = 0;
		Set<String> groupList = GlobalData.tasksLimit.keySet();
		Iterator<String> it = groupList.iterator();
		while (it.hasNext()) {
			String temp = it.next();
			if (player.hasPermission("dailyquest.accept." + temp)) {
				limit = GlobalData.getTasksLimit(temp);
				return limit;
			}
		}
		limit = GlobalData.getTasksLimit("default");
		return limit;
	}

	public static boolean canAccept(Player player) {
		int times = getTimes(player);
		int limit = getLimit(player);
		if (limit <= times) {
			return false;
		} else
			return true;
	}

	public static void sendReward(Player player) {

		int n = GlobalData.random(0, GlobalData.rewardProSum);
		int index = 0;
		while (true) {
			if (GlobalData.rewardPro.get(index) < n) {
				index++;
				continue;
			}
			break;
		}
		String line = GlobalData.rewardList.get(index);
		line = line.replace("%player%", player.getName());
		String commands[] = line.split(";");
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		for (int i = 0; i < commands.length - 1; i++) {
			Bukkit.dispatchCommand(console, commands[i]);
			player.sendMessage("Debug：获得命令" + commands[i]);
		}
		player.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.YELLOW).append("获得奖励！").toString());
	}

}
