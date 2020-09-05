package io.ace.nordclient.hacks.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.NordClient;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.HackManager;
import io.ace.nordclient.utilz.clientutil.Setting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Comparator;

public class ArrayList extends Hack {

    /**
     * @author Ace________/Ace_#1233
     */

    int hackCount;



    Setting x;
    Setting y;

    public ArrayList() {
        super("ArrayList", Category.RENDER);
        NordClient.INSTANCE.settingsManager.rSetting(x = new Setting("x", this, 1, 0, 1000, false, "ArrayListX"));
        NordClient.INSTANCE.settingsManager.rSetting(y = new Setting("y", this, 3, 0, 1000, false, "ArrayListY"));

    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event)  {
        if (mc.world == null)
            return;
        hackCount = 0;
        HackManager.getHacks()
                .stream()
                .filter(Hack::isEnabled)
                .filter(Hack::isDrawn)
                .sorted(Comparator.comparing(hack -> mc.fontRenderer.getStringWidth(this.getName() + ChatFormatting.GRAY + " " + this.getHudInfo()) * (-1)))
                .forEach(h -> {
                    mc.fontRenderer.drawStringWithShadow("| " + h.getName() + ChatFormatting.GRAY + " " + h.getHudInfo(), x.getValInt(), y.getValInt() + (hackCount * 10),16755200);

                    hackCount++;
                });
    }

}
