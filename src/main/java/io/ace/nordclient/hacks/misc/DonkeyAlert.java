package io.ace.nordclient.hacks.misc;


import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
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

        super("DonkeyAlert", Category.MISC, "Announces the location of any donkeys in the players render distance", 16);

    CousinWare.INSTANCE.settingsManager.rSetting(donkeyAlert = new Setting("DonkeyAlert", this, true, "DonkeyAlertDonkeyAlert"));
    CousinWare.INSTANCE.settingsManager.rSetting(muleAlert = new Setting("MuleAlert", this, true, "DonkeyAlertMuleAlert"));
    CousinWare.INSTANCE.settingsManager.rSetting(llamaAlert = new Setting("LlamaAlert", this, true, "DonkeyAlertLlamaAlert"));
    CousinWare.INSTANCE.settingsManager.rSetting(horseAlert = new Setting("HorseAlert", this, true, "DonkeyAlertHorseAlert"));

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


    CousinWare.INSTANCE.settingsManager.rSetting(mode = new Setting("Mode", this, "GREEN", modes, "DonkeyAlertColorModes"));

}

   /* private Setting<Boolean> donkeyAlert = register(Settings.b("Donkey", true));
    private Setting<Boolean> muleAlert = register(Settings.b("Mule", true));
    private Setting<Boolean> llamaAlert = register(Settings.b("Llama", true));
    private Setting<Boolean> horseAlert = register(Settings.b("Horse", false));

    private Setting<DonkeyAlert.colour> mode = register(Settings.e("Colour", DonkeyAlert.colour.DARK_PURPLE)); */

    private int antiSpam;

    @Override
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
    private ChatFormatting colorchoice(){
        switch (mode.getValString()){
            case "BLACK": return ChatFormatting.BLACK;
            case "RED": return ChatFormatting.RED;
            case "AQUA": return ChatFormatting.AQUA;
            case "BLUE": return ChatFormatting.BLUE;
            case "GOLD": return ChatFormatting.GOLD;
            case "GRAY": return ChatFormatting.GRAY;
            case "WHITE": return ChatFormatting.WHITE;
            case "GREEN": return ChatFormatting.GREEN;
            case "YELLOW": return ChatFormatting.YELLOW;
            case "DARK_RED": return ChatFormatting.DARK_RED;
            case "DARK_AQUA": return ChatFormatting.DARK_AQUA;
            case "DARK_BLUE": return ChatFormatting.DARK_BLUE;
            case "DARK_GRAY": return ChatFormatting.DARK_GRAY;
            case "DARK_GREEN": return ChatFormatting.DARK_GREEN;
            case "DARK_PURPLE": return ChatFormatting.LIGHT_PURPLE;
            case "LIGHT_PURPLE": return ChatFormatting.DARK_PURPLE;
            default: return ChatFormatting.WHITE;
        }


    }

    private enum color{
        BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE
    }

}





