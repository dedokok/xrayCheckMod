package xraycheck.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.text.Text;
import xraycheck.XrayCheck;
import xraycheck.methods.DoubleChestScreen;
import xraycheck.methods.ExampleGui;
import xraycheck.zapis;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SentMessage;
import net.minecraft.network.message.SignedMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import static java.lang.Math.abs;
import static xraycheck.XrayCheck.MOD_ID;
import org.spongepowered.asm.mixin.Shadow;
import java.util.function.Predicate;
import java.util.Iterator;



@Mixin(ClientPlayNetworkHandler.class)
public abstract class ExampleMixin {



//		@Unique
//		public int messages = 0;
//		@Unique
//		public long lastMessage = 0;
//
//		@Inject(method = "onGameMessage", at = @At(value = "HEAD"), cancellable = true)
//		private void onGameMessage(GameMessageS2CPacket packet, CallbackInfo ci) {
//			MinecraftClient client = MinecraftClient.getInstance();
//			client.player.sendMessage(Text.of("rabtaem"),false);
//
//
//
//		}
























//	private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
//	MinecraftClient client = MinecraftClient.getInstance();
//	@Shadow
//	@Mutable
//	@Final
//	private Set<PlayerListEntry> listedPlayerListEntries;
//
//	@Inject(method = "onGameMessage", at = @At("HEAD"), cancellable = true)
//	private void onGameMessage(GameMessageS2CPacket packet, CallbackInfo ci) {
//		LOGGER.info("rabota");
//		//return; - похуй, сообщение приходит
//
//		Text message = packet.content();
//		String plainText = message.getString();
//		client.player.sendMessage(Text.of("rabotta"),false);
//		//client.player.sendMessage(Text.of(message),false);
//
//		coreprotectStart(plainText);
//		//ci.cancel(); сообщение не приходит
//	}
//
//
//
//	//boolean chitat = false;
//
//	boolean zipos = false;
//	zapis zapis1 = new zapis();
//	int listik = 0;
//	void coreprotectStart(String plainText) {
//		final int[] kolvo2 = {XrayCheck.kolvo};
//		if (XrayCheck.chitat && listik <= XrayCheck.kolvo) {
//			if (plainText.length() > 1) {
//
//				if (plainText.indexOf("Lookup Results") > -1) {
//					zipos = true;
//				} else if (zipos) {
//					if (plainText.indexOf("broke") != -1) {
//
//						int nachBlock = plainText.indexOf("broke") + 6;
//						int konBlock = plainText.length() - 3;
//						int vrem = plainText.indexOf("/") + 1;
//						int nachNick = plainText.indexOf("§c- ") + 4;
//						int konNick = plainText.indexOf("§f broke");
//						if (plainText.indexOf("§m") != -1) {
//							nachBlock = plainText.indexOf("broke") + 8;
//							nachNick = plainText.indexOf("§c- ") + 6;
//							konNick = plainText.indexOf("§f§m broke");
//						}
//
//						zapis1.nickname = plainText.substring(nachNick, konNick);
//						zapis1.vremya = plainText.substring(0, vrem + 1);
//						zapis1.block = plainText.substring(nachBlock, konBlock);
//
//					} else if (plainText.indexOf("(") != -1 && plainText.indexOf("Page") == -1) {
//						try {
//							zapis1.x = Integer.parseInt(plainText.substring(plainText.indexOf("x") + 1, plainText.indexOf("y") - 1));
//							zapis1.y = Integer.parseInt(plainText.substring(plainText.indexOf("y") + 1, plainText.indexOf("z") - 1));
//							zapis1.z = Integer.parseInt(plainText.substring(plainText.indexOf("z") + 1, plainText.indexOf("world") - 1));
//							XrayCheck.zapisi.add(new zapis(zapis1));
//							LOGGER.info("добавил " + zapis1.block);
//						} catch (Exception e) {
//							XrayCheck.chitat = false;
//						}
//
//
//					}
//				}
//				if (plainText.indexOf("Page §f") != -1 && zipos && XrayCheck.zapisi.size() > 1) {
//
//					for (int i = 0; i < XrayCheck.zapisi.size(); i++) {
//						zapis zapis2 = XrayCheck.zapisi.get(i);
//						String stroka = zapis2.nickname + " " + zapis2.block + " " + zapis2.vremya + " " + zapis2.x + " " + zapis2.y + " " + zapis2.z;
//						LOGGER.info(stroka);
//					}
//					//zapisi.clear();
//					int nachKolvo;
//					int konKolvo = 0;
//					int nachSeych = 0;
//					int konSeych = 0;
//
//					if ((plainText.indexOf("▶") != -1 && plainText.indexOf("◀") == -1) || (plainText.indexOf("▶") != -1 && plainText.indexOf("◀") != -1)) {
//						konKolvo = plainText.indexOf("▶") - 1;
//					} else if (plainText.indexOf("▶") == -1 && plainText.indexOf("◀") != -1) {
//						konKolvo = plainText.indexOf("§7") - 1;
//					}
//					nachKolvo = plainText.indexOf("/") + 1;
//					konSeych = plainText.indexOf("/");
//					nachSeych = plainText.indexOf("Page §f") + 7;
//					String stroka = plainText.substring(nachKolvo, konKolvo) + " || " + nachKolvo + " " + konKolvo;
//					LOGGER.info(stroka);
//					int kolvoListov = 0;// = Integer.parseInt(plainText.substring(nachKolvo, konKolvo));
//					int listSeych = 0;// = Integer.parseInt(plainText.substring(nachSeych, konSeych));
//					try {
//						kolvoListov = Integer.parseInt(plainText.substring(nachKolvo, konKolvo));
//						listSeych = Integer.parseInt(plainText.substring(nachSeych, konSeych));
//					} catch (Exception e) {
//						XrayCheck.chitat = false;
//					}
//					client.player.sendMessage(Text.of("[" + kolvoListov + "] " + "[" + listSeych + "]"), false);
//					if (listSeych == XrayCheck.kolvo) {
//
//						checkVeins();
//						client.send(() -> client.setScreen(new DoubleChestScreen(new ExampleGui(XrayCheck.zapisi, 1))));
//						XrayCheck.chitat = false;
//					}
//
//
//					zipos = true;
//				}
//				if (plainText.indexOf("No results found for that") != -1) {
//
//					if (XrayCheck.zapisi.size() > 0) {
//						checkVeins();
//						client.send(() -> client.setScreen(new DoubleChestScreen(new ExampleGui(XrayCheck.zapisi, 1))));
//						XrayCheck.chitat = false;
//					}
//
//				}
//			}
//		}
//	}
//
//	void checkVeins(){
//		for(int i = XrayCheck.zapisi.size()-1;i>1;i--) {
//			boolean yesVein = false;
//			zapis blockSeych = XrayCheck.zapisi.get(i);
//			zapis blockBack = XrayCheck.zapisi.get(i-1);
//			if (blockSeych.block.equals("deepslate_diamond_ore") && blockBack.block.equals("deepslate_diamond_ore")) {
//				int bsX = blockSeych.x;
//				int bsY = blockSeych.y;
//				int bsZ = blockSeych.z;
//
//				int bBX = blockBack.x;
//				int bBY = blockBack.y;
//				int bBZ = blockBack.z;
//
//
//
//				if (abs(bsX - bBX) < 2 && abs(bsY - bBY) < 2 && abs(bsZ - bBZ) < 2) {
//					//yesVein = true;
//					zapis zapis = new zapis();
//					String block = blockBack.block;
//					zapis.block = block;
//					zapis.x = bBX;
//					zapis.y = bBY;
//					zapis.z = bBZ;
//					zapis.vremya = blockBack.vremya;
//					zapis.nickname = blockBack.nickname;
//					zapis.kolvoOre = blockBack.kolvoOre+1;
//
//					XrayCheck.zapisi.remove(i);
//					XrayCheck.zapisi.remove(i-1);
//					XrayCheck.zapisi.add(zapis);
//				}
//
//			}
//		}
//	}
}


