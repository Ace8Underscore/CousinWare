package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.network.play.client.CPacketChatMessage;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

public class ChatSuffix extends Hack {

    Setting mode;

    public ChatSuffix() {
        super("ChatSuffix", Category.MISC, 13319279);
        ArrayList<String> modes = new ArrayList<>();
        modes.add("2b2t");
        modes.add("CousinWare");
        modes.add("AceHack");
        CousinWare.INSTANCE.settingsManager.rSetting(mode = new Setting("Mode", this, "2b2t", modes, "ChatSuffixModes"));

    }

    String suffix = "";

    @Listener
    public void onUpdate(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketChatMessage) {
            if (mode.getValString().equalsIgnoreCase("2b2t")) {
                suffix = " | CousinWare";
            }
            if (mode.getValString().equalsIgnoreCase("AceHack")) {
                suffix = " \uFF5C \u1d00\u1d04\u1d07\u029c\u1d00\u1d04\u1d0b";
            }
            if (mode.getValString().equalsIgnoreCase("CousinWare")) {
                suffix = " \uFF5C \u1d04\u1d0f\u1d1c\ua731\u026a\u0274\u1d21\u1d00\u0280\u1d07";
            }
            String message = ((CPacketChatMessage) event.getPacket()).getMessage();
            if (!message.contains(suffix) && !message.startsWith("/") && !message.startsWith(Command.getClientPrefix())) {
                event.setCanceled(true);
                mc.player.sendChatMessage(message + suffix);
            }
            //String message = ((CPacketChatMessage) event.getPacket()).getMessage();
            //mc.player.sendChatMessage(message + " | CousinWare");
        }
    }
}
