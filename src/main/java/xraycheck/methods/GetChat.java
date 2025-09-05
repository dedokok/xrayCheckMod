package xraycheck.methods;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetChat {
    private static final Logger LOGGER = LoggerFactory.getLogger("XrayCheck-Chat");

    public static String register() {
        // Подписываемся на событие получения сообщения в чат
        ClientReceiveMessageEvents.CHAT.register((message, signedMsg, sender, params, receptionTimestamp) -> {
            String plainText = message.getString(); // Получаем текст сообщения



            if (plainText.contains("алмаз")) {
                LOGGER.warn("Обнаружено подозрительное слово в чате!");
            }
        });
        return "123";

    }
}