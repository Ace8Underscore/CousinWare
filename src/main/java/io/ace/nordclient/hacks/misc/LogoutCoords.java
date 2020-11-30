package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.hacks.Hack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * @author Ace________/Ace_#1233
 */

public class LogoutCoords extends Hack {

    public LogoutCoords() {
        super("LogoutCoords", Category.MISC, "Saves your coords to the clipboard when logging out of a server", 14363678);
    }

    @SubscribeEvent
    public void onPlayerLeaveEvent(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        if (!mc.isSingleplayer()) {
            if (!mc.getCurrentServerData().serverIP.equalsIgnoreCase("2b2tpvp.net") && mc.player.dimension != 1) {

                int x = (int) mc.player.posX;
                int y = (int) mc.player.posY;
                int z = (int) mc.player.posZ;
                String coords = "Logout Coords: X:" + x + " Y:" + y + " Z:" + z;

                StringSelection data = new StringSelection(coords);
                Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
                cb.setContents(data, data);

            }
        }
    }
}
