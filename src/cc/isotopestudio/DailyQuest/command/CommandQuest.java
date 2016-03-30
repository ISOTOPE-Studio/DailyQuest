package cc.isotopestudio.DailyQuest.command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import cc.isotopestudio.DailyQuest.DailyQuest;
import cc.isotopestudio.DailyQuest.data.GlobalData;
import cc.isotopestudio.DailyQuest.data.PlayerData;
import cc.isotopestudio.DailyQuest.task.CheckUpdate;
import cc.isotopestudio.DailyQuest.task.DailyUpdate;

public class CommandQuest implements CommandExecutor {

	private final DailyQuest plugin;

	public CommandQuest(DailyQuest plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("quest")) {
			try {
				if (sender.isOp() && args[0].equalsIgnoreCase("update")) {
					new DailyUpdate(plugin, true).runTaskLater(plugin, 10);
					sender.sendMessage(new StringBuilder(DailyQuest.prefix).append("重置数据库").toString());
					return true;
				}
				if (sender.isOp() && args[0].equalsIgnoreCase("fix")) {
					new CheckUpdate(plugin, true).runTaskLater(plugin, 10);
					sender.sendMessage(new StringBuilder(DailyQuest.prefix).append("尝试修复").toString());
					return true;
				}
			} catch (Exception e) {
			}
			if (!(sender instanceof Player)) {
				sender.sendMessage(
						new StringBuilder(DailyQuest.prefix).append(ChatColor.RED).append("必须要玩家才能执行").toString());
				return true;
			}
			Player player = (Player) sender;
			if (args.length > 0 && !args[0].equalsIgnoreCase("help")) {
				if (player.isOp() && args[0].equalsIgnoreCase("test")) {
					for (int i = 0; i <= 100; i++)
						PlayerData.sendReward(player);
				}
				if (args[0].equalsIgnoreCase("accept")) {
					if (!PlayerData.canAccept(player)) {
						player.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.RED)
								.append("你不能再接受新的任务了").toString());
						return true;
					}
					if (PlayerData.getStage(player) >= 4) {
						player.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.BLUE)
								.append("任务已完成，未领取奖励").toString());
						return true;
					}
					if (PlayerData.getStage(player) >= 1) {
						player.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.BLUE).append("任务正在进行")
								.toString());
						return true;
					}
					PlayerData.setStage(player, 1);
					player.sendMessage(
							new StringBuilder(DailyQuest.prefix).append(ChatColor.YELLOW).append("任务现在开始！").toString());
					player.sendMessage(new StringBuilder("    ").append(ChatColor.BLUE).append("任务进行到第一阶段").toString());
					player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
							.append("杀死 " + GlobalData.getStage1Limit() + " 只" + GlobalData.getStage1TypeName())
							.toString());
					return true;
				}
				if (args[0].equalsIgnoreCase("info")) {
					player.sendMessage(
							new StringBuilder(DailyQuest.prefix).append(ChatColor.GREEN).append("任务信息").toString());
					player.sendMessage(
							new StringBuilder().append(ChatColor.YELLOW)
									.append("你今天已做任务" + PlayerData.getTimes(player) + "次，还可以做"
											+ (PlayerData.getLimit(player) - PlayerData.getTimes(player)) + "次")
							.toString());
					if (PlayerData.canAccept(player))
						switch (PlayerData.getStage(player)) {
						case (0): {
							player.sendMessage(
									new StringBuilder("    ").append(ChatColor.BLUE).append("任务未开始").toString());
							break;
						}
						case (1): {
							player.sendMessage(
									new StringBuilder("    ").append(ChatColor.BLUE).append("任务进行到第一阶段").toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
									.append("杀死 " + GlobalData.getStage1Limit() + " 只" + GlobalData.getStage1TypeName())
									.toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
									.append("你已完成 " + PlayerData.getStep(player) + " 只").toString());
							break;
						}
						case (2): {
							player.sendMessage(
									new StringBuilder("    ").append(ChatColor.BLUE).append("任务进行到第二阶段").toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
									.append("挖 " + GlobalData.getStage2Limit() + " 个" + GlobalData.getStage2TypeName())
									.toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
									.append("你已完成 " + PlayerData.getStep(player) + " 个").toString());
							break;
						}
						case (3): {
							Location loc = GlobalData.getStage3Location(PlayerData.getStep(player));
							player.sendMessage(
									new StringBuilder("    ").append(ChatColor.BLUE).append("任务进行到第三阶段").toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
									.append("到达 10 个指定地点(在资源世界)").toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
									.append("你已完成 " + PlayerData.getStep(player) + " 个").toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
									.append("下一个地点：X " + loc.getBlockX() + " Z " + loc.getBlockZ()).toString());
							PlayerData.sendDirection(player, plugin);
							break;
						}
						case (4): {
							player.sendMessage(
									new StringBuilder("    ").append(ChatColor.BLUE).append("任务已完成，未领取奖励").toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.GREEN)
									.append("请输入 /renwu award 领取奖励").toString());
							break;
						}
						}
					else {
						player.sendMessage(new StringBuilder("").append(ChatColor.RED).append("不能再接受新的任务了").toString());
					}
					return true;
				}
				if (args[0].equalsIgnoreCase("today")) {
					player.sendMessage(
							new StringBuilder(DailyQuest.prefix).append(ChatColor.GREEN).append("今日任务").toString());
					player.sendMessage(new StringBuilder("    ").append(ChatColor.RED).append(
							"杀死 " + GlobalData.getStage1Limit() + " 只" + GlobalData.getStage1TypeName().toString())
							.toString());
					player.sendMessage(new StringBuilder("    ").append(ChatColor.RED).append(
							"挖 " + GlobalData.getStage2Limit() + " 个" + GlobalData.getStage2TypeName().toString())
							.toString());
					player.sendMessage(
							new StringBuilder("    ").append(ChatColor.RED).append("到达 10 个指定地点(在资源世界)").toString());

					return true;
				}
				if (args[0].equalsIgnoreCase("award")) {
					if (PlayerData.getStage(player) != 4) {
						player.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.RED)
								.append("你没有可领取的奖励").toString());
						return true;
					}
					PlayerData.sendReward(player);
					PlayerData.setStage(player, 0);
					return true;
				}

				sender.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.RED)
						.append("未知命令，请输入 /renwu 查看帮助").toString());
				return true;

			} else {
				player.sendMessage(
						new StringBuilder(DailyQuest.prefix).append(ChatColor.GREEN).append("帮助菜单").toString());
				sender.sendMessage(
						new StringBuilder().append(ChatColor.GREEN).append("/renwu accept 开始每日任务").toString());
				sender.sendMessage(
						new StringBuilder().append(ChatColor.GREEN).append("/renwu award 领取任务奖励").toString());
				sender.sendMessage(
						new StringBuilder().append(ChatColor.GREEN).append("/renwu info 查看你的任务信息").toString());
				sender.sendMessage(
						new StringBuilder().append(ChatColor.GREEN).append("/renwu today 查看今日任务信息").toString());
				return true;
			}
		}
		return false;
	}
}