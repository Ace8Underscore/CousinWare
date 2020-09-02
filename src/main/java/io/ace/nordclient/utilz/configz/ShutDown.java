package io.ace.nordclient.utilz.configz;

import io.ace.nordclient.NordClient;

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
        NordClient.INSTANCE.configUtils.saveMods();
        NordClient.INSTANCE.configUtils.saveBinds();
        NordClient.INSTANCE.configUtils.saveDrawn();
        NordClient.INSTANCE.configUtils.savePrefix();
        NordClient.INSTANCE.configUtils.saveFriends();
        NordClient.INSTANCE.configUtils.saveSettingsList();

    }
}