package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.HackManager;
import io.ace.nordclient.managers.RotationManager;
import io.ace.nordclient.mixin.accessor.ICPacketPlayer;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.NordTessellator;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class CrystalAura extends Hack {

    int delay = 0;
    double breakX;
    double breakY;
    double breakZ;
    float yaw;
    float pitch;
    boolean spoofRotate;
    boolean sendOwnPacket;

    Setting breakDelay;
    Setting breakRange;
    Setting rotate;

    public CrystalAura() {
        super("CrystalAura", Category.COMBAT, 2794140);
        CousinWare.INSTANCE.settingsManager.rSetting(breakDelay = new Setting("BreakDelay", this, 2, 0, 20, true, "CrystalAuraBreakDelay"));
        CousinWare.INSTANCE.settingsManager.rSetting(breakRange = new Setting("BreakRange", this, 5.5, 0, 8, false, "CrystalAuraBreakRange"));
        CousinWare.INSTANCE.settingsManager.rSetting(rotate = new Setting("Rotate", this, true, "CrystalAuraRotate"));

    }
    @Override
    public void onUpdate() {
        delay++;
        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (entity instanceof EntityEnderCrystal) {
                if (mc.player.getDistance(entity) <= breakRange.getValDouble()) {
                    if (delay >= breakDelay.getValInt()) {
                        spoofRotate = true;
                        breakX = entity.posX;
                        breakY = entity.posY;
                        breakZ = entity.posZ;
                        if (sendOwnPacket && rotate.getValBoolean()) {
                            mc.player.connection.sendPacket(new CPacketPlayer.Rotation(yaw, pitch, mc.player.onGround));
                            sendOwnPacket = false;
                        }
                        mc.playerController.attackEntity(mc.player, entity);
                        mc.player.swingArm(HackManager.getHackByName("Swing").isEnabled() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                        delay = 0;

                        } else if (delay <= breakDelay.getValInt()) {
                        spoofRotate = false;
                        sendOwnPacket = false;
                    }
                } //might have to do else right here
            }
        }
    }
    @Override
    public void onWorldRender(RenderEvent event) {
        BlockPos breakPos = new BlockPos(breakX, breakY, breakZ);
        NordTessellator.drawBoundingBoxBlockPos(breakPos.down(), 2, 255, 1, 1, 255);
    }


    private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = RotationManager.calculateLookAt(px, py, pz, me);
        setYawAndPitch((float) v[0], (float) v[1]);
    }

    private void setYawAndPitch(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Listener
    public void onUpdate(PacketEvent.Send event) {
            if (event.getPacket() instanceof CPacketPlayer && spoofRotate && rotate.getValBoolean()) {
                sendOwnPacket = false;
                lookAtPacket(breakX, breakY, breakZ, mc.player);
                ((ICPacketPlayer) event.getPacket()).setYaw(yaw);
                ((ICPacketPlayer) event.getPacket()).setPitch(pitch);

        }
            if (!(event.getPacket() instanceof CPacketPlayer) && spoofRotate && rotate.getValBoolean() && mc.player.onGround && mc.player.motionZ < .1 && mc.player.motionZ > -.1&& mc.player.motionX < .1 && mc.player.motionX > -.1) {
                sendOwnPacket = true;
            }
        if (event.getPacket() instanceof CPacketPlayer && !spoofRotate && rotate.getValBoolean()) {
            ((ICPacketPlayer) event.getPacket()).setYaw(mc.player.rotationYaw);
            ((ICPacketPlayer) event.getPacket()).setPitch(mc.player.rotationPitch);

        }


    }
    @Override
    public void onEnable() {
        spoofRotate = false;
    }
}
