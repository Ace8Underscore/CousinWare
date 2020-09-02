package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import net.minecraft.client.gui.inventory.GuiInventory;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class QuickDrop extends Hack {
    private int delay = 0;

    public QuickDrop() {
        super("QuickDrop", Category.MISC, "When Holding Q items will contine to drop");
    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        delay++;
        if (mc.gameSettings.keyBindDrop.isPressed()) {

            if (delay >= 6)  {
                mc.player.dropItem(false);
            }
            if (delay >= 12) {
                mc.player.dropItem(false);
                delay = 0;

            }
        }

    }
}
