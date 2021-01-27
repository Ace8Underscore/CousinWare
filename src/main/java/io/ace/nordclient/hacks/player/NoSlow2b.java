package io.ace.nordclient.hacks.player;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.CPacketEntityAction;

import java.util.ArrayList;

/**
 * @author Ace________/Ace_#1233
 */

public class NoSlow2b extends Hack {
    private int delay;
    private boolean sneaking = false;

    Setting mode;
    Setting speed;

    public NoSlow2b() {
        super("NoSlow2b", Category.PLAYER, 11464533);
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Spam");
        modes.add("Constant");
        CousinWare.INSTANCE.settingsManager.rSetting(mode = new Setting("Mode", this, "Constant", modes, "NoSlow2bMode"));
        CousinWare.INSTANCE.settingsManager.rSetting(speed = new Setting("SwitchDelay", this, 5, 0, 20, true, "NoSlow2bSpeed"));

    }

    @Override
    public void onUpdate() {
        delay++;
        if (mode.getValString().equalsIgnoreCase("Constant")) {
            if (mc.player.getHeldItemMainhand().getItemUseAction().equals(EnumAction.EAT) && mc.player.getHeldItemMainhand().getItem() instanceof ItemFood && mc.gameSettings.keyBindUseItem.isKeyDown()) {
                if (delay > speed.getValInt() && !sneaking && !mc.player.onGround) {
                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    delay = 0;
                    sneaking = true;
                }

            }
                if (mc.player.getHeldItemMainhand().getItemUseAction().equals(EnumAction.EAT) && delay > speed.getValInt() && sneaking && !mc.gameSettings.keyBindUseItem.isKeyDown()) {

                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    delay = 0;
                    sneaking = false;
                }
            }




        if (mode.getValString().equalsIgnoreCase("Spam")) {
            if (mc.player.getHeldItemMainhand().getItemUseAction().equals(EnumAction.EAT) && mc.player.getHeldItemMainhand().getItem() instanceof ItemFood && mc.gameSettings.keyBindUseItem.isKeyDown() && !sneaking) {
                if (delay > speed.getValInt() && !sneaking) {
                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    sneaking = true;
                    delay = 0;
                }
            } else {
                if (sneaking) {
                    if (delay > speed.getValInt() && sneaking) {
                        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                        delay = 0;
                        sneaking = false;
                    }
                }
            }

        }
    }

    @Override
    public void onEnable() {
        sneaking = false;
        delay = 0;
    }

    }



