package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.NordClient;
import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.clientutil.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemStack;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class QuickDrop extends Hack {
    int delay = 0;
    Setting speed;


    public QuickDrop() {
        super("QuickDrop", Category.MISC, "When Holding Q items will contine to drop");
        NordClient.INSTANCE.settingsManager.rSetting(speed = new Setting("Speed", this, 5, 0, 5, true, "QuickDropSpeed"));

    }

    @Listener
    public void onUpdate(UpdateEvent event) {

    if (mc.gameSettings.keyBindDrop.isKeyDown()) {
        //ItemStack is = new ItemStack(mc.player.getHeldItemMainhand().getItem(), mc.player.getHeldItemOffhand().getCount());
        delay++;
        if (delay > speed.getValInt()) {
            mc.player.dropItem(false);
            delay = 0;
        }



        }

    }
}
