package cc.isotopestudio.DailyQuest.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
			System.out.print("����Stage����");
			return;
		}
	}

	public static void setStep(Player player, int i) {
		try {
			statement.executeUpdate("update players set step=" + i + " where name=\"" + player.getName() + "\";");
		} catch (SQLException e) {
			System.out.print("����Step����");
			return;
		}
	}

	public static void increaseStep(Player player, int i) {
		try {
			statement.executeUpdate("update players set step=step+" + i + " where name=\"" + player.getName() + "\";");
		} catch (SQLException e) {
			System.out.print("����Step����");
			return;
		}
	}

	public static void increaseTimes(Player player, int i) {
		try {
			statement
					.executeUpdate("update players set times=times+" + i + " where name=\"" + player.getName() + "\";");
		} catch (SQLException e) {
			System.out.print("����Step����");
			return;
		}
	}

	public static void sendReward(Player player) {
		player.sendMessage("Send reward");
	}

}
