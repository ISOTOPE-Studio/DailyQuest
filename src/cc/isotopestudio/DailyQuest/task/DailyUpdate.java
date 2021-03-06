package cc.isotopestudio.DailyQuest.task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import org.bukkit.scheduler.BukkitRunnable;

import cc.isotopestudio.DailyQuest.DailyQuest;
import cc.isotopestudio.DailyQuest.data.GlobalData;

public class DailyUpdate extends BukkitRunnable {
	private final DailyQuest plugin;
	private final boolean force;

	public DailyUpdate(DailyQuest plugin) {
		this.plugin = plugin;
		this.force = false;
	}

	public DailyUpdate(DailyQuest plugin, boolean force) {
		this.plugin = plugin;
		this.force = force;
	}

	@Override
	public void run() {
		boolean ifTableExist = true;
		Statement statement = null;
		try {
			statement = DailyQuest.c.createStatement();
		} catch (SQLException e1) {
		}
		// Check if a new day
		ResultSet res = null;
		java.util.Date todayDate = new java.util.Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String today = format.format(todayDate);
		try {
			res = statement.executeQuery("select * from global;");
		} catch (SQLException e1) {
			// if table does not exist
			ifTableExist = false;
		}
		if (ifTableExist && !force) // Check if date is correct
			try {
				if (res.next()) {
					ifTableExist = true;
					java.util.Date dayDate = new java.util.Date((res.getDate("today").getTime()));
					String day = format.format(dayDate);
					if (day.equals(today)) {
						GlobalData.insertInformation(plugin, statement);
						return;
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		try {
			if (ifTableExist || force) {
				// Delete tables
				statement.executeUpdate("drop table global;");
				statement.executeUpdate("drop table globalstage3;");
				statement.executeUpdate("drop table players;");
				System.out.println("Deleted tables");
			}
			// Create tables
			statement.executeUpdate("create table global(" + "today date not null primary key,"
					+ "random int not null,"+ "stage1Type tinytext not null," + "stage1Num tinyint not null," + "stage2Type tinytext not null,"
					+ "stage2Num tinyint not null);");
			statement.executeUpdate("create table globalstage3("
					+ " id int unsigned not null auto_increment primary key," + " world text not null,"
					+ " X SMALLINT not null," + " Y SMALLINT not null," + " Z SMALLINT not null);");
			statement.executeUpdate("create table players(" + " name char(20) not null primary key,"
					+ " stage tinyint not null," + " step tinyint not null," + " times tinyint not null);");
			System.out.println("Created tables");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// Update global information
		plugin.reloadConfig();
		GlobalData.update(plugin, statement, today);

	}

}
