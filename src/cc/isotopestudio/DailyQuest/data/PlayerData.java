package cc.isotopestudio.DailyQuest.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.entity.Player;

public class PlayerData {

	public static Statement statement;

	public static int getStage(Player player) {
		ResultSet res;
		try {
			res = statement.executeQuery("select * from players where name=\"" + player.getName() + "\";");
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
			res = statement.executeQuery("select * from players where name=\"" + player.getName() + "\";");
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
			res = statement.executeQuery("select * from players where name=\"" + player.getName() + "\";");
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
			res = statement.executeQuery("select * from players where name=\"" + player.getName() + "\";");
			if (res.next())
				statement.executeUpdate("update players set stage=" + i + " where name=\"" + player.getName() + "\";");
			else
				statement.executeUpdate("insert into players values(\"" + player.getName() + "\"," + i + ",0,0);");
		} catch (SQLException e) {
			System.out.print("设置Stage出错！");
			return;
		}
	}

	public static void setStep(Player player, int i) {
		try {
			statement.executeUpdate("update players set step=" + i + " where name=\"" + player.getName() + "\";");
		} catch (SQLException e) {
			System.out.print("设置Step出错！");
			return;
		}
	}

	public static void increaseStep(Player player, int i) {
		try {
			statement.executeUpdate("update players set step=step+" + i + " where name=\"" + player.getName() + "\";");
		} catch (SQLException e) {
			System.out.print("设置Step出错！");
			return;
		}
	}

	public static void increaseTimes(Player player, int i) {
		try {
			statement
					.executeUpdate("update players set times=times+" + i + " where name=\"" + player.getName() + "\";");
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
		player.sendMessage("Send reward");
	}

}
