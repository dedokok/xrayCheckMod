package xraycheck;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.apache.commons.lang3.SerializationUtils;
import java.io.Serializable;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents.AllowGame;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xraycheck.methods.GetChat;
import xraycheck.methods.DoubleChestScreen;
import net.fabricmc.fabric.api.client.command.v2.*;
import xraycheck.methods.ExampleGui;
import xraycheck.methods.ProfileGUI;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.lang.Math.abs;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static xraycheck.methods.ExampleGui.zapisNext;

@Environment(EnvType. CLIENT)
public class XrayCheck implements ClientModInitializer {
	public static final String MOD_ID = "xraycheck";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static boolean chitat = false;
	public static int kolvo = 1;
	String nickname;
	static List<zapis> zapisi = new ArrayList<>();
	public static List<zapis> zapisiProfile = new ArrayList<>();
	public static Boolean isNotProfile = true;
	public static String nickProfile = "";

	MinecraftClient client = MinecraftClient.getInstance();
	boolean receiveMessages = true;
	int kolvoListov = 0;
	int listSeych = 0;
	int commandG = 0;
	int kolvoGetCounts = 0;
	public XrayCheck() {
	}
	@Override
	public void onInitializeClient() {
		register(receiveMessages);
		GetChat.register();
		LOGGER.info("Мод XrayCheck успешно загружен!");
		ArrayList<String> stroki = new ArrayList<>();
		MyTimer.ClientTimer.init();



		//команда /xraycheck
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(
					ClientCommandManager.literal("xraycheck")
							.executes(context -> {
								List<zapis> zapisi_copy = sortZapisi(zapisi);
								//List<zapis> zapisiProfile_copy = sortZapisi(zapisiProfile);
								if(isNotProfile) {
									client.send(() -> client.setScreen(new DoubleChestScreen(new ExampleGui(zapisi_copy, 1, -1, -1, -1))));
								}
								else{
									client.player.sendMessage(Text.of(String.valueOf(zapisiProfile.size())),false);
									client.send(() -> client.setScreen(new DoubleChestScreen(new ProfileGUI(zapisi,1))));
								}
								return 1;
							})
							.then(argument("kolvo", IntegerArgumentType.integer())
									.executes(context -> {
										kolvo = IntegerArgumentType.getInteger(context, "kolvo");

										chitat = true;
										zapisi.clear();
										zapisiProfile.clear();

										ClientPlayNetworkHandler networkHandler = client.getNetworkHandler();
										String command = "co lookup action: -block include: deepslate_diamond_ore time: 10w";
										networkHandler.sendChatCommand(command);
										//for (int i = 2; i <= kolvo; i++) {
										//sendCommandsSequentially(2);
										//}
										return 1;
									})

									.then(argument("nickname", StringArgumentType.string())
											.suggests((context, builder) -> {

												if (client.world != null) {
													for (PlayerEntity player : client.world.getPlayers()) {
														builder.suggest(player.getName().getString());
													}
												}
												return CompletableFuture.completedFuture(builder.build());
											})
											.executes(context -> {
												kolvo = IntegerArgumentType.getInteger(context, "kolvo");
												nickname = StringArgumentType.getString(context, "nickname");
												chitat = true;
												zapisi.clear();
												zapisiProfile.clear();
												ClientPlayNetworkHandler networkHandler = client.getNetworkHandler();
												String command = "co lookup action: -block include: deepslate_diamond_ore " + " user: " + nickname + " time: 10w";
												networkHandler.sendChatCommand(command);
												//int delay = 20;
												//sendCommandsSequentially(2);
												return 1;
											})
									)
							)

			);
		});

		//команда /xcmenu
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(
					ClientCommandManager.literal("xcmenu")
							.executes(context -> {
								List<zapis> zapisi_copy = sortZapisi(zapisi);
								if(isNotProfile) {
									client.send(() -> client.setScreen(new DoubleChestScreen(new ExampleGui(zapisi_copy, 1, -1, -1, -1))));
								}
								else{
									client.player.sendMessage(Text.of(String.valueOf(zapisiProfile.size())),false);
									client.send(() -> client.setScreen(new DoubleChestScreen(new ProfileGUI(zapisi,1))));
								}
								return 1;
							})
			);
		});
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(
					ClientCommandManager.literal("xraycheck")
							.then(argument("next", StringArgumentType.string())
									.executes(context -> {
										String mess = StringArgumentType.getString(context, "next");
										if(mess.equals("next")){
											List<zapis>zapisi_copy = sortZapisi(zapisi);
											List<zapis>zapisiProfile_copy = sortZapisi(zapisiProfile);
											if(isNotProfile) {

												if (zapisNext + 1 < zapisi_copy.size()) {
													zapis block = zapisi_copy.get(zapisNext + 1);

													Double blockX = (double) block.x;
													if (blockX < 0) {
														blockX -= 0.5;
													} else {
														blockX += 0.5;
													}
													Double blockY = (double) block.y;
													Double blockZ = (double) block.z;
													if (blockZ < 0) {
														blockZ -= 0.5;
													} else {
														blockZ += 0.5;
													}
													String command = "co teleport " + blockX + " " + blockY + " " + blockZ;
													ClientPlayNetworkHandler networkHandler = client.getNetworkHandler();

													networkHandler.sendChatCommand(command);
													zapisNext++;
												} else {
													client.player.sendMessage(Text.of("записи закончились"), false);

												}
											}
											else{
												if (zapisNext + 1 < zapisiProfile_copy.size()) {
													zapis block = zapisiProfile_copy.get(zapisNext + 1);

													Double blockX = (double) block.x;
													if (blockX < 0) {
														blockX -= 0.5;
													} else {
														blockX += 0.5;
													}
													Double blockY = (double) block.y;
													Double blockZ = (double) block.z;
													if (blockZ < 0) {
														blockZ -= 0.5;
													} else {
														blockZ += 0.5;
													}
													String command = "co teleport " + blockX + " " + blockY + " " + blockZ;
													ClientPlayNetworkHandler networkHandler = client.getNetworkHandler();

													networkHandler.sendChatCommand(command);
													zapisNext++;
												} else {
													client.player.sendMessage(Text.of("записи закончились"), false);
												}
											}

										}
										return 1;
									})
							)
					);
		}
		);
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
					dispatcher.register(
							ClientCommandManager.literal("xraycheck")
									.then(argument("back", StringArgumentType.string())
											.executes(context -> {
												String mess = StringArgumentType.getString(context, "back");
												if(mess.equals("back")){
													List<zapis>zapisi_copy = sortZapisi(zapisi);

													if(zapisNext+1<zapisi_copy.size()) {
														zapis block = zapisi_copy.get(zapisNext + 1);

														Double blockX = (double) block.x;
														if(blockX<0){blockX-=0.5;}
														else{blockX+=0.5;}
														Double blockY = (double) block.y;
														Double blockZ = (double) block.z;
														if(blockZ<0){blockZ-=0.5;}
														else{blockZ+=0.5;}
														String command = "co teleport " + blockX + blockY + blockZ;
														ClientPlayNetworkHandler networkHandler = client.getNetworkHandler();

														networkHandler.sendChatCommand(command);
														zapisNext--;
													}
													else{
														client.player.sendMessage(Text.of("записи закончились"),false);

													}

												}
												return 1;
											})
									)
					);
				}
		);


	}


	public void coreprotectStart(String plainText) {
		final int[] kolvo2 = {kolvo};
		if (chitat && listik <= kolvo) {
			if (plainText.length() > 1) {

				if (plainText.indexOf("Lookup Results") > -1) {
					//kolvoGetMessages++;
					zipos = true;
				} else if (zipos) {
					if (plainText.indexOf("broke") != -1) {


						int nachBlock = plainText.indexOf("broke") + 6;
						int konBlock = plainText.length() - 3;
						int vrem = plainText.indexOf("/") + 1;
						int nachNick = plainText.indexOf("§c- ") + 4;
						int konNick = plainText.indexOf("§f broke");
						if (plainText.indexOf("§m") != -1) {
							nachBlock = plainText.indexOf("broke") + 8;
							nachNick = plainText.indexOf("§c- ") + 6;
							konNick = plainText.indexOf("§f§m broke");
						}

						zapis1.nickname = plainText.substring(nachNick, konNick);
						zapis1.vremya = plainText.substring(0, vrem + 1);
						zapis1.block = plainText.substring(nachBlock, konBlock);

					} else if (plainText.indexOf("(") != -1 && plainText.indexOf("Page") == -1) {
						try {
							zapis1.x = Integer.parseInt(plainText.substring(plainText.indexOf("x") + 1, plainText.indexOf("y") - 1));
							zapis1.y = Integer.parseInt(plainText.substring(plainText.indexOf("y") + 1, plainText.indexOf("z") - 1));
							zapis1.z = Integer.parseInt(plainText.substring(plainText.indexOf("z") + 1, plainText.indexOf("world") - 1));
							zapisi.add(new zapis(zapis1));
							//LOGGER.info("добавил " + zapis1.block);
						} catch (Exception e) {
							chitat = false;
						}


					}
				}
				if (plainText.indexOf("Page §f") != -1 && zipos && zapisi.size() > 1) {

					for (int i = 0; i < zapisi.size(); i++) {
						zapis zapis2 = zapisi.get(i);
						String stroka = zapis2.nickname + " " + zapis2.block + " " + zapis2.vremya + " " + zapis2.x + " " + zapis2.y + " " + zapis2.z;
						//LOGGER.info(stroka);
					}
					//zapisi.clear();
					int nachKolvo;
					int konKolvo = 0;
					int nachSeych = 0;
					int konSeych = 0;

					if ((plainText.indexOf("▶") != -1 && plainText.indexOf("◀") == -1) || (plainText.indexOf("▶") != -1 && plainText.indexOf("◀") != -1)) {
						konKolvo = plainText.indexOf("▶") - 1;
					} else if (plainText.indexOf("▶") == -1 && plainText.indexOf("◀") != -1) {
						konKolvo = plainText.indexOf("§7") - 1;
					}
					nachKolvo = plainText.indexOf("/") + 1;
					konSeych = plainText.indexOf("/");
					nachSeych = plainText.indexOf("Page §f") + 7;

					String stroka = plainText.substring(nachKolvo, konKolvo) + " || " + nachKolvo + " " + konKolvo;
					//LOGGER.info(stroka);
					int kolvoListov = 0;// = Integer.parseInt(plainText.substring(nachKolvo, konKolvo));
					int listSeych = 0;// = Integer.parseInt(plainText.substring(nachSeych, konSeych));
					try {
						kolvoListov = Integer.parseInt(plainText.substring(nachKolvo, konKolvo));

						listSeych = Integer.parseInt(plainText.substring(nachSeych, konSeych));
					} catch (Exception e) {

					}
					if(kolvoListov<kolvo){
						kolvo=kolvoListov;
					}
					//client.player.sendMessage(Text.of("[" + kolvoListov + "] " + "[" + listSeych + "]"), false);
					if (listSeych == kolvo) {
						//chitat=false;
						checkVeins();
						List<zapis> zapisi_copy = sortZapisi(zapisi);
						client.send(() -> client.setScreen(new DoubleChestScreen(new ExampleGui(zapisi_copy, 1,-1,-1,-1))));

					}


					zipos = true;
				}
				if (plainText.indexOf("No results found for that") != -1) {

					if (zapisi.size() > 0) {
						checkVeins();
						//kolvo=listSeych-1;
						List<zapis> zapisi_copy = sortZapisi(zapisi);
						client.send(() -> client.setScreen(new DoubleChestScreen(new ExampleGui(zapisi_copy, 1,-1,-1,-1))));

					}

				}
			}
		}
	}

	int isUbratPosledneeSoob = 0;
	boolean zipos = false;
	zapis zapis1 = new zapis();
	int listik = 0;


	//ивент для корпртекта
	public void register(boolean doRec) {

		ClientReceiveMessageEvents.ALLOW_GAME.register((message, overlay) -> {
			String plainText = message.getString();
			//LOGGER.info(String.valueOf(message));

			boolean isTrue = true;
			//if(funcForChar(plaintText)){return
			if(plainText.contains("Lookup searching")){
				kolvoGetCounts++;
			}
			coreprotectStart(plainText);
			isTrue = funcForChat(plainText);
			return isTrue;
		});
		//PacketSender sender =
		//chitat=true
		ClientPlayConnectionEvents.JOIN.register((handler, sender, client)->{
			if(chitat==true){chitat = false;}
			if(isUbratPosledneeSoob>0){isUbratPosledneeSoob=0;}
		});
	}

	boolean funcForChat(String message) {


		if(chitat&&kolvoGetCounts<kolvo && message.contains("Page")){

			//kolvoGetCounts++;
			//LOGGER.info(String.valueOf(kolvoGetCounts));
			if(kolvoGetCounts>0) {
				sendCommandsSequentially(kolvoGetCounts + 1);
			}

			return false;
		}
		else if (chitat&&kolvoGetCounts==kolvo && message.contains("Page")) {
			//chitat=false;
			checkVeins();
			kolvo=0;
			kolvoGetCounts=0;
			List<zapis> zapisi_copy = sortZapisi(zapisi);
			client.send(() -> client.setScreen(new DoubleChestScreen(new ExampleGui(zapisi_copy, 1,-1,-1,-1))));
			return false;
		}
		else if(chitat && kolvoGetCounts>kolvo && (message.contains("Page"))){
			//kolvoGetCounts=0;
			kolvo = 0;
			kolvoGetCounts=0;
			chitat=false;
			return false;
		}
		else if(chitat && message.contains("No results found for that page.")){
			kolvo=0;
			kolvoGetCounts=0;
			chitat=false;
			return false;
		}
		else if(kolvoGetCounts>kolvo){
			isUbratPosledneeSoob=0;
			kolvoGetCounts=0;
			kolvo=0;
			chitat=false;
			return false;
		}
		if(isUbratPosledneeSoob==1){
			isUbratPosledneeSoob--;
			return false;
		}



		if(chitat && (message.contains("Lookup searching. Please wait...") || message.contains("§f") || message.contains("CoreProtect |  Lookup Results"))) {
			//isUbratPosledneeSoob = 1;
			return false;
		}
		if(chitat &&message.contains("Page") && kolvoListov==listSeych){
			return false;
		}
			if(chitat && message.contains("No results found for that page.")){
				kolvo=0;
				kolvoGetCounts=0;
				chitat=false;
				//isUbratPosledneeSoob = 1;
				return false;
			}
//		if (isUbratPosledneeSoob >0 || chitat && (message.indexOf("§f") != -1 || message.contains("CoreProtect"))) {
//			//LOGGER.info("obema2");
//			isUbratPosledneeSoob--;
//			return false;
//		}
		return true;
	}

	public void sendCommandsSequentially(int current) {
		MyTimer.ClientTimer.setTimer(4, () -> {
			ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();
			if (networkHandler != null) {
				String command = "co l " + current;
				networkHandler.sendChatCommand(command);
			}
		});
	}

	int kolvoRud = 0;
	void checkVeins() {

		for (int i = zapisi.size() - 1; i > 0; i--) {////////////
			zapis blockSeych = zapisi.get(i);
			zapis blockBack = zapisi.get(i - 1);
			if (blockSeych.block.equals("deepslate_diamond_ore") && blockBack.block.equals("deepslate_diamond_ore")
			&& blockBack.nickname.equals(blockSeych.nickname)
			) {
				int bsX = blockSeych.x;
				int bsY = blockSeych.y;
				int bsZ = blockSeych.z;

				int bBX = blockBack.x;
				int bBY = blockBack.y;
				int bBZ = blockBack.z;


				if (abs(bsX - bBX) < 2 && abs(bsY - bBY) < 2 && abs(bsZ - bBZ) < 2) {
					//yesVein = true;
					zapis zapis = new zapis();
					String block = blockBack.block;
					zapis.block = block;
					zapis.x = bBX;
					zapis.y = bBY;
					zapis.z = bBZ;
					zapis.vremya = blockBack.vremya;
					zapis.nickname = blockBack.nickname;
					zapis.kolvoOre = blockSeych.kolvoOre + 1;
					//kolvoRud++;
					//LOGGER.info(String.valueOf(kolvoRud));
					zapisi.remove(i);
					//zapisi.remove(i - 1);
					zapisi.set(i-1,zapis);
				}

			}
			else if(blockSeych.block.equals("deepslate_diamond_ore")){

				if(zapisi.get(i).kolvoOre==0){
					zapisi.get(i).kolvoOre = 1;
				}







			}
		}
	}

	static List<zapis> sortZapisi(List<zapis> zapisi){
		for(int i = 0; i<zapisi.size();i++){
			zapis zapis_1 = zapisi.get(i);
			for(int j=0; j<zapisi.size();j++){
				zapis zapis_2 = zapisi.get(j);
				if(getTime(zapis_1.vremya)<getTime(zapis_2.vremya)){
					zapis temp = zapisi.get(j);
					zapisi.set(j,zapisi.get(i));
					zapisi.set(i,temp);
				}
			}
		}
		return zapisi;
	}

	static float getTime(String vremya){
		float time = 0;
		float vremya_get = Float.parseFloat(vremya.substring(0,vremya.indexOf("/")));
		String vType = vremya.substring(vremya.indexOf("/")+1,vremya.indexOf("/")+2);
		if(vType.equals("m")){time=vremya_get*60;}
		if(vType.equals("h")){time=vremya_get*60*60;}
		if(vType.equals("d")){time=vremya_get*60*60*24;}
		if(vType.equals("w")){time=vremya_get*60*60*24*7;}
		return time;
	}


}




