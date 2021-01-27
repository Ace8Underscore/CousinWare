package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.hacks.Hack;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

/**
 * @author Ace________/Ace_#1233
 */

public class FastXp extends Hack {

    public FastXp() {
        super("FastXp", Category.COMBAT, 2301059);
    }

    @Override
    public void onUpdate() {
        if (mc.player.getHeldItemMainhand().getItem().equals(Items.EXPERIENCE_BOTTLE) && mc.gameSettings.keyBindUseItem.isKeyDown()) {
            mc.getConnection().sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        }
    }
}
