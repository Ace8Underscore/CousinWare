package io.ace.nord.hacks.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nord.command.Command;
import io.ace.nord.hacks.Hack;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;

public class TestCommand extends Hack {

    public TestCommand() {
        super("NordOnTop", Category.RENDER, "Testing For My Own Custom Base");
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.RenderTickEvent event) {
        if (mc.world == null)
            return;

        if (mc.currentScreen != null) {
            mc.fontRenderer.drawStringWithShadow(mc.player.getName() + " On Top", MouseInfo.getPointerInfo().getLocation().x / 2, MouseInfo.getPointerInfo().getLocation().y / 2, 16755200);
            //                 mc.fontRenderer.drawStringWithShadow("HackDescriptionTest", MouseInfo.getPointerInfo().getLocation().x / 2 + 10 , MouseInfo.getPointerInfo().getLocation().y / 2, 16755200);
        }


    }
}


