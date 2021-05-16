package io.ace.nordclient.hacks.render;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;

/**
 * @author Ace________/Ace_#1233
 */

public class ViewModelChanger extends Hack {

    public static Setting sizeX;
    public static Setting sizeY;
    public static Setting sizeZ;
    public static Setting sizeXMain;
    public static Setting sizeYMain;
    public static Setting sizeZMain;
    public static Setting rotateZ;
    public ViewModelChanger() {
        super("ViewModelChanger", Category.RENDER, 9392978);
        CousinWare.INSTANCE.settingsManager.rSetting(sizeX = new Setting("OffSetXMain", this, 1, -3, 3,false,"ViewModelChangerSizeX" ));
        CousinWare.INSTANCE.settingsManager.rSetting(sizeY = new Setting("OffSetYMain", this, 1, -3, 3,false,"ViewModelChangerSizeY" ));
        CousinWare.INSTANCE.settingsManager.rSetting(sizeZ = new Setting("SizeMain", this, 1, 0, 4,false,"ViewModelChangerSizeZ" ));
        CousinWare.INSTANCE.settingsManager.rSetting(sizeXMain = new Setting("SizeXMain", this, 1, 0, 4,false,"ViewModelChangerSizeMainX" ));
        CousinWare.INSTANCE.settingsManager.rSetting(sizeYMain = new Setting("SizeYMain", this, 1, 0, 4,false,"ViewModelChangerSizeMainY" ));
        CousinWare.INSTANCE.settingsManager.rSetting(sizeZMain = new Setting("SizeZMain", this, 1, 0, 4,false,"ViewModelChangerSizeMainZ" ));
        CousinWare.INSTANCE.settingsManager.rSetting(rotateZ = new Setting("RotateYawMain", this, 1, 0, 360,false,"ViewModelChangerRotateZ" ));

    }
}
