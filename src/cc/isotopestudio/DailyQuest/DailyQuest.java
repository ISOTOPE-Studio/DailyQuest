package cc.isotopestudio.DailyQuest;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import cc.isotopestudio.DailyQuest.command.CommandQuest;
import cc.isotopestudio.DailyQuest.util.MySQL;

public class DailyQuest extends JavaPlugin {
	public static final String prefix = (new StringBuilder()).append(ChatColor.GREEN).append("[")
			.append(ChatColor.ITALIC).append(ChatColor.BOLD).append("每日任务").append(ChatColor.RESET)
			.append(ChatColor.GREEN).append("]").append(ChatColor.RESET).toString();
	public static final String pluginName = "DailyQuest";

	public void createFile(String name) {

		File file;
		file = new File(getDataFolder(), name + ".yml");
		if (!file.exists()) {
			saveDefaultConfig();
		}
	}
	
	@Override
	public void onEnable() {
		getLogger().info("加载配置文件中");

		createFile("config");

		this.getCommand("quest").setExecutor(new CommandQuest());
		
		MySQL MySQL = new MySQL("host.name", "port", "database", "user", "pass");
		Connection c = null;
		try {
			c = MySQL.openConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			getLogger().info(pluginName + "数据库出错 Error1");
		} catch (SQLException e) {
			e.printStackTrace();
			getLogger().info(pluginName + "数据库出错 Error2");
		}
		getLogger().info(pluginName + "成功加载!");
		getLogger().info(pluginName + "由ISOTOPE Studio制作!");
		getLogger().info("http://isotopestudio.cc");
	}

	public void onReload() {
	}

	@Override
	public void onDisable() {
		getLogger().info(pluginName + "成功卸载!");
	}

}
