package io.ace.nordclient.utilz.configz;

import io.ace.nordclient.CousinWare;

public class ShutDown extends Thread {

    /**
     * @author Finz0
     *
     **/

    @Override
    public void run(){
        saveConfig();
    }

    public static void saveConfig(){
        CousinWare.INSTANCE.configUtils.saveMods();
        CousinWare.INSTANCE.configUtils.saveBinds();
        CousinWare.INSTANCE.configUtils.saveDrawn();
        CousinWare.INSTANCE.configUtils.savePrefix();
        CousinWare.INSTANCE.configUtils.saveFriends();
        CousinWare.INSTANCE.configUtils.saveSettingsList();
        CousinWare.INSTANCE.configUtils.saveFont();
        CousinWare.INSTANCE.configUtils.saveHuds();
        CousinWare.INSTANCE.configUtils.saveHudPos();

    }
}