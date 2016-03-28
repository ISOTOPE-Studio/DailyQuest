package cc.isotopestudio.DailyQuest.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import cc.isotopestudio.DailyQuest.DailyQuest;
import cc.isotopestudio.DailyQuest.util.ParticleEffect;

public class PlayerData {

	public static Statement statement;

	public static int getStage(Player player) {
		ResultSet res;
		try {
			res = statement
					.executeQuery("select * from players where name=\"" + player.getName().toLowerCase() + "\";");
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
			res = statement
					.executeQuery("select * from players where name=\"" + player.getName().toLowerCase() + "\";");
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
			res = statement
					.executeQuery("select * from players where name=\"" + player.getName().toLowerCase() + "\";");
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
			res = statement
					.executeQuery("select * from players where name=\"" + player.getName().toLowerCase() + "\";");
			if (res.next())
				statement.executeUpdate(
						"update players set stage=" + i + " where name=\"" + player.getName().toLowerCase() + "\";");
			else
				statement.executeUpdate(
						"insert into players values(\"" + player.getName().toLowerCase() + "\"," + i + ",0,0);");
		} catch (SQLException e) {
			System.out.print("设置Stage出错！");
			return;
		}
	}

	public static void setStep(Player player, int i) {
		try {
			statement.executeUpdate(
					"update players set step=" + i + " where name=\"" + player.getName().toLowerCase() + "\";");
		} catch (SQLException e) {
			System.out.print("设置Step出错！");
			return;
		}
	}

	public static void increaseStep(Player player, int i) {
		try {
			statement.executeUpdate(
					"update players set step=step+" + i + " where name=\"" + player.getName().toLowerCase() + "\";");
		} catch (SQLException e) {
			System.out.print("设置Step出错！");
			return;
		}
	}

	public static void increaseTimes(Player player, int i) {
		try {
			statement.executeUpdate(
					"update players set times=times+" + i + " where name=\"" + player.getName().toLowerCase() + "\";");
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

	public static void sendDirection(Player player, DailyQuest plugin) {
		Location a = player.getEyeLocation();
		Location b = GlobalData.getStage3Location(getStep(player));
		if (!a.getWorld().equals(b.getWorld())) {
			player.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.RED)
					.append("这里不是资源世界, 请前往资源世界的指定地点").toString());
			return;
		}
		int x = b.getBlockX() - a.getBlockX();
		int y = 0;
		int z = b.getBlockZ() - a.getBlockZ();
		Vector v = new Vector(x, y, z);
		Double scale = 0.5 / v.length();
		v.multiply(scale);
		System.out.println(v.toString());
		sendEffect(v, player, 0, plugin);
	}

	static void sendEffect(final Vector v, final Player player, int i, DailyQuest plugin) {
		new BukkitRunnable() {
			public void run() {
				if (i < 7) {
					sendEffect(v, player, i + 1, plugin);
				}
				ParticleEffect.FLAME.display(v, 1, player.getEyeLocation(), player);
			}
		}.runTaskLater(plugin, 10);

	}

	public static void sendReward(Player player) {

		int n = GlobalData.random(0, GlobalData.rewardProSum);
		int index = 0;
		System.out.print(GlobalData.rewardPro + ":" + n + ":" + GlobalData.rewardProSum);
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
			// player.sendMessage("Debug：获得命令" + commands[i]);
		}
		player.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.YELLOW).append("获得奖励！").toString());
	}

}
