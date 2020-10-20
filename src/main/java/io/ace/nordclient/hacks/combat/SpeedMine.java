package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.EventPlayerClickBlock;
import io.ace.nordclient.event.EventPlayerDamageBlock;
import io.ace.nordclient.event.EventPlayerResetBlockRemoving;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

public class SpeedMine extends Hack {
    public SpeedMine() {
        super("SpeedMine", Category.COMBAT, "Mine blocks faster", 10);
        CousinWare.INSTANCE.settingsManager.rSetting(reset = new Setting("Reset", this, true, "SpeedMineReset"));
        CousinWare.INSTANCE.settingsManager.rSetting(fastFall = new Setting("FastFall", this, false, "SpeedMineFastFall"));
        CousinWare.INSTANCE.settingsManager.rSetting(doubleBreak = new Setting("DoubleBreak", this, true, "SpeedMineDoubleBreak"));


        ArrayList<String> modes = new ArrayList<>();
        modes.add("Packet");
        modes.add("Damage");
        modes.add("Instant");
        CousinWare.INSTANCE.settingsManager.rSetting(mode = new Setting("Mode", this, "Packet", modes, "SpeedMineMode"));

    }

    Setting mode;
    Setting reset;
    Setting fastFall;
    Setting doubleBreak;

    @Override
    public String getHudInfo() {
        return "\u00A77[\u00A7f" + mode.getValString() + "\u00A77]";
    }


    @Override
    public void onUpdate() {
        mc.playerController.blockHitDelay = 0;
        if (this.reset.getValBoolean() && Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown()) {
            mc.playerController.isHittingBlock = false;
        }
        if (fastFall.getValBoolean()) {
            if (mc.player.onGround)
                --mc.player.motionY;
        }
    }


    @Listener
    public void setReset(EventPlayerResetBlockRemoving event) {
        if (this.reset.getValBoolean()) {
            event.setCanceled(true);
        }
    }


    @Listener
    public void clickBlock(EventPlayerClickBlock event) {
        if (this.reset.getValBoolean()) {
            if (mc.playerController.curBlockDamageMP > 0.1f) {
                mc.playerController.isHittingBlock = true;

            }
        }
    }


    @Listener
    public void damageBlock(EventPlayerDamageBlock event) {
        if (canBreak(event.getPos())) {
            if (this.reset.getValBoolean()) {
                mc.playerController.isHittingBlock = false;
            }

            if (mode.getValString().equalsIgnoreCase("Instant")) {
                mc.player.swingArm(EnumHand.MAIN_HAND);
                mc.player.connection.sendPacket(new CPacketPlayerDigging(
                        CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getDirection()));
                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK,
                        event.getPos(), event.getDirection()));
                mc.playerController.onPlayerDestroyBlock(event.getPos());
                mc.world.setBlockToAir(event.getPos());
            }
            if ((mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe)) {

                if (mode.getValString().equalsIgnoreCase("Packet")) {
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(
                            CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getDirection()));
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK,
                            event.getPos(), event.getDirection()));
                    event.setCanceled(true);
                }
                if (mode.getValString().equalsIgnoreCase("Damage")) {
                    if  (mc.playerController.curBlockDamageMP >= 0.7) {
                        mc.playerController.curBlockDamageMP = 1;

                    }

                }

            }


            if (this.doubleBreak.getValBoolean()) {
                final BlockPos above = event.getPos().add(0, 1, 0);

                if (canBreak(above) && mc.player.getDistance(above.getX(), above.getY(), above.getZ()) <= 5f) {
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(
                            CPacketPlayerDigging.Action.START_DESTROY_BLOCK, above, event.getDirection()));
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK,
                            above, event.getDirection()));
                    mc.playerController.onPlayerDestroyBlock(above);
                    mc.world.setBlockToAir(above);
                }
            }
        }
    }

    private boolean canBreak(BlockPos pos) {
        final IBlockState blockState = mc.world.getBlockState(pos);
        final Block block = blockState.getBlock();

        return block.getBlockHardness(blockState, Minecraft.getMinecraft().world, pos) != -1;
    }
}