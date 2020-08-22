package io.ace.nordclient.event;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.NordClient;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.managers.CommandManager;
import io.ace.nordclient.managers.HackManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class EventProcessor {
    public static final Minecraft mc = Minecraft.getMinecraft();
    CommandManager commandManager = new CommandManager();


    public void init() {
        NordClient.INSTANCE.getEventManager().addEventListener(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatSent(ClientChatEvent event) {

        if (event.getMessage().startsWith(Command.getClientPrefix())) {
            event.setCanceled(true);
            try {
                mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
                commandManager.callClientCommand(event.getMessage().substring(1));
            } catch (Exception e) {
                e.printStackTrace();
                Command.sendClientSideMessage(ChatFormatting.DARK_RED + "Error: " + e.getMessage());
            }

        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() == 0 || Keyboard.getEventKey() == Keyboard.KEY_NONE) return;
            //Hack binds
            HackManager.onBind(Keyboard.getEventKey());


        }
    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        //rainbow stuff

        if (mc.player != null || mc.world != null) {
            HackManager.onUpdate();
        }
    }

    @SubscribeEvent
    public void onInputUpdate(InputUpdateEvent event) {
        NordClient.INSTANCE.getEventManager().dispatchEvent(event);
    }
}



