package io.ace.nordclient.hacks.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.HackManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Comparator;

public class ArrayList extends Hack {

    int hackCount;

    public ArrayList() {
        super("ArrayList", Category.RENDER);
    }

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent event) {
        if (mc.world == null)
            return;
        hackCount = 0;
        HackManager.getHacks()
                .stream()
                .filter(Hack::isEnabled)
                .filter(Hack::isDrawn)
                .sorted(Comparator.comparing(hack -> mc.fontRenderer.getStringWidth(this.getName() + ChatFormatting.GRAY + " " + this.getHudInfo()) * (-1)))
                .forEach(h -> {
                    mc.fontRenderer.drawStringWithShadow("| " + h.getName() + ChatFormatting.GRAY + " " + h.getHudInfo(), 1, 3 + (hackCount * 10),16755200);
                    hackCount++;
                });
    }

}
