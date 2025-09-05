package xraycheck.methods;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xraycheck.zapis;

import java.util.List;

import static xraycheck.XrayCheck.MOD_ID;
import static xraycheck.methods.ExampleGui.inventar;

public class OpenBlackList extends LightweightGuiDescription {
    WGridPanel root = new WGridPanel();
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    MinecraftClient client = MinecraftClient.getInstance();
    ClientPlayNetworkHandler networkHandler = client.getNetworkHandler();
    int g = 1;
    int indexSlot=1;

    int page;
    boolean canRClick = true;
    public OpenBlackList(List<zapis> zapisi, int pageBila, List<String> blackList, int slotClick) {
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
                boolean isTrue = true;
                if(blackList.size()>=g){
                    int finalSlotClick = g;
                    WItemSlot itemSlot = new WItemSlot(inventar, g,1,1,false){
                        @Override
                        public void addTooltip(TooltipBuilder tooltip) {
                            // LOGGER.info("проверка 5");


                                tooltip.add(Text.of("Вернуть " + blackList.get(finalSlotClick-1)));
                                super.addTooltip(tooltip);

                        }
                        @Override
                        public InputResult onClick(int x, int y, int button){
                            if(button==0) {

                                    for(int bL = finalSlotClick-1; bL<blackList.size();bL++){
                                        if(blackList.size()==bL+1){
                                            break;
                                        }
                                        blackList.set(bL,blackList.get(bL+1));
                                    }
                                    blackList.remove(blackList.size()-1);
                                    client.send(() -> client.setScreen(new DoubleChestScreen(new OpenBlackList(zapisi, pageBila,blackList,finalSlotClick))));

                            }
                            return super.onClick(x,y,button);
                        }
                    };
                itemSlot.setIcon(new ItemIcon(new ItemStack(Items.PLAYER_HEAD)));

                    root.add(itemSlot,j,i);
                }
                else{
                    WItemSlot itemSlot = WItemSlot.of(inventar,0);
                    root.add(itemSlot,j,i);
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
                    client.send(() -> client.setScreen(new DoubleChestScreen(new OpenBlackList(zapisi, pageBila,blackList,-1))));
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
                    client.send(() -> client.setScreen(new DoubleChestScreen(new OpenBlackList(zapisi, pageBila,blackList,-1))));

                }
                return super.onClick(x, y, button);
            }
        };
        root.add(buttonRight, 12, 8, 3, 2);
        WButton buttonBack = new WButton(Text.of("Back")){
            @Override
            public InputResult onClick(int x, int y, int button) {

                client.send(() -> client.setScreen(new DoubleChestScreen(new ExampleGui(zapisi,1,-1,-1,-1))));
                return super.onClick(x, y, button);
            }
        };
        root.add(buttonBack, 1, 1, 2, 1);
        root.add(label, 6, 1, 2, 1);










    }
}
