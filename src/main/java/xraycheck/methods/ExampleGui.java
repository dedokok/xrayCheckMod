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


import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static xraycheck.XrayCheck.*;


public class ExampleGui extends LightweightGuiDescription {
    WGridPanel root = new WGridPanel();
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static int zapisNext = 0;
    int kolvoVskopanihRuf = 0;
    static MinecraftClient client = MinecraftClient.getInstance();
    static ClientPlayNetworkHandler networkHandler = client.getNetworkHandler();
    int g = 1;
    int g2 = 1;
    int indexSlot=1;
    public static List<String>blackList = new ArrayList<>();
    int page;
    boolean canRClick = true;
    public ExampleGui(List<zapis> zapisi, int page1, int xFSlot, int yFSlot,int slotClick) {

       // LOGGER.info("проверка 1");
        //zapisi = sortZapisi(zapisi);
        page = page1;

        setRootPanel(root);


        int kolvoZapis = zapisi.size();

        int countOfPages = countPages(kolvoZapis);
        if(page>countOfPages){
            page=countOfPages;
        }


        Text textForLabel = Text.of("Page " + page + "/" + countOfPages);
        WLabel label = new WLabel(textForLabel, 0xFFFFFF);
        Boolean isNotFirstSlotClick = true;
        g=(page-1)*60+1;




        root.setSize(300, 200);
        int xSlot = 3;
        int ySlot =2;
        int xMax = 13;
        int xMin = 3;
        int yMax = 7;
        int yMin = 2;



        //for (int i = yMin; i < yMax; i++) {
            //for (int j = xMin; j < xMax; j++) {
        int dopG = 0;
        int kolvoG = 51;
        Boolean isWhile = true;

        int findSlotClick=-2;
        int xSlotClick = xMin;
        int ySlotClick=yMin;
//        if(slotClick!=-1){
//            findSlotClick=1;
//            for(int i = yMin;i<yMax;i++){
//                for(int j = xMin; j<xMax;j++){
//
//                    if(xSlotClick==xMax){
//                        xSlotClick=xMin;
//                        ySlotClick++;
//                    }
//                    if(ySlotClick==yMax){
//                        ySlotClick=yMin;
//                    }
//
//                    if(findSlotClick==slotClick){
//                        xSlotClick=j;
//                        ySlotClick=i;
//                        break;
//                    }
//                    else{
//                        xSlotClick++;
//                        findSlotClick++;
//                    }
//                }
//            }
//        }
//        if(xSlot==xMin-1 && slotClick==-1){
//            xSlot++;
//        }
        while(g<=kolvoG+dopG+1){
            if (xSlot == xMax) {
                xSlot = xMin;
                ySlot++;
            }
            if (ySlot == yMax) {
                ySlot = yMin;
            }


            boolean isLKMSlotClick = false;
            boolean isPKMSlotProfile = false;
            boolean isPKMSlotUbrat = false;

            boolean isTrue = true;




            //j - x, i = y
            if (slotClick == g) {
                isLKMSlotClick = true;
            }



            if (slotClick == g + 1) {
                isPKMSlotProfile = true;

            }
            if (slotClick == g - 1) {
                isPKMSlotUbrat = true;
            }


            if (!isLKMSlotClick && !isPKMSlotProfile && !isPKMSlotUbrat && xSlot>=xMin) {
                if (g != slotClick - 1 && g != slotClick && g != slotClick + 1) {
                    if(g<=zapisi.size()) {
                        if (blackList.contains(zapisi.get(g - 1).nickname)) {
                            //g++;
                            //dopG++;
                            while (g <= zapisi.size()) {
                                if (blackList.contains(zapisi.get(g - 1).nickname)) {
                                    g++;
                                    dopG++;
                                } else {
                                    WItemSlot itemSlot = funcForSlot(zapisi, g, slotClick, page1, "-");
                                    root.add(itemSlot, xSlot, ySlot);
                                    xSlot++;
                                    dopG++;

                                    break;
                                }
                            }

                        } else {
                            WItemSlot itemSlot = funcForSlot(zapisi, g, slotClick, page1, "-");
                            root.add(itemSlot, xSlot, ySlot);
                            xSlot++;
                            if(dopG>0){
                                dopG--;
                            }

                        }
                    }
                    else{

                        //g = zapisi.size();
                        //WItemSlot itemSlot = funcForSlot(zapisi,g,-1,page1,"-");
                        if(xSlot==xMin && ySlot==yMin && g>10){
                            break;
                        }
                        WItemSlot itemSlot = WItemSlot.of(inventar,0);
                        root.add(itemSlot,xSlot,ySlot);

                        xSlot++;
                    }

//                        for (int bL = 0; bL < blackList.size(); bL++) {
//                            if (g - 1 < zapisi.size() && zapisi.get(g - 1).nickname.equals(blackList.get(bL))) {
//                                isTrue=true;
//                                while(isTrue) {
//
//                                    if (g - 1 < zapisi.size()) {
//                                        Boolean isNotBlackList = true;
//
//                                        for (int bL2 = 0; bL2 < blackList.size(); bL2++) {
//                                            if (g - 1 < zapisi.size() && zapisi.get(g - 1).nickname.equals(blackList.get(bL2))) {
//                                                isNotBlackList=false;
//                                                g++;
//                                            }
//                                            else{isTrue=false;
//                                            isNotBlackList=true;}
//
//                                        }
//                                        if(isNotBlackList==true){
//                                            isTrue=false;
//                                        }
//                                        //else{g++;}
//                                    }
//                                    else{isTrue=false;}
//                                }
//                            }
//                            //else{g++;}
//                        }


//                    WItemSlot itemSlot = funcForSlot(zapisi, g, slotClick, page1, "-");
//                    root.add(itemSlot, xSlot, ySlot);
//                    xSlot++;
                    //}
//                        else
//                        {
//                            WItemSlot itemSlot = WItemSlot.of(inventar,0);
//                            root.add(itemSlot, xSlot, ySlot);
//                            xSlot++;
//                        }
                }
            } else if (isLKMSlotClick) {


                //СЛОТ, НА КОТОРЫЙ КЛИКНУЛ
                int finalSlotClick = slotClick;
                WItemSlot itemSlot2 = new WItemSlot(inventar, finalSlotClick, 1, 1, false) {
                    @Override
                    public InputResult onClick(int x, int y, int button) {

                        client.send(() -> client.setScreen(new DoubleChestScreen(new ExampleGui(zapisi, page, -1, -1, -1))));
                        return super.onClick(x, y, button);
                    }

                    @Override
                    public void addTooltip(TooltipBuilder tooltip) {
                        String playerNick = zapisi.get(finalSlotClick - 1).nickname;
                        tooltip.add(Text.of("Развыделить"));
                        super.addTooltip(tooltip);
                    }
                };
                itemSlot2.setIcon(new ItemIcon(new ItemStack(Items.LAPIS_BLOCK)));
                isLKMSlotClick = false;

                root.add(itemSlot2, xSlot, ySlot);

                if(slotClick==1){



                    WItemSlot itemSlot3 = new WItemSlot(inventar, finalSlotClick, 1, 1, false) {
                        @Override
                        public InputResult onClick(int x, int y, int button) {
                            isNotProfile=false;
                            nickProfile = zapisi.get(finalSlotClick - 1).nickname;
                            client.send(() -> client.setScreen(new DoubleChestScreen(new ProfileGUI(zapisi, page))));//////////////////////////
                            return super.onClick(x, y, button);
                        }

                        @Override
                        public void addTooltip(TooltipBuilder tooltip) {
                            String playerNick = zapisi.get(finalSlotClick).nickname;//////////////////////////////////
                            tooltip.add(Text.of("Открыть профиль " + playerNick));
                            super.addTooltip(tooltip);
                        }
                    };
                    itemSlot3.setIcon(new ItemIcon(new ItemStack(Items.PLAYER_HEAD)));


                    //else if(xSlot==xMin)


                        root.add(itemSlot3, xSlot-1, ySlot);


                }

                xSlot++;
            }

            //СЛОТ ДЛЯ ОТКРЫТИЯ ПРОФИЛЯ
            else if (isPKMSlotProfile) {

                int finalSlotClick = slotClick - 1;


                WItemSlot itemSlot3 = new WItemSlot(inventar, finalSlotClick, 1, 1, false) {
                    @Override
                    public InputResult onClick(int x, int y, int button) {
                        isNotProfile=false;
                        nickProfile = zapisi.get(finalSlotClick).nickname;
                        client.send(() -> client.setScreen(new DoubleChestScreen(new ProfileGUI(zapisi, page))));//////////////////////////
                        return super.onClick(x, y, button);
                    }

                    @Override
                    public void addTooltip(TooltipBuilder tooltip) {
                        String playerNick = zapisi.get(finalSlotClick).nickname;//////////////////////////////////
                        tooltip.add(Text.of("Открыть профиль " + playerNick));
                        super.addTooltip(tooltip);
                    }
                };
                itemSlot3.setIcon(new ItemIcon(new ItemStack(Items.PLAYER_HEAD)));

                isPKMSlotProfile = false;
                if (xSlot == xMax-1) {
                    root.add(itemSlot3, 2, ySlot + 1);
                    WItemSlot itemSlot4 = funcForSlot(zapisi, g, slotClick, page1, "-");
                    itemSlot4.setIcon(new ItemIcon(new ItemStack(Items.DEEPSLATE_DIAMOND_ORE)));
                    root.add(itemSlot4, xSlot, ySlot);
                    xSlot++;
                }
                //else if(xSlot==xMin)

                else {
                    root.add(itemSlot3, xSlot, ySlot);
                    xSlot++;
                }


            }


            //СЛОТ ДЛЯ ДОБАВЛЕНИЯ В ЧЁРНЫЙ СПИСОК
            else if (isPKMSlotUbrat) {
                int finalSlotClick = slotClick;
                WItemSlot itemSlot3 = new WItemSlot(inventar, finalSlotClick, 1, 1, false) {
                    @Override
                    public InputResult onClick(int x, int y, int button) {
                        //List<String>blackList = new ArrayList<>();
                        blackList.add(zapisi.get(finalSlotClick - 1).nickname);
                        client.send(() -> client.setScreen(new DoubleChestScreen(new ExampleGui(zapisi, page, -1, -1, -1))));/////////////////////////

                        return super.onClick(x, y, button);
                    }

                    @Override
                    public void addTooltip(TooltipBuilder tooltip) {
                        String playerNick = zapisi.get(finalSlotClick - 1).nickname;
                        tooltip.add(Text.of("Убрать " + playerNick));
                        super.addTooltip(tooltip);
                    }
                };
                itemSlot3.setIcon(new ItemIcon(new ItemStack(Items.WRITTEN_BOOK)));

                isPKMSlotProfile = false;


                if (xSlot == xMin && slotClick!=1) {
                    root.add(itemSlot3, 13, ySlot - 1);
                    WItemSlot itemSlot4 = funcForSlot(zapisi, g, slotClick, page1, "-");
                    itemSlot4.setIcon(new ItemIcon(new ItemStack(Items.DEEPSLATE_DIAMOND_ORE)));
                    root.add(itemSlot4, xSlot, ySlot);
                    xSlot++;

                }
                else if(xSlot == xMin && slotClick == 1){
                    root.add(itemSlot3,xMin+1,yMin);
                    xSlot++;
                }
                else {
                    if(ySlot>=yMin && xSlot>=xMin) {
                        root.add(itemSlot3, xSlot, ySlot);

                    }
                    xSlot++;
                }

            }


                g++;

            //}
        }
        //}

        WButton buttonLeft = new WButton(Text.of("Back")){
            @Override
            public InputResult onClick(int x, int y, int button) {
               // LOGGER.info("проверка 3");
                if(page>1){
                    indexSlot -=60;
                    page--;
                    Text textForLabel = Text.of("Page " + page + "/" + countOfPages);
                    label.setText(textForLabel);
                    client.send(() -> client.setScreen(new DoubleChestScreen(new ExampleGui(zapisi,page,-1,-1,-1))));
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
                    client.send(() -> client.setScreen(new DoubleChestScreen(new ExampleGui(zapisi,page,-1,-1,-1))));

                }
                return super.onClick(x, y, button);
            }
        };
        root.add(buttonRight, 12, 8, 3, 2);

        WButton buttonBlackList = new WButton(Text.of("Blacklist")){
            @Override
            public InputResult onClick(int x, int y, int button) {
                // LOGGER.info("проверка 3");

                    indexSlot -=60;
                    page--;
                    Text textForLabel = Text.of("Page " + page + "/" + countOfPages);
                    label.setText(textForLabel);
                    int pageBila = page;
                    client.send(() -> client.setScreen(new DoubleChestScreen(new OpenBlackList(zapisi, pageBila,blackList,-1))));


                return super.onClick(x, y, button);
            }
        };
        root.add(buttonBlackList, 7, 8, 2, 1);

        root.add(label, 6, 1, 2, 1);




    }





    public static WItemSlot funcForSlot(List<zapis> zapisi, int g, int slotClick, int pageBila,String nickProfile){
        Boolean isNotProfile_func = true;

        if(!nickProfile.equals("-")){isNotProfile_func=false;}
        Boolean finalIsNotProfile = isNotProfile_func;
        WItemSlot itemSlot = new WItemSlot(inventar, g,1,1,false){


            @Override
            public void addTooltip(TooltipBuilder tooltip) {
               // LOGGER.info("проверка 5");

                if (g <= zapisi.size()) {
                    if (g <= zapisi.size()) {
                        zapis block = zapisi.get(g-1);
                        String blockX = String.valueOf(block.x);
                        String blockY = String.valueOf(block.y);
                        String blockZ = String.valueOf(block.z);
                        String blockKolvoOre = String.valueOf(block.kolvoOre);

                        if (block.block.equals("deepslate_diamond_ore") && finalIsNotProfile) {
                            tooltip.add(Text.of(block.vremya + " " + block.nickname));
                            tooltip.add(Text.of(blockX + " " + blockY + " " + blockZ));
                            tooltip.add(Text.of(blockKolvoOre));
                        }
                        else if(block.block.equals("deepslate_diamond_ore") && !finalIsNotProfile && block.nickname.equals(nickProfile)){
                            tooltip.add(Text.of(block.vremya + " " + block.nickname));
                            tooltip.add(Text.of(blockX + " " + blockY + " " + blockZ));
                            tooltip.add(Text.of(blockKolvoOre));
                        }
                    }
                    super.addTooltip(tooltip);
                }
            }
            @Override
            public InputResult onClick(int x, int y, int button){
                //LOGGER.info(String.valueOf(x) + " " + String.valueOf(y) + " " + String.valueOf(button));

                //if(key==1) {

                    if(button==0) {
                        if (g <= zapisi.size()) {
                            zapis block = zapisi.get(g - 1);
                            String blockX = String.valueOf(block.x);
                            String blockY = String.valueOf(block.y);
                            String blockZ = String.valueOf(block.z);
                            String userNickname = client.getSession().getUsername();
                            zapisNext = g-1;
                            String command = "co teleport " + blockX + ".5 " + blockY + ".5 " + blockZ + ".5";
                            networkHandler.sendChatCommand(command);
                        }
                    }
                    else if(button == 1){
                        client.send(() -> client.setScreen(new DoubleChestScreen(new ExampleGui(zapisi, pageBila,-1,-1,g))));
                        //priLKM(g,zapisi);
                    }


                //}

//                if(key==2){
//                    priPKM(g,zapisi);
//                }


               // return super.onKeyPressed(ch,key,modifiers);
                return super.onClick(x,y,button);

            }
        };
        if(g!=slotClick && g!=slotClick-1 && g!=slotClick+1 && g<=zapisi.size()){
            //LOGGER.info("проверка 7");

           // client.player.sendMessage(Text.of(zapisi.get(g-1).block),false);
            if(zapisi.get(g-1).block.equals("deepslate_diamond_ore")&&isNotProfile_func){
                itemSlot.setIcon(new ItemIcon(new ItemStack(Items.DEEPSLATE_DIAMOND_ORE)));
            }
            else if(zapisi.get(g-1).block.equals("deepslate_diamond_ore")&&!isNotProfile_func&&zapisi.get(g-1).nickname.equals(nickProfile)){
                itemSlot.setIcon(new ItemIcon(new ItemStack(Items.DEEPSLATE_DIAMOND_ORE)));
            }
        }
        return itemSlot;
    }




    public static int countPages(int kolvoZapis) {
        //LOGGER.info("проверка 7");

        int ostatok = -1;
        int kolvoStr = 0;

        while (kolvoZapis > 0) {
            if (kolvoZapis < 60) {
                kolvoStr++;
                kolvoZapis = 0;
            } else if (kolvoZapis > 60 || ostatok > 60) {
                ostatok = kolvoZapis % 60;
                kolvoStr++;
                kolvoZapis -= 60;
            } else if (ostatok == 0) {
                kolvoStr++;
                kolvoZapis -= 60;
            }

        }
        return kolvoStr;
    }









    public static Inventory inventar = new Inventory() {

        @Override
        public int size() {
            //LOGGER.info("проверка 8");
            return 0;
        }

        @Override
        public boolean isEmpty() {
            //LOGGER.info("проверка 9");
            return false;
        }

        @Override
        public ItemStack getStack(int slot) {
            //LOGGER.info("проверка 10");
            return null;
        }

        @Override
        public ItemStack removeStack(int slot, int amount) {
            //LOGGER.info("проверка 11");
            return null;
        }

        @Override
        public ItemStack removeStack(int slot) {
            //LOGGER.info("проверка 12");
            return null;
        }

        @Override
        public void setStack(int slot, ItemStack stack) {
           // LOGGER.info("проверка 13");

        }

        @Override
        public void markDirty() {
            //OGGER.info("проверка 14");

        }

        @Override
        public boolean canPlayerUse(PlayerEntity player) {
            //LOGGER.info("проверка 15");
            return false;
        }

        @Override
        public void clear() {
           // LOGGER.info("проверка 16");


        }

    };

    List<zapis> sortZapisi(List<zapis> zapisi){
        for(int i = 0; i<zapisi.size();i++){
            zapis zapis_1 = zapisi.get(i);
            for(int j=i; j<zapisi.size();j++){
                zapis zapis_2 = zapisi.get(j);
                if(getTime(zapis_1.vremya)>getTime(zapis_2.vremya)){
                    zapis_2 = zapis_1;
                    zapis zapis_3=zapis_2;
                    zapisi.set(i,zapis_3);
                    zapisi.set(j,zapis_1);
                }


            }
        }


        return zapisi;
    }
    float getTime(String vremya){
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

