package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.command.commands.Set;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.InventoryUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketEntityHeadLook;
import net.minecraft.network.play.server.SPacketEntityProperties;
import net.minecraft.network.play.server.SPacketPlayerAbilities;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.BlockPos;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

/**
 * @author Ace________/Ace_#1233
 */

public class Burrow extends Hack {


    int delayPlace;
    boolean blockPlaced;
    boolean noForce;
    int startingHand;

    Setting delay;
    Setting lagBackPower;
    Setting noForceRotate;



    public Burrow() {
        super("Burrow", Category.COMBAT, 5254300);
        CousinWare.INSTANCE.settingsManager.rSetting(delay = new Setting("Delay", this, 4, 4, 8, true, "BurrowDelay"));
        CousinWare.INSTANCE.settingsManager.rSetting(lagBackPower = new Setting("LagBackPower", this,1, .5,3, true, "BurrowLagBack"));
        CousinWare.INSTANCE.settingsManager.rSetting(noForceRotate = new Setting("NoForceRotate", this, true, "BurrowNoForceRotate"));
    }

    @Override
    public void onUpdate() {
        delayPlace++;
        if (delayPlace >= delay.getValInt() && !noForce) {
            BlockPos pos = new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ);
            BlockInteractionHelper.placeBlockScaffold(pos);
            if (!mc.world.getBlockState(pos).getBlock().canPlaceBlockAt(mc.world, pos)) {
                mc.player.motionY = lagBackPower.getValDouble();
                //mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - 1, mc.player.posZ, true));
                mc.player.inventory.currentItem = startingHand;
                    this.toggle();
                }
            }
        }

    public void onEnable() {
        startingHand = mc.player.inventory.currentItem;
        if (InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN) == -1) {
            Command.sendClientSideMessage("No Obsidian Found");
            this.toggle();
        } else {
            mc.player.inventory.currentItem = InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN);
            blockPlaced = false;
            noForce = false;
            mc.player.jump();
            delayPlace = 0;
        }
    }

}

