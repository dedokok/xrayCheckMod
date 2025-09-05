package xraycheck.methods;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;


import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.realms.dto.RealmsNotification;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xraycheck.zapis;


import java.util.List;

import static xraycheck.XrayCheck.*;
import static xraycheck.methods.ExampleGui.inventar;

public class ProfileGUI extends LightweightGuiDescription{
    WGridPanel root = new WGridPanel();
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    MinecraftClient client = MinecraftClient.getInstance();
    ClientPlayNetworkHandler networkHandler = client.getNetworkHandler();
    int g = 1;
    int indexSlot=1;
    List<String>blackList = null;
    int page;
    boolean canRClick = true;

    public ProfileGUI(List<zapis> zapisi, int pageBila) {
        page = 1;


        setRootPanel(root);


        int kolvoZapis = zapisi.size();

        int countOfPages = ExampleGui.countPages(kolvoZapis);


        Text textForLabel = Text.of("Page " + page + "/" + countOfPages);
        WLabel label = new WLabel(textForLabel, 0xFFFFFF);
        g=(page-1)*60+1;
        root.setSize(300, 200);
        int xSlot = 3;
        int ySlot =2;
        int xMax = 13;
        int xMin = 3;
        int yMax = 7;
        int yMin = 2;
        boolean isTrue = true;
        zapisiProfile.clear();
            for (int i = 1; i <= zapisi.size(); i++) {
                if (zapisi.get(i - 1).block.equals("deepslate_diamond_ore") && zapisi.get(i - 1).nickname.equals(nickProfile)) {

                    zapisiProfile.add(zapisi.get(i - 1));
                }
            }

        for (int i = yMin; i < yMax; i++) {
            for (int j = xMin; j < xMax; j++) {
                if(xSlot==xMax){
                    xSlot=xMin;
                    ySlot++;
                }
                if(ySlot==yMax){
                    ySlot=yMin;
                }



                //WItemSlot itemSlot = funcForSlot(zapisi, g, nickProfile);

                // LOGGER.info("проверка 2");


                WItemSlot itemSlot = ExampleGui.funcForSlot(zapisiProfile,g,-1,page,nickProfile);
                    if (g - 1 < zapisiProfile.size()) {
                        if (zapisiProfile.get(g - 1).block.equals("deepslate_diamond_ore")) {
                            root.add(itemSlot, xSlot, ySlot);
                            isTrue=false;
                        }
                        else{g++;

                        }
                    } else {
                        WItemSlot itemSlot2 = WItemSlot.of(inventar, 0);
                        root.add(itemSlot2, xSlot, ySlot);
                    }

                xSlot++;

                g++;
            }
        }

        WButton buttonLeft = new WButton(Text.of("Back")){
            @Override
            public InputResult onClick(int x, int y, int button) {
                // LOGGER.info("проверка 3");
                if(page>1){
                    indexSlot -=60;
                    page--;
                    Text textForLabel = Text.of("Page " + page + "/" + countOfPages);
                    label.setText(textForLabel);

                    client.send(() -> client.setScreen(new DoubleChestScreen(new ProfileGUI(zapisiProfile,page))));
                }

                return super.onClick(x, y, button);
            }
        };
        root.add(buttonLeft, 1, 8, 3, 2);


        WButton buttonRight = new WButton(Text.of("Next")){
            @Override
            public InputResult onClick(int x, int y, int button) {
                //   LOGGER.info("проверка 4");
                if(page<countOfPages) {
                    page++;
                    indexSlot +=60;
                    //Text textForLabel = Text.of("Page " + page + "/" + countOfPages);
                    //label.setText(textForLabel);
                    client.send(() -> client.setScreen(new DoubleChestScreen(new ProfileGUI(zapisiProfile,page))));

                }
                return super.onClick(x, y, button);
            }
        };
        root.add(buttonRight, 12, 8, 3, 2);
        WButton buttonBack = new WButton(Text.of("Back")){
            @Override
            public InputResult onClick(int x, int y, int button) {
                isNotProfile=true;
                client.send(() -> client.setScreen(new DoubleChestScreen(new ExampleGui(zapisi,pageBila,-1,-1,-1))));
                return super.onClick(x, y, button);
            }
        };
        root.add(buttonBack, 1, 1, 2, 1);
        root.add(label, 6, 1, 2, 1);










    }
//    WItemSlot funcForSlot(List<zapis> zapisi, int g, String nickProfile){
//        WItemSlot itemSlot = new WItemSlot(inventar, g,1,1,false){
//
//
//            @Override
//            public void addTooltip(TooltipBuilder tooltip) {
//                // LOGGER.info("проверка 5");
//
//                if (g <= zapisi.size()) {
//                    if (g <= zapisi.size()) {
//                        zapis block = zapisi.get(g-1);
//                        String blockX = String.valueOf(block.x);
//                        String blockY = String.valueOf(block.y);
//                        String blockZ = String.valueOf(block.z);
//                        String blockKolvoOre = String.valueOf(block.kolvoOre);
//
//                        if (block.block.equals("deepslate_diamond_ore") && block.nickname.equals(nickProfile)) {
//                            tooltip.add(Text.of(block.vremya + " " + block.nickname));
//                            tooltip.add(Text.of(blockX + " " + blockY + " " + blockZ));
//                            tooltip.add(Text.of(blockKolvoOre));
//                        }
//                    }
//                    super.addTooltip(tooltip);
//                }
//            }
//            @Override
//            public InputResult onClick(int x, int y, int button){
//                //LOGGER.info(String.valueOf(key) + " " + String.valueOf(ch) + " " + String.valueOf(modifiers));
//
//                //if(key==1) {
//                if (g <= zapisi.size()) {
//                    zapis block = zapisi.get(g - 1);
//                    String blockX = String.valueOf(block.x);
//                    String blockY = String.valueOf(block.y);
//                    String blockZ = String.valueOf(block.z);
//                    String userNickname = client.getSession().getUsername();
//                    String command = "co teleport " + blockX + ".5 " + blockY + ".5 " + blockZ + ".5";
//                    networkHandler.sendChatCommand(command);
//                }
//                return super.onClick(x,y,button);
//
//            }
//
//        };
//        if(g<=zapisi.size()){
//
//            if(zapisi.get(g-1).block.equals("deepslate_diamond_ore")&&zapisi.get(g-1).nickname.equals(nickProfile)){
//                itemSlot.setIcon(new ItemIcon(new ItemStack(Items.DEEPSLATE_DIAMOND_ORE)));
//            }
//        }
//        return itemSlot;
//    }
}
