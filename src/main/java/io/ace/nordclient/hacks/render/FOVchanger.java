package io.ace.nordclient.hacks.render;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;

public class FOVchanger  extends Hack {


    Setting FOV;

    public FOVchanger(){
        super("FOVchanger",Category.RENDER,1);
        CousinWare.INSTANCE.settingsManager.rSetting(FOV = new Setting("FOV",this,100,10,150,true,"FOVchangerFOV"));

    }

    @Override
    public void onUpdate(){
        mc.gameSettings.fovSetting = FOV.getValInt();
    }
}
