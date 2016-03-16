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
			.append(ChatColor.ITALIC).append(ChatColor.BOLD).append("ÿ������").append(ChatColor.RESET)
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
		getLogger().info("���������ļ���");

		createFile("config");

		this.getCommand("quest").setExecutor(new CommandQuest());
		
		MySQL MySQL = new MySQL("host.name", "port", "database", "user", "pass");
		Connection c = null;
		try {
			c = MySQL.openConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			getLogger().info(pluginName + "���ݿ���� Error1");
		} catch (SQLException e) {
			e.printStackTrace();
			getLogger().info(pluginName + "���ݿ���� Error2");
		}
		getLogger().info(pluginName + "�ɹ�����!");
		getLogger().info(pluginName + "��ISOTOPE Studio����!");
		getLogger().info("http://isotopestudio.cc");
	}

	public void onReload() {
	}

	@Override
	public void onDisable() {
		getLogger().info(pluginName + "�ɹ�ж��!");
	}

}
