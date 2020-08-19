package io.ace.nord.hacks.test;

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
    private int delay = 0;

    public TestCommand() {
        super("Test", Category.Test, "Testing For My Own Custom Base");
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.RenderTickEvent event) {
        if (mc.world == null)
            return;
        delay++;

        if (mc.currentScreen instanceof GuiInventory) {
            mc.fontRenderer.drawStringWithShadow("NordMalware", MouseInfo.getPointerInfo().getLocation().x / 2, MouseInfo.getPointerInfo().getLocation().y / 2, 16755200);
            //                 mc.fontRenderer.drawStringWithShadow("HackDescriptionTest", MouseInfo.getPointerInfo().getLocation().x / 2 + 10 , MouseInfo.getPointerInfo().getLocation().y / 2, 16755200);
        }


    }
}


