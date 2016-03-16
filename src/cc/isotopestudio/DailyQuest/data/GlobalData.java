package cc.isotopestudio.DailyQuest.data;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import cc.isotopestudio.DailyQuest.DailyQuest;

public class GlobalData {
	
	public GlobalData(DailyQuest plugin){
		
	}
	
	public static EntityType getStage1Type(){
		return null;
	}
	
	public static int getStage1Limit(){
		return 0;
	}
	
	public static Material getStage2Type(){
		return null;
	}

	public static int getStage2Limit(){
		return 0;
	}
	
	public static Location getStage3Location(int step){
		return null;
	}

}
