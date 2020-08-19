package io.ace.nord.event;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nord.NordClient;
import io.ace.nord.command.Command;
import io.ace.nord.managers.CommandManager;
import io.ace.nord.managers.HackManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class EventProcessor {
    public static final Minecraft mc = Minecraft.getMinecraft();
    CommandManager commandManager = new CommandManager();


    public void init(){
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
            //Module binds
            HackManager.onBind(Keyboard.getEventKey());


            }
        }
    }



