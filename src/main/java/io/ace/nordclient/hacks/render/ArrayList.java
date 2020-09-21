package io.ace.nordclient.hacks.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.NordClient;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.HackManager;
import io.ace.nordclient.utilz.RainbowUtil;
import io.ace.nordclient.utilz.clientutil.Setting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.Comparator;

public class ArrayList extends Hack {

    /**
     * @author Ace________/Ace_#1233
     */

    int hackCount;



    Setting x;
    Setting y;
    Setting r;
    Setting g;
    Setting b;
    Color c;
    Setting rainbow;

    public ArrayList() {
        super("ArrayList", Category.RENDER);
        NordClient.INSTANCE.settingsManager.rSetting(x = new Setting("x", this, 1, 0, 1000, false, "ArrayListX"));
        NordClient.INSTANCE.settingsManager.rSetting(y = new Setting("y", this, 3, 0, 1000, false, "ArrayListY"));
        NordClient.INSTANCE.settingsManager.rSetting(r = new Setting("Red", this, 255, 0, 255, true, "ArrayListRed"));
        NordClient.INSTANCE.settingsManager.rSetting(g = new Setting("Green", this, 26, 0, 255, true, "ArrayListGreen"));
        NordClient.INSTANCE.settingsManager.rSetting(b = new Setting("Blue", this, 42, 0, 255, true, "ArrayListBlue"));
        NordClient.INSTANCE.settingsManager.rSetting(rainbow = new Setting("Rainbow", this, true, "ArrayListRainbow"));
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event)  {
        int[] offsetCounter = {1};
        if (mc.world == null)
            return;
        hackCount = 0;
        HackManager.getHacks()
                .stream()
                .filter(Hack::isEnabled)
                .filter(Hack::isDrawn)
                .sorted(Comparator.comparing(hack -> mc.fontRenderer.getStringWidth(this.getName() + ChatFormatting.GRAY + " " + this.getHudInfo()) * (-1)))
                .forEach(h -> {
                    if (rainbow.getValBoolean()) {
                        c = new Color(r.getValInt(), g.getValInt(), b.getValInt());
                        mc.fontRenderer.drawStringWithShadow("| " + h.getName() + ChatFormatting.GRAY + " " + h.getHudInfo(), x.getValInt(), y.getValInt() + (hackCount * 10), RainbowUtil.getRainbow(hackCount * 150));
                        offsetCounter[0]++;
                        hackCount++;
                    } else {
                        c = new Color(r.getValInt(), g.getValInt(), b.getValInt());
                        mc.fontRenderer.drawStringWithShadow("| " + h.getName() + ChatFormatting.GRAY + " " + h.getHudInfo(), x.getValInt(), y.getValInt() + (hackCount * 10), c.getRGB());
                        offsetCounter[0]++;
                        hackCount++;

                    }
                });
    }

}
