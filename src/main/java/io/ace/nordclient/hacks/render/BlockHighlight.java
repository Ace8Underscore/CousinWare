package io.ace.nordclient.hacks.render;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.RainbowUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.awt.*;

public class BlockHighlight extends Hack {

    public static Setting r;
    public static Setting g;
    public static Setting b;
    public static Setting a;
    Setting rainbow;

    public BlockHighlight() {

        super("BlockHighlight", Category.RENDER);

        CousinWare.INSTANCE.settingsManager.rSetting(r = new Setting("Red", this, 1, 0, 255, false, "BlockHighlightRed"));
        CousinWare.INSTANCE.settingsManager.rSetting(g = new Setting("Green", this, 1, 0, 255, false, "BlockHighlightGreen"));
        CousinWare.INSTANCE.settingsManager.rSetting(b = new Setting("Blue", this, 1, 0, 255, false, "BlockHighlightBlue"));
        CousinWare.INSTANCE.settingsManager.rSetting(a = new Setting("alpha", this, 1, 0, 1, false, "BlockHighlightAlpha"));
        CousinWare.INSTANCE.settingsManager.rSetting(rainbow = new Setting("Rainbow", this, true, "BlockHighlightRainbow"));

    }
    @Listener
    public void onUpdate(UpdateEvent event) {
        if (rainbow.getValBoolean()) {
            RainbowUtil.settingRainbow(r, g, b);
        }
    }


    //
}
