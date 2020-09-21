package io.ace.nordclient.hacks.misc;


import io.ace.nordclient.NordClient;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.clientutil.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMule;

import java.util.ArrayList;

public class DonkeyAlert extends Hack {

    Setting donkeyAlert;
    Setting muleAlert;
    Setting llamaAlert;
    Setting horseAlert;
    Setting mode;

public DonkeyAlert() {

        super("DonkeyAlert", Category.MISC, "Announces the location of any donkeys in the players render distance");

    NordClient.INSTANCE.settingsManager.rSetting(donkeyAlert = new Setting("DonkeyAlert", this, true, "DonkeyAlertDonkeyAlert"));
    NordClient.INSTANCE.settingsManager.rSetting(muleAlert = new Setting("MuleAlert", this, true, "DonkeyAlertMuleAlert"));
    NordClient.INSTANCE.settingsManager.rSetting(llamaAlert = new Setting("LlamaAlert", this, true, "DonkeyAlertLlamaAlert"));
    NordClient.INSTANCE.settingsManager.rSetting(horseAlert = new Setting("HorseAlert", this, true, "DonkeyAlertHorseAlert"));

    ArrayList<String> modes = new ArrayList<>();
    modes.add("BLACK");
    modes.add("RED");
    modes.add("AQUA");
    modes.add("BLUE");
    modes.add("GOLD");
    modes.add("GRAY");
    modes.add("WHITE");
    modes.add("GREEN");
    modes.add("YELLOW");
    modes.add("DARK_RED");
    modes.add("DARK_AQUA");
    modes.add("DARK_BLUE");
    modes.add("DARK_GRAY");
    modes.add("DARK_GREEN");
    modes.add("DARK_PURPLE");
    modes.add("LIGHT_PURPLE");


    NordClient.INSTANCE.settingsManager.rSetting(mode = new Setting("Mode", this, "GREEN", modes, "DonkeyAlertColorModes"));

}

   /* private Setting<Boolean> donkeyAlert = register(Settings.b("Donkey", true));
    private Setting<Boolean> muleAlert = register(Settings.b("Mule", true));
    private Setting<Boolean> llamaAlert = register(Settings.b("Llama", true));
    private Setting<Boolean> horseAlert = register(Settings.b("Horse", false));

    private Setting<DonkeyAlert.colour> mode = register(Settings.e("Colour", DonkeyAlert.colour.DARK_PURPLE)); */

    private int antiSpam;
        public void onUpdate() {
        antiSpam++;

            for (Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
                if (e instanceof EntityDonkey && donkeyAlert.getValBoolean()) {
                    if (antiSpam >= 100) {
                        Command.sendClientSideMessage(colorchoice() + " Found Donkey!" + " X:" + (int) e.posX + " Z:" + (int) e.posZ);
                        antiSpam = -600;
                    }
                }
                if (e instanceof EntityMule && muleAlert.getValBoolean()) {
                    if (antiSpam >= 100) {
                        Command.sendClientSideMessage(colorchoice() + " Found Mule!" + " X:" + (int) e.posX + " Z:" + (int) e.posZ);
                        antiSpam = -600;
                    }

                }
                if (e instanceof EntityLlama && llamaAlert.getValBoolean()) {
                    if (antiSpam >= 100) {
                        Command.sendClientSideMessage(colorchoice() + " Found Llama!" + " X:" + (int) e.posX + " Z:" + (int) e.posZ);
                        antiSpam = -600;
                    }

                }
                if (e instanceof EntityHorse && horseAlert.getValBoolean()) {
                    if (antiSpam >= 100) {
                        Command.sendClientSideMessage(colorchoice() + " Found Horse!" + " X:" + (int) e.posX + " Z:" + (int) e.posZ);
                        antiSpam = -600;
                    }

                }

            }
        }
    private String colorchoice(){
        switch (mode.getValString()){
            case "BLACK": return "&0";
            case "RED": return "&c";
            case "AQUA": return "&b";
            case "BLUE": return "&9";
            case "GOLD": return "&6";
            case "GRAY": return "&7";
            case "WHITE": return "&f";
            case "GREEN": return "&a";
            case "YELLOW": return "&e";
            case "DARK_RED": return "&4";
            case "DARK_AQUA": return "&3";
            case "DARK_BLUE": return "&1";
            case "DARK_GRAY": return "&8";
            case "DARK_GREEN": return "&2";
            case "DARK_PURPLE": return "&5";
            case "LIGHT_PURPLE": return "&d";
            default: return "";
        }


    }

    private enum color{
        BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE
    }

}





