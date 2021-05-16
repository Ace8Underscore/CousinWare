package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.FriendManager;
import io.ace.nordclient.managers.RotationManager;
import io.ace.nordclient.mixin.accessor.ICPacketPlayer;
import io.ace.nordclient.utilz.Setting;
import io.ace.nordclient.utilz.TpsUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;
import java.util.Comparator;

public class Aura extends Hack {

    Setting range;
    Setting rotate;
    Setting tpsSync;
    Setting hitDelay;
    Setting swordCheck;
    Setting stopSprint;
    Setting raytrace;
    Setting priority;
    //Setting player;
    //Setting

    Entity attackTarget;

    int delay = 0;
    int tpsDelay = 1;
    boolean fakeRotation;
    boolean spoofing;
    float yaw;
    float pitch;

    public Aura() {
        super("Aura", Category.COMBAT, 16562801);
        CousinWare.INSTANCE.settingsManager.rSetting(range = new Setting("Range", this, 5.5, 0, 7, false, "AuraRange"));
        CousinWare.INSTANCE.settingsManager.rSetting(rotate = new Setting("Rotate", this, true, "AuraRotate"));
        CousinWare.INSTANCE.settingsManager.rSetting(raytrace = new Setting("Raytrace", this, false, "AuraRaytrace"));
        CousinWare.INSTANCE.settingsManager.rSetting(hitDelay = new Setting("HitDelay", this, true, "AuraHitDelay"));
        ArrayList<String> prioModes = new ArrayList<>();
        prioModes.add("Distance");
        prioModes.add("Health");
        CousinWare.INSTANCE.settingsManager.rSetting(priority = new Setting("Priority", this, "Distance", prioModes, "AuraPrioModes"));
        CousinWare.INSTANCE.settingsManager.rSetting(stopSprint = new Setting("StopSprinting", this, true, "AuraStopSprint"));
        CousinWare.INSTANCE.settingsManager.rSetting(swordCheck = new Setting("SwordCheck", this, true, "AuraSwordCheck"));
        //CousinWare.INSTANCE.settingsManager.rSetting(player = new Setting("Player", this, true, "AuraPlayer"));
        ArrayList<String> tpsModes = new ArrayList<>();
        tpsModes.add("None");
        tpsModes.add("Average");
        tpsModes.add("AboveAvg");
        CousinWare.INSTANCE.settingsManager.rSetting(tpsSync = new Setting("TpsSync", this, "Average", tpsModes, "AuraTpsSync"));
    }
    @Override
    public void onUpdate() {
        if (priority.getValString().equalsIgnoreCase("Distance"))
        if (tpsSync.getValString().equalsIgnoreCase("average")) tpsDelay = 1;
        else if (tpsSync.getValString().equalsIgnoreCase("AboveAvg"))tpsDelay = 2;
        else tpsDelay = 0;
        if (getTarget() == null) return;
        if (raytrace.getValBoolean() && !mc.player.canEntityBeSeen(getTarget())) return;
        if (swordCheck.getValBoolean()) if (!(mc.player.inventory.getCurrentItem().getItem() instanceof ItemSword)) return;
            if (rotate.getValBoolean()) lookAtPacket(getTarget().posX, getTarget().posY, getTarget().posZ, mc.player);
            //if (delay >= 18 + ((20 - TpsUtils.getTickRate()))) {
            if (hitDelay.getValBoolean()) {
            if (mc.player.getCooledAttackStrength(0) == 1) {
                delay++;
                if (delay >= ((20 - TpsUtils.getTickRate()) * tpsDelay)) {
                    if (rotate.getValBoolean()) spoofing = true;
                    else spoofing = false;
                    attackTaget();
                    delay = 0;
                } else {
                    if (spoofing) {
                        //spoofing = false;
                    }
                }
            }
            } else {
                if (rotate.getValBoolean()) spoofing = true;
                else spoofing = false;
                attackTaget();
            }
            if (spoofing) {
                mc.player.rotationPitch += 0.0004;
            } else {

            }


    }

    public Entity getTarget() {
        if (priority.getValString().equalsIgnoreCase("Distance")) return getTargetDistance();
        else return getTargetHealth();
    }



    public Entity getTargetDistance() {
        EntityPlayer targets = mc.world.playerEntities.stream()
                .filter(entity -> entity != mc.player)
                .filter(entity -> mc.player.getDistance(entity) <= range.getValDouble())
                .filter(entity -> !entity.isDead)
                .filter(entity -> entity instanceof EntityPlayer)
                .filter(entity -> ((EntityPlayer) entity).getHealth() > 0)
                .filter(entity -> !FriendManager.isFriend(entity.getName()))
                .min(Comparator.comparing(e -> mc.player.getDistance(e)))
                .orElse(null);


            attackTarget = targets;

    return attackTarget;
    }

    public Entity getTargetHealth() {
        EntityPlayer targets = mc.world.playerEntities.stream()
                .filter(entity -> entity != mc.player)
                .filter(entity -> mc.player.getDistance(entity) <= range.getValDouble())
                .filter(entity -> !entity.isDead)
                .filter(entity -> entity instanceof EntityPlayer)
                .filter(entity -> ((EntityPlayer) entity).getHealth() > 0)
                .filter(entity -> !FriendManager.isFriend(entity.getName()))
                .min(Comparator.comparing(e -> ((EntityPlayer) e).getHealth() + ((EntityPlayer) e).getAbsorptionAmount()))
                .orElse(null);
        attackTarget = targets;

        return attackTarget;
    }


    public void attackTaget() {
        if (getTarget() == null) return;
            if (mc.player.getDistance(getTarget()) <= range.getValDouble()) {
                if (rotate.getValBoolean()) spoofing = true;
                if (stopSprint.getValBoolean()) mc.player.setSprinting(false);
                mc.playerController.attackEntity(mc.player, getTarget());
                mc.player.swingArm(EnumHand.MAIN_HAND);

            }

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
        if (event.getPacket() instanceof CPacketPlayer && spoofing) {
            ((ICPacketPlayer) event.getPacket()).setYaw(yaw);
            ((ICPacketPlayer) event.getPacket()).setPitch(pitch);

        }

    }
    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
        spoofing = false;
    }

    @Override
    public String getHudInfo() {
        return "\u00A77[\u00A7f" + "Single" + "\u00A77]";
    }


}




