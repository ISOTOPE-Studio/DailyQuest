package cc.isotopestudio.DailyQuest.task;

import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.scheduler.BukkitRunnable;

import cc.isotopestudio.DailyQuest.DailyQuest;
import cc.isotopestudio.DailyQuest.data.GlobalData;

public class DailyUpdate extends BukkitRunnable {
	private final DailyQuest plugin;

	public DailyUpdate(DailyQuest plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		Statement statement = null;
		try {
			statement = DailyQuest.c.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// Check if a new day

		// if true

		// Delete Database
		try {
			statement.executeUpdate("drop table global;");
			statement.executeUpdate("drop table globalstage3;");
			statement.executeUpdate("drop table players;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Delete databse");
		// Create table
		try {
			statement.executeUpdate("create table global(" + "today date not null primary key,"
					+ "stage1Type tinytext not null," + "stage1Num tinyint not null," + "stage2Type tinytext not null,"
					+ "stage2Num tinyint not null);");
			statement.executeUpdate(
					"create table globalstage3(" + " id int unsigned not null auto_increment primary key,"
							+ " X tinyint not null," + " Y tinyint not null," + " Z tinyint not null);");
			statement.executeUpdate("create table players(" + " name char(20) not null primary key,"
					+ " stage tinyint not null," + " step tinyint not null," + " time tinyint not null);");
			statement.executeUpdate("insert into global values(20160316,\"ZOMBIE\",5,\"IRON_ORE\",2);");
			statement.executeUpdate("insert into globalstage3 values(NULL,2,1,-50);");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Update global information
		GlobalData.update(plugin);

	}

}
