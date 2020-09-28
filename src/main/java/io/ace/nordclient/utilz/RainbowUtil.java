package io.ace.nordclient.utilz;

import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.client.Colors;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.awt.*;

public class RainbowUtil {

    public static Color getAllRainbow() {
        final float[] hue = {(System.currentTimeMillis() % (360 * 32)) / (360f * 32)};
        int rgb = Color.HSBtoRGB(1, 1, 1);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        Color c = new Color(red, green, blue);
        return c;

    }

    public static int getRainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.70f, 1f).getRGB();
    }

    public static void settingRainbow(Setting r, Setting g, Setting b) {

        float[] tick_color = {(System.currentTimeMillis() % (360 * 32)) / (360f * 32)};

        int colorRGB = Color.HSBtoRGB(tick_color[0], (float) Colors.saturation.getValDouble(), (float) Colors.brightness.getValDouble());

        r.setValDouble((colorRGB >> 16) & 0xFF);
        g.setValDouble((colorRGB >> 8) & 0xFF);
        b.setValDouble(colorRGB & 0xFF);

    }
}
