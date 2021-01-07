package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.mixin.accessor.ISPacketPlayerPosLook;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.InventoryUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.BlockPos;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

/**
 * @author Ace________/Ace_#1233
 */

public class Burrow extends Hack {


    int delayPlace;
    boolean blockPlaced;
    boolean noForce;
    int startingHand;
    double startingY;
    int useItem;

    Setting delay;
    Setting lagBackPower;
    Setting noForceRotate;
    Setting blockMode;
    Setting lagMode;
    Setting noJump;



    public Burrow() {
        super("Burrow", Category.COMBAT, 5254300);
        CousinWare.INSTANCE.settingsManager.rSetting(delay = new Setting("Delay", this, 4, 0, 8, true, "BurrowDelay"));
        CousinWare.INSTANCE.settingsManager.rSetting(lagBackPower = new Setting("LagBackPower", this,1, .5,10, true, "BurrowLagBack"));
        CousinWare.INSTANCE.settingsManager.rSetting(noForceRotate = new Setting("NoForceRotate", this, true, "BurrowNoForceRotate"));
        ArrayList<String> blockModes = new ArrayList<>();
        blockModes.add("Obi");
        blockModes.add("EChest");
        CousinWare.INSTANCE.settingsManager.rSetting(blockMode = new Setting("BlockMode", this, "Obi", blockModes, "BurrowBlockMode"));
        ArrayList<String> lagModes = new ArrayList<>();
        lagModes.add("Packet");
        lagModes.add("Fly");
        CousinWare.INSTANCE.settingsManager.rSetting(lagMode = new Setting("LagMode", this, "Packet", lagModes, "BurrowLagMode"));
        CousinWare.INSTANCE.settingsManager.rSetting(noJump = new Setting("NoJump", this, true, "BurrowNoJump"));

    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.world == null) this.disable();
        delayPlace++;
        if (noJump.getValBoolean()) mc.player.posY = startingY;
        if (delayPlace >= delay.getValInt() && !noForce) {
            BlockPos pos = new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ);
            if (noJump.getValBoolean()) {
                mc.player.posY = startingY;
                mc.player.inventory.currentItem = useItem;
                BlockInteractionHelper.placeBlockScaffold(pos.up());
                mc.player.inventory.currentItem = startingHand;
            } else  {
                mc.player.posY = startingY;
                mc.player.inventory.currentItem = useItem;
                BlockInteractionHelper.placeBlockScaffold(pos);
                mc.player.inventory.currentItem = startingHand;
            }
            if (!mc.world.getBlockState(pos).getBlock().canPlaceBlockAt(mc.world, pos)) {
                if (lagMode.getValString().equalsIgnoreCase("Fly")) {
                    mc.player.motionY = lagBackPower.getValDouble();
                } else {
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + lagBackPower.getValDouble(), mc.player.posZ, mc.player.onGround));
                }
                if (!noForceRotate.getValBoolean()) this.disable();

                }
            }
        }

    public void onEnable() {
        startingY = mc.player.posY;
        startingHand = mc.player.inventory.currentItem;
        if (blockMode.getValString().equalsIgnoreCase("Obi")) {
            if (InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN) == -1) {
                Command.sendClientSideMessage("No Obsidian Found");
                this.disable();
            } else {
                useItem = InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN);
                blockPlaced = false;
                noForce = false;
                mc.player.jump();
                delayPlace = 0;
            }
        }

        if (blockMode.getValString().equalsIgnoreCase("EChest")) {
            if (InventoryUtil.findBlockInHotbar(Blocks.ENDER_CHEST) == -1) {
                Command.sendClientSideMessage("No EChest Found");
                this.disable();
            } else {
                useItem = InventoryUtil.findBlockInHotbar(Blocks.ENDER_CHEST);
                blockPlaced = false;
                noForce = false;
                mc.player.jump();
                delayPlace = 0;
            }
        }
    }
    @Listener
    public void onUpdate(PacketEvent.Receive event) {
        if (mc.world == null || mc.player == null)
            this.disable();
        if (noForceRotate.getValBoolean()) {
            Packet packet = event.getPacket();

            if (event.getPacket() instanceof SPacketPlayerPosLook) {
                event.setCanceled(true);
                //this.toggle();
                double d0 = ((ISPacketPlayerPosLook) event.getPacket()).getX();
                double d1 = ((ISPacketPlayerPosLook) event.getPacket()).getY();
                double d2 = ((ISPacketPlayerPosLook) event.getPacket()).getZ();


                if (((ISPacketPlayerPosLook) event.getPacket()).getFlags().contains(SPacketPlayerPosLook.EnumFlags.X)) {
                    d0 += mc.player.posX;
                } else {
                    ((ISPacketPlayerPosLook) event.getPacket()).setX(0.0D);
                }

                if (((ISPacketPlayerPosLook) event.getPacket()).getFlags().contains(SPacketPlayerPosLook.EnumFlags.Y)) {
                    d1 += mc.player.posY;
                } else {
                    mc.player.motionY = 0.0D;
                }

                if (((ISPacketPlayerPosLook) event.getPacket()).getFlags().contains(SPacketPlayerPosLook.EnumFlags.Z)) {
                    d2 += mc.player.posZ;
                } else {
                    mc.player.motionZ = 0.0D;
                }

                mc.player.setPosition(d0, d1, d2);
                mc.getConnection().sendPacket(new CPacketConfirmTeleport(((ISPacketPlayerPosLook) event.getPacket()).getTeleportId()));
                mc.getConnection().sendPacket(new CPacketPlayer.PositionRotation(mc.player.posX, mc.player.getEntityBoundingBox().minY, ((ISPacketPlayerPosLook) event.getPacket()).getZ(), ((ISPacketPlayerPosLook) event.getPacket()).getYaw(), ((ISPacketPlayerPosLook) event.getPacket()).getPitch(), false));
                this.disable();
            }
        }
    }
    //

}

