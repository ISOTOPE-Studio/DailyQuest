package cc.isotopestudio.DailyQuest.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import cc.isotopestudio.DailyQuest.DailyQuest;

public class GlobalData {

	private static EntityType stage1Type;
	private static int stage1Limit;
	private static Material stage2Type;
	private static int stage2Limit;
	private static Location[] stage3Location;

	public GlobalData(DailyQuest plugin) {

	}

	public static EntityType getStage1Type() {
		return stage1Type;
	}

	public static int getStage1Limit() {
		return stage1Limit;
	}

	public static Material getStage2Type() {
		return stage2Type;
	}

	public static int getStage2Limit() {
		return stage2Limit;
	}

	public static Location getStage3Location(int step) {
		return stage3Location[step];
	}

	public static void update(DailyQuest plugin) {
		// Gen global data
		
		// Insert to database and plugin variables
		Statement statement = null;
		try {
			statement = DailyQuest.c.createStatement();
			ResultSet res = statement.executeQuery("select * from global;");
			res.next();
			stage1Type = EntityType.valueOf(res.getString("stage1Type"));
			stage1Limit = res.getInt("stage1Num");
			stage2Type = Material.getMaterial(res.getString("stage2Type"));
			stage2Limit = res.getInt("stage2Num");
			stage3Location = new Location[10];
			res = statement.executeQuery("select * from globalstage3;");
			for (int i = 0; i < 10; i++) {
				res.next();
				stage3Location[i] = new Location(plugin.getServer().getWorld(res.getString("world")), res.getInt("X"),
						res.getInt("Y"), res.getInt("Z"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
