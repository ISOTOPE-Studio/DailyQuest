package cc.isotopestudio.DailyQuest.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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

	public static void update(DailyQuest plugin, Statement statement, String today) {
		// Gen global data

		List<String> stage1List = plugin.getConfig().getStringList("stage1.monsters");
		int ran = random(0, stage1List.size() - 1);
		String stage1Monster = stage1List.get(ran);
		int stage1Num = random(plugin.getConfig().getInt("stage1.minNum"), plugin.getConfig().getInt("stage1.maxNum"));

		List<String> stage2List = plugin.getConfig().getStringList("stage2.ores");
		ran = random(0, stage2List.size() - 1);
		String stage2Material = stage2List.get(ran);
		int stage2Num = random(plugin.getConfig().getInt("stage2.minNum"), plugin.getConfig().getInt("stage2.maxNum"));
		String world = plugin.getConfig().getString("stage3.world");
		int radius = plugin.getConfig().getInt("stage3.radius");
		try {
			statement.executeUpdate("insert into global values(" + today + ",\"" + stage1Monster + "\"," + stage1Num
					+ ",\"" + stage2Material + "\"," + stage2Num + ");");
			for (int i = 0; i < 10; i++) {
				int x = random(-radius, radius);
				int y = random(-radius, radius);
				int z = random(-radius, radius);
				statement.executeUpdate(
						"insert into globalstage3 values(NULL,\"" + world + "\"," + x + "," + y + "," + z + ");");
			}
			// Insert to database and plugin variables
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

	private static int random(int min, int max) {
		double ran = Math.random() * (max - min + 1) + min;
		return (int) ran;
	}

}
