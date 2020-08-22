package io.ace.nordclient.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public abstract class Command {
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static String prefix = ".";
    public abstract String[] getClientAlias();
    public abstract String getClientSyntax();
    public abstract void onClientCommand(String command, String[] args) throws Exception;

    public static void sendClientSideMessage(String message) { mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_RED + "[Nord]"+ " " + ChatFormatting.WHITE + message)); }

    public static String getClientPrefix(){
        return prefix;
    }

    public static void setClientPrefix(String p){
        prefix = p;
    }


}
