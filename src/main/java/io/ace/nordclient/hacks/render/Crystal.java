package io.ace.nordclient.hacks.render;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.client.settings.GameSettings;

public class Crystal extends Hack {

    public static Setting x;
    public static Setting y;
    public static Setting z;

    public Crystal() {
        super("DjCrystal", Category.RENDER, "Change Size of EndCrystals", 13436958);
        CousinWare.INSTANCE.settingsManager.rSetting(x = new Setting("X", this, 1, -1, 1,false,"CrystalX" ));
        CousinWare.INSTANCE.settingsManager.rSetting(y = new Setting("Y", this, 1, -1, 1,false,"CrystalY" ));
        CousinWare.INSTANCE.settingsManager.rSetting(z = new Setting("Z", this, 1, -1, 1,false,"CrystalZ" ));

    }


}
