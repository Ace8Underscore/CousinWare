package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.EventPlayerClickBlock;
import io.ace.nordclient.event.EventPlayerDamageBlock;
import io.ace.nordclient.event.EventPlayerResetBlockRemoving;
import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.mixin.accessor.IPlayerControllerMP;
import io.ace.nordclient.utilz.NordTessellator;
import io.ace.nordclient.utilz.Setting;
import io.ace.nordclient.utilz.Timer;
import io.ace.nordclient.utilz.TpsUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.awt.*;
import java.util.ArrayList;

public class SpeedMine extends Hack {

    Setting mode;
    Setting reset;
    Setting fastFall;
    Setting doubleBreak;
    Setting onlyPic;
    Setting render;
    BlockPos breakPos;
    private final Timer timer;

    public SpeedMine() {
        super("SpeedMine", Category.COMBAT, "Mine blocks faster", 15763555);
        CousinWare.INSTANCE.settingsManager.rSetting(reset = new Setting("Reset", this, true, "SpeedMineReset"));
        CousinWare.INSTANCE.settingsManager.rSetting(fastFall = new Setting("FastFall", this, false, "SpeedMineFastFall"));
        CousinWare.INSTANCE.settingsManager.rSetting(doubleBreak = new Setting("DoubleBreak", this, true, "SpeedMineDoubleBreak"));
        CousinWare.INSTANCE.settingsManager.rSetting(onlyPic = new Setting("OnlyPic", this, true, "SpeedMineOnlyPic"));

        ArrayList<String> modes = new ArrayList<>();
        modes.add("Packet");
        modes.add("Insta");
        modes.add("Damage");
        modes.add("Instant");
        CousinWare.INSTANCE.settingsManager.rSetting(mode = new Setting("Mode", this, "Packet", modes, "SpeedMineMode"));
        CousinWare.INSTANCE.settingsManager.rSetting(render = new Setting("Render", this, true, "SpeedMineRender"));

        this.timer = new Timer();

    }

    @Override
    public String getHudInfo() {
        return "\u00A77[\u00A7f" + mode.getValString() + "\u00A77]";
    }


    @Override
    public void onUpdate() {
        if (this.reset.getValBoolean() && Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown()) {
            ((IPlayerControllerMP) mc.playerController).setIsHittingBlock(false);
        }
        if (fastFall.getValBoolean()) {
            if (mc.player.onGround)
                --mc.player.motionY;
        }//

    }

    @Override
    public void onWorldRender(RenderEvent event) {
        if (this.render.getValBoolean() && this.breakPos != null) {
            final Color color = new Color(this.timer.passedMs((int) (2000.0f * TpsUtils.getTickRate())) ? 0 : 255, this.timer.passedMs((int) (2000.0f * TpsUtils.getTickRate())) ? 255 : 0, 0, 255);
            NordTessellator.drawBoundingBoxBlockPos(this.breakPos, 1, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
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
            if (((IPlayerControllerMP) mc.playerController).getCurBlockDamageMP() > 0.1f) {
                ((IPlayerControllerMP) mc.playerController).setIsHittingBlock(true);

            }
        }
    }

    @Listener
    public void damageBlock(EventPlayerDamageBlock event) {
        if (canBreak(event.getPos())) {
            if (event.getPos() != null) {
                //timer.reset();
                breakPos = event.getPos();
            }
            if (this.reset.getValBoolean()) {
                ((IPlayerControllerMP) mc.playerController).setIsHittingBlock(false);
            }

            if (mode.getValString().equalsIgnoreCase("Instant")) {
                mc.player.swingArm(EnumHand.MAIN_HAND);
                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getDirection()));
                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getDirection()));
                mc.playerController.onPlayerDestroyBlock(event.getPos());
                mc.world.setBlockToAir(event.getPos());
            }
            if (onlyPic.getValBoolean() && (mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe)) {

                if (mode.getValString().equalsIgnoreCase("Packet")) {
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getDirection()));
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getDirection()));
                    event.setCanceled(true);
                }
                if (mode.getValString().equalsIgnoreCase("Insta")) {
                    ((IPlayerControllerMP) mc.playerController).setCurBlockDamageMP(.9f);
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getDirection()));

                }

                if (mode.getValString().equalsIgnoreCase("Damage")) {
                    if (((IPlayerControllerMP) mc.playerController).getCurBlockDamageMP() >= 0.7f) {
                        ((IPlayerControllerMP) mc.playerController).setCurBlockDamageMP(1);
//
                    }

                }


            }
            if (!onlyPic.getValBoolean()) {
                if (mode.getValString().equalsIgnoreCase("Packet")) {
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getDirection()));
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getDirection()));
                    event.setCanceled(true);
                }
                if (mode.getValString().equalsIgnoreCase("Insta")) {
                    ((IPlayerControllerMP) mc.playerController).setCurBlockDamageMP(.9f);
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getDirection()));

                }

                if (mode.getValString().equalsIgnoreCase("Damage")) {
                    if (((IPlayerControllerMP) mc.playerController).getCurBlockDamageMP() >= 0.7f) {
                        ((IPlayerControllerMP) mc.playerController).setCurBlockDamageMP(1);
//
                    }

                }

            }


            if (this.doubleBreak.getValBoolean()) {
                final BlockPos above = event.getPos().add(0, 1, 0);

                if (canBreak(above) && mc.player.getDistance(above.getX(), above.getY(), above.getZ()) <= 5f) {
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, above, event.getDirection()));
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, above, event.getDirection()));
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
