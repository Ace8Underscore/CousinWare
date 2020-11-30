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
    public static Setting rotateX;
    public static Setting rotateY;
    public static Setting rotateZ;
    public ViewModelChanger() {
        super("ViewModelChanger", Category.RENDER, 9392978);
        CousinWare.INSTANCE.settingsManager.rSetting(sizeX = new Setting("OffSetX", this, 1, -3, 3,false,"ViewModelChangerSizeX" ));
        CousinWare.INSTANCE.settingsManager.rSetting(sizeY = new Setting("OffSetY", this, 1, -3, 3,false,"ViewModelChangerSizeY" ));
        CousinWare.INSTANCE.settingsManager.rSetting(sizeZ = new Setting("Size", this, 1, 0, 4,false,"ViewModelChangerSizeZ" ));

        CousinWare.INSTANCE.settingsManager.rSetting(rotateX = new Setting("RotateX", this, 1, 0, 360,false,"ViewModelChangerRotateX" ));
        CousinWare.INSTANCE.settingsManager.rSetting(rotateY = new Setting("RotateY", this, 1, 0, 360,false,"ViewModelChangerRotateY" ));
        CousinWare.INSTANCE.settingsManager.rSetting(rotateZ = new Setting("RotateYaw", this, 1, 0, 360,false,"ViewModelChangerRotateZ" ));

    }
}
