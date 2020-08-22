package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import javafx.scene.input.ClipboardContent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class LogoutCoords extends Hack {

    public LogoutCoords() {
        super("LogoutCoords", Category.MISC, "Saves your coords to the clipboard when logging out of a server");
    }

    @SubscribeEvent
    public void onPlayerLeaveEvent(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        int x = (int) mc.player.posX;
        int y = (int) mc.player.posY;
        int z = (int) mc.player.posZ;
        String coords = "Logout Coords: X:" + x + ", Y:"+ y + ", Z:" + z;

        StringSelection data = new StringSelection(coords);
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        cb.setContents(data, data);

    } // PLEASE WORK ITS NOT WORKING WTF THE CLIENT AINT
}
