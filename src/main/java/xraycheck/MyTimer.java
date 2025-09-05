package xraycheck;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

public class MyTimer {
    public class ClientTimer {
        private static int ticksRemaining = 0;
        private static Runnable callback;

        public static void init() {
            ClientTickEvents.END_CLIENT_TICK.register(client -> {
                if (ticksRemaining > 0) {
                    ticksRemaining--;
                    if (ticksRemaining == 0 && callback != null) {
                        callback.run();
                    }
                }
            });
        }

        public static void setTimer(int ticks, Runnable action) {
            ticksRemaining = ticks;
            callback = action;
        }
    }
}