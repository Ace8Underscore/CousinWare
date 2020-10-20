package io.ace.nordclient.hacks.render;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.player.NoSlow2b;
import io.ace.nordclient.utilz.Setting;

public class NoLag extends Hack {

    public static Setting eTable;

    public NoLag() {
        super("NoLag", Category.RENDER, 1);
        CousinWare.INSTANCE.settingsManager.rSetting(eTable = new Setting("ETable", this, true, "NoLagETable"));
    }

    public void onUpdate() {

    }
}
