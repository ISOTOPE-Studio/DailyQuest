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
					sender.sendMessage(new StringBuilder(DailyQuest.prefix).append("�������ݿ�").toString());
					return true;
				}
				if (sender.isOp() && args[0].equalsIgnoreCase("fix")) {
					new CheckUpdate(plugin, true).runTaskLater(plugin, 10);
					sender.sendMessage(new StringBuilder(DailyQuest.prefix).append("�����޸�").toString());
					return true;
				}
			} catch (Exception e) {
			}
			if (!(sender instanceof Player)) {
				sender.sendMessage(
						new StringBuilder(DailyQuest.prefix).append(ChatColor.RED).append("����Ҫ��Ҳ���ִ��").toString());
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
								.append("�㲻���ٽ����µ�������").toString());
						return true;
					}
					if (PlayerData.getStage(player) >= 4) {
						player.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.BLUE)
								.append("��������ɣ�δ��ȡ����").toString());
						return true;
					}
					if (PlayerData.getStage(player) >= 1) {
						player.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.BLUE).append("�������ڽ���")
								.toString());
						return true;
					}
					PlayerData.setStage(player, 1);
					player.sendMessage(
							new StringBuilder(DailyQuest.prefix).append(ChatColor.YELLOW).append("�������ڿ�ʼ��").toString());
					player.sendMessage(new StringBuilder("    ").append(ChatColor.BLUE).append("������е���һ�׶�").toString());
					player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
							.append("ɱ�� " + GlobalData.getStage1Limit() + " ֻ" + GlobalData.getStage1TypeName())
							.toString());
					return true;
				}
				if (args[0].equalsIgnoreCase("info")) {
					player.sendMessage(
							new StringBuilder(DailyQuest.prefix).append(ChatColor.GREEN).append("������Ϣ").toString());
					player.sendMessage(
							new StringBuilder().append(ChatColor.YELLOW)
									.append("�������������" + PlayerData.getTimes(player) + "�Σ���������"
											+ (PlayerData.getLimit(player) - PlayerData.getTimes(player)) + "��")
							.toString());
					if (PlayerData.canAccept(player))
						switch (PlayerData.getStage(player)) {
						case (0): {
							player.sendMessage(
									new StringBuilder("    ").append(ChatColor.BLUE).append("����δ��ʼ").toString());
							break;
						}
						case (1): {
							player.sendMessage(
									new StringBuilder("    ").append(ChatColor.BLUE).append("������е���һ�׶�").toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
									.append("ɱ�� " + GlobalData.getStage1Limit() + " ֻ" + GlobalData.getStage1TypeName())
									.toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
									.append("������� " + PlayerData.getStep(player) + " ֻ").toString());
							break;
						}
						case (2): {
							player.sendMessage(
									new StringBuilder("    ").append(ChatColor.BLUE).append("������е��ڶ��׶�").toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
									.append("�� " + GlobalData.getStage2Limit() + " ��" + GlobalData.getStage2TypeName())
									.toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
									.append("������� " + PlayerData.getStep(player) + " ��").toString());
							break;
						}
						case (3): {
							Location loc = GlobalData.getStage3Location(PlayerData.getStep(player));
							player.sendMessage(
									new StringBuilder("    ").append(ChatColor.BLUE).append("������е������׶�").toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
									.append("���� 10 ��ָ���ص�(����Դ����)").toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
									.append("������� " + PlayerData.getStep(player) + " ��").toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
									.append("��һ���ص㣺X " + loc.getBlockX() + " Z " + loc.getBlockZ()).toString());
							PlayerData.sendDirection(player, plugin);
							break;
						}
						case (4): {
							player.sendMessage(
									new StringBuilder("    ").append(ChatColor.BLUE).append("��������ɣ�δ��ȡ����").toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.GREEN)
									.append("������ /renwu award ��ȡ����").toString());
							break;
						}
						}
					else {
						player.sendMessage(new StringBuilder("").append(ChatColor.RED).append("�����ٽ����µ�������").toString());
					}
					return true;
				}
				if (args[0].equalsIgnoreCase("today")) {
					player.sendMessage(
							new StringBuilder(DailyQuest.prefix).append(ChatColor.GREEN).append("��������").toString());
					player.sendMessage(new StringBuilder("    ").append(ChatColor.RED).append(
							"ɱ�� " + GlobalData.getStage1Limit() + " ֻ" + GlobalData.getStage1TypeName().toString())
							.toString());
					player.sendMessage(new StringBuilder("    ").append(ChatColor.RED).append(
							"�� " + GlobalData.getStage2Limit() + " ��" + GlobalData.getStage2TypeName().toString())
							.toString());
					player.sendMessage(
							new StringBuilder("    ").append(ChatColor.RED).append("���� 10 ��ָ���ص�(����Դ����)").toString());

					return true;
				}
				if (args[0].equalsIgnoreCase("award")) {
					if (PlayerData.getStage(player) != 4) {
						player.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.RED)
								.append("��û�п���ȡ�Ľ���").toString());
						return true;
					}
					PlayerData.sendReward(player);
					PlayerData.setStage(player, 0);
					return true;
				}

				sender.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.RED)
						.append("δ֪��������� /renwu �鿴����").toString());
				return true;

			} else {
				player.sendMessage(
						new StringBuilder(DailyQuest.prefix).append(ChatColor.GREEN).append("�����˵�").toString());
				sender.sendMessage(
						new StringBuilder().append(ChatColor.GREEN).append("/renwu accept ��ʼÿ������").toString());
				sender.sendMessage(
						new StringBuilder().append(ChatColor.GREEN).append("/renwu award ��ȡ������").toString());
				sender.sendMessage(
						new StringBuilder().append(ChatColor.GREEN).append("/renwu info �鿴���������Ϣ").toString());
				sender.sendMessage(
						new StringBuilder().append(ChatColor.GREEN).append("/renwu today �鿴����������Ϣ").toString());
				return true;
			}
		}
		return false;
	}
}