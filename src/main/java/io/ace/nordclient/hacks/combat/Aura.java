package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.FriendManager;
import io.ace.nordclient.managers.HackManager;
import io.ace.nordclient.managers.RotationManager;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.Setting;
import io.ace.nordclient.utilz.TpsUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Overwrite;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Aura extends Hack {

    Setting range;
    Setting crits;

    Entity attackTarget;

    int delay = 0;
    boolean fakeRotation;
    boolean spoofing;
    float yaw;
    float pitch;

    public Aura() {
        super("Aura", Category.COMBAT, 1);
        CousinWare.INSTANCE.settingsManager.rSetting(range = new Setting("Range", this, 5.5, 0, 7, false, "AuraRange"));
        CousinWare.INSTANCE.settingsManager.rSetting(crits = new Setting("Criticals", this, true, "AuraCriticals"));
    }
    @Override
    public void onUpdate() {
        lookAtPacket(getTarget().posX, getTarget().posY, getTarget().posZ, mc.player);
        //if (delay >= 18 + ((20 - TpsUtils.getTickRate()))) {
            if (mc.player.getCooledAttackStrength(0) == 1) {
                delay++;
                if (delay >= 2 + ((20 - TpsUtils.getTickRate()))) {
                    attackTaget();
                    delay = 0;
                }
            }
    }



    public Entity getTarget() {
        List<Entity> targets = mc.world.loadedEntityList.stream()
                .filter(entity -> entity != mc.player)
                .filter(entity -> mc.player.getDistance(entity) <= range.getValDouble())
                .filter(entity -> !entity.isDead)
                .filter(entity -> entity instanceof EntityPlayer)
                .filter(entity -> ((EntityPlayer) entity).getHealth() > 0)
                .filter(entity -> !FriendManager.isFriend(entity.getName()))
                .sorted(Comparator.comparing(e -> mc.player.getDistance(e)))
                .collect(Collectors.toList());

        targets.forEach(target -> {
                if (!(mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) return;
            attackTarget = target;
        });
    return attackTarget;
    }


    public void attackTaget() {
        if (mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) {
            if (mc.player.getDistance(getTarget()) <= range.getValDouble()) {
                spoofing = true;
                if (crits.getValBoolean()) {
                    //doCrits();
                }
                mc.playerController.attackEntity(mc.player, getTarget());
                mc.player.swingArm(EnumHand.MAIN_HAND);

            }
        } else {
            spoofing = false;
        }
    }

    private void doCrits() {
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + .0628, mc.player.posZ, true));
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
        //mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, true));
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

        if (event.getPacket() instanceof CPacketPlayer) {
            ((CPacketPlayer) event.getPacket()).yaw = (float) yaw;
            ((CPacketPlayer) event.getPacket()).pitch = (float) pitch;

        }
        if (event.getPacket() instanceof CPacketUseEntity) {
            final CPacketUseEntity packet = (CPacketUseEntity)event.getPacket();
            if (packet.getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround && crits.getValBoolean()) {
                mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.10000000149011612, mc.player.posZ, false));
                mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));

            }
        }
    }
    @Override
    public void onEnable() {
        spoofing = true;
    }

    @Override
    public void onDisable() {
        spoofing = false;
    }
}




