package io.ace.nordclient.hacks.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;

import java.util.ArrayList;

public class FriendTab extends Hack {


    public static Setting mode;

    public FriendTab() {
        super("FriendTab", Category.RENDER, 40);

        java.util.ArrayList<String> modes = new ArrayList<>();
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
        modes.add("OBFUSCATED");
        CousinWare.INSTANCE.settingsManager.rSetting(mode = new Setting("Mode", this, "GREEN", modes, "FriendTabColorModes"));


    }

    public static ChatFormatting colorchoice(){
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
            case "OBFUSCATED": return ChatFormatting.OBFUSCATED;
            default: return ChatFormatting.WHITE;
        }


    }
}
