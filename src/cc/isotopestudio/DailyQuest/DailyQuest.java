package cc.isotopestudio.DailyQuest;

import java.io.File;
import java.sql.Connection;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import cc.isotopestudio.DailyQuest.command.CommandQuest;
import cc.isotopestudio.DailyQuest.listener.JoinListener;
import cc.isotopestudio.DailyQuest.listener.StagesListener;
import cc.isotopestudio.DailyQuest.task.DailyUpdate;
import cc.isotopestudio.DailyQuest.util.MySQL;
import cc.isotopestudio.DailyQuest.util.SqlManager;

public class DailyQuest extends JavaPlugin {
	public static final String prefix = (new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.BOLD).append("[")
			.append("ÿ������").append("]").append(ChatColor.RESET).toString();
	public static final String pluginName = "DailyQuest";

	// mySQL
	public static MySQL MySQL;
	public static Connection c;

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
		if (SqlManager.connectMySQL(this) == false) {
			getLogger().info(pluginName + "�޷�����!");
			getLogger().info(pluginName + "���ݿ��޷����ӣ�");
			this.getPluginLoader().disablePlugin(this);
		}

		BukkitTask task1 = new DailyUpdate(this).runTaskTimer(this, 10, 36000);

		this.getCommand("quest").setExecutor(new CommandQuest(this));
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new StagesListener(this), this);
		pm.registerEvents(new JoinListener(this), this);
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
