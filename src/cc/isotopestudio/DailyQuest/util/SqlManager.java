package cc.isotopestudio.DailyQuest.util;

import java.sql.*;

import cc.isotopestudio.DailyQuest.DailyQuest;

public class SqlManager {

	public static boolean connectMySQL(DailyQuest plugin) {
		String host = plugin.getConfig().getString("mySQL.host");
		String port = plugin.getConfig().getString("mySQL.port");
		String user = plugin.getConfig().getString("mySQL.user");
		String pw = plugin.getConfig().getString("mySQL.password");
		String db = plugin.getConfig().getString("mySQL.database");
		DailyQuest.MySQL = new MySQL(host, port, db, user, pw);
		DailyQuest.c = null;
		try {
			DailyQuest.c = DailyQuest.MySQL.openConnection();
		} catch (ClassNotFoundException e) {
			plugin.getLogger().info(DailyQuest.pluginName + "数据库出错 Error1");
			return false;
		} catch (SQLException e) {
			plugin.getLogger().info(DailyQuest.pluginName + "数据库出错 Error2");
			return false;
		}
		return true;
	}

}
