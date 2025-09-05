package xraycheck;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

public class zapis{
	public String block;
	public String vremya;
	public int x;
	public int y;
	public int z;
	public int kolvoOre = 1;
	public String nickname;
	public zapis() {}
	public zapis(zapis other) {
		this.block = other.block;
		this.vremya = other.vremya;
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
		this.kolvoOre = other.kolvoOre;
		this.nickname = other.nickname;
	}



}
