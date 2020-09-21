package io.ace.nordclient.hacks.render;

import io.ace.nordclient.NordClient;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.clientutil.Setting;

public class BlockHighlight extends Hack {

    public static Setting r;
    public static Setting g;
    public static Setting b;
    public static Setting a;

    public BlockHighlight() {

        super("BlockHighlight", Category.RENDER);

        NordClient.INSTANCE.settingsManager.rSetting(r = new Setting("Red", this, 1, 0, 1, false, "BlockHighlightRed"));
        NordClient.INSTANCE.settingsManager.rSetting(g = new Setting("Green", this, 1, 0, 1, false, "BlockHighlightGreen"));
        NordClient.INSTANCE.settingsManager.rSetting(b = new Setting("Blue", this, 1, 0, 1, false, "BlockHighlightBlue"));
        NordClient.INSTANCE.settingsManager.rSetting(a = new Setting("alpha", this, 1, 0, 1, false, "BlockHighlightAlpha"));

    }
    //
}
