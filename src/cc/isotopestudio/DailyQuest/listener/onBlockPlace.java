package cc.isotopestudio.DailyQuest.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import cc.isotopestudio.DailyQuest.DailyQuest;

public class onBlockPlace {
	private final DailyQuest plugin;

	public onBlockPlace(DailyQuest plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent event) {

	}

	@EventHandler
	public void onPlaceBreak(BlockBreakEvent event) {

	}
}
