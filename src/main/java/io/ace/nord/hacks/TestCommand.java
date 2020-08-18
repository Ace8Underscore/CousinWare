package io.ace.nord.hacks;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nord.command.Command;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;

public class TestCommand extends Hack {
    private int delay = 0;

    public TestCommand() {
        super("TestCommand", Category.Test, "Testing For My Own Custom Base");
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.RenderTickEvent event) {
        if (mc.world == null)
            return;
        delay++;
        if (delay > 1000) {
            if (mc.player.isSneaking()) {
                Command.sendClientSideMessage("Working?");
            }
            if (mc.currentScreen instanceof GuiCrafting) {
                mc.fontRenderer.drawStringWithShadow("HackDescriptionTest", MouseInfo.getPointerInfo().getLocation().x / 2, MouseInfo.getPointerInfo().getLocation().y / 2, 16755200);
            //                 mc.fontRenderer.drawStringWithShadow("HackDescriptionTest", MouseInfo.getPointerInfo().getLocation().x / 2 + 10 , MouseInfo.getPointerInfo().getLocation().y / 2, 16755200);
            }


        }
    }
    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
        //Command.sendClientSideMessage(this.name + " Was" + ChatFormatting.GREEN + "Enabled!");

    }

    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
        //Command.sendClientSideMessage(this.name + " Was" + ChatFormatting.RED +"Disabled!");

    }
}

