package io.ace.nordclient.hacks.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.client.Core;
import io.ace.nordclient.utilz.RenderUtilz;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author Ace________/Ace_#1233
 */

public class NameTags extends Hack {

    Setting range;

    public NameTags() {
        super("NameTags", Category.RENDER, 10955851);
        CousinWare.INSTANCE.settingsManager.rSetting(range = new Setting("Range", this, 100, 0, 400, false, "NameTagsRange"));
    }

    @Override
    public void onWorldRender(RenderEvent event) {
        renderName();
    }

    public void renderName() {
        for (Entity player : mc.world.getLoadedEntityList()) {
            if (player instanceof EntityPlayer) {
                if (!player.getName().equals(mc.player.getName())) {
                    if (mc.player.getDistance(player) <= range.getValDouble()) {
                        if (Core.customFont.getValBoolean()) RenderUtilz.drawTextCustom( (float) player.posX - (float) .5, (float) player.posY + 2, (float) player.posZ - (float) .5, ChatFormatting.WHITE + player.getName() + " " + ChatFormatting.GREEN + Math.round(((EntityPlayer) player).getHealth() + ((EntityPlayer) player).getAbsorptionAmount() * 1.00) / 1.00);
                        else RenderUtilz.drawText( (float) player.posX - (float) .5, (float) player.posY + 2, (float) player.posZ - (float) .5, ChatFormatting.WHITE + player.getName() + " " + ChatFormatting.GREEN + Math.round(((EntityPlayer) player).getHealth() + ((EntityPlayer) player).getAbsorptionAmount() * 1.00) / 1.00);


                    }

                }
            }
        }
    }


}
