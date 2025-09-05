package xraycheck.methods;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.text.Text;
public class coreprotectStart {

    coreprotectStart(MinecraftClient client, String block, String nickname, String time){
        ClientPlayNetworkHandler networkHandler = client.getNetworkHandler();

        if (networkHandler != null) {
            String command = "co lookup action: -block include: " + block + " user: " + nickname + " time: " + time;
            networkHandler.sendChatCommand(command);

        }
        client.player.sendMessage(Text.of("Собираем данные"),false);
    }
}
