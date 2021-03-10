package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

public class HotbarRefill extends Hack {

    Setting refillAt;

    public HotbarRefill() {
        super("HotbarRefill", Category.MISC, 8868332);
        CousinWare.INSTANCE.settingsManager.rSetting(refillAt = new Setting("RefillAt", this, 32, 1, 64, true, "HotbarRefillFillAt"));
    }

    ItemStack refillItem;
    int slotId;

    @Override
    public void onUpdate() {
        for (int i = 0; i < 45; i++) {
            if (i == 36 || i == 37 || i == 38 || i == 39 || i == 40 || i == 41 || i == 42 || i == 43 || i == 44) {
                if (!mc.player.openContainer.getSlot(i).inventory.isEmpty()) {
                    if (mc.player.openContainer.getSlot(i).getStack().getCount() < refillAt.getValInt()) {
                        refillItem = mc.player.openContainer.getSlot(i).getStack();
                        slotId = i;

                    } else {
                        refillItem = null;
                    }
                }
            } else {
                if (refillItem != null)
                    if (mc.player.openContainer.getSlot(i).getStack().getItem() == refillItem.getItem()) {
                        Command.sendClientSideMessage("clicking");
                        mc.playerController.windowClick(0, i, 1, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(0, slotId, 1, ClickType.PICKUP, mc.player);

                    }
            }
        }

    }
}
