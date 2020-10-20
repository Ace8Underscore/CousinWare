package io.ace.nordclient.hacks.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.MathUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;

public class ElytraFly extends Hack {

    Setting speed;
    Setting glide;
    Setting glideSpeed;
    Setting noGlideAFK;
    Setting boost;
    Setting autoTakeoff;
    private Setting flyMode;
    private int sendPacketDelay = 0;

    public ElytraFly() {
        super("ElytraFly", Category.MOVEMENT, 23);

        CousinWare.INSTANCE.settingsManager.rSetting(speed = new Setting("Speed", this, 2, 0, 10, false, "ElytraFlySpeed"));
        CousinWare.INSTANCE.settingsManager.rSetting(glide = new Setting("Glide", this, true, "ElytraFlyGlide"));
        CousinWare.INSTANCE.settingsManager.rSetting(glideSpeed = new Setting("GlideSpeed", this, 1, 0, 2.5, false, "ElytraFlyGlideSpeed"));
        CousinWare.INSTANCE.settingsManager.rSetting(noGlideAFK = new Setting("NoGlideAFK", this, false, "ElytraFlyNoGlideAFK"));
        CousinWare.INSTANCE.settingsManager.rSetting(boost = new Setting("Boost", this, true, "ElytraFlyBoost"));
        CousinWare.INSTANCE.settingsManager.rSetting(autoTakeoff = new Setting("AutoTakeOff", this, true, "ElytraFlyAutoTakeOff"));

        ArrayList<String> flyModes = new ArrayList<>();
        flyModes.add("2b");
        flyModes.add("Creative");
        flyModes.add("Plane");
        CousinWare.INSTANCE.settingsManager.rSetting(flyMode = new Setting("FlyModes", this, "2b", flyModes, "ElytraFlyFlyModes"));



    }

    @Override
    public void onUpdate() {
        if (mc.player.isElytraFlying() && !mc.gameSettings.keyBindSneak.isKeyDown()) {
            final float yaw = GetRotationYawForCalc();
            if (flyMode.getValString().equalsIgnoreCase("2b")) {
                if (mc.player.rotationPitch > 0) {
                    mc.player.motionX -= MathHelper.sin(yaw) * .18 / 10;
                    mc.player.motionZ += MathHelper.cos(yaw) * .18 / 10;


                }
            }

            if (flyMode.getValString().equalsIgnoreCase("plane")) {
                final double[] dir = MathUtil.directionSpeed(speed.getValDouble());
                mc.player.motionY *= 0;
                mc.player.motionX = dir[0];
                mc.player.motionZ = dir[1];
            }
            if (flyMode.getValString().equalsIgnoreCase("creative")) {
                final double[] dir = MathUtil.directionSpeed(speed.getValDouble());
                if (!boost.getValBoolean()) {
                    mc.player.motionX = dir[0];
                    mc.player.motionZ = dir[1];
                } else {
                    if (mc.player.rotationPitch > 0) {
                        mc.player.motionX = dir[0];
                        mc.player.motionZ = dir[1];

                    }
                    if (mc.player.rotationPitch < 0) {
                        mc.player.motionX -= MathHelper.sin(yaw) * .08 / 10;
                        mc.player.motionZ += MathHelper.cos(yaw) * .08 / 10;
                    }

                }

            }

            if (boost.getValBoolean()) {
                if (mc.player.rotationPitch < 0) {
                    mc.player.motionX -= MathHelper.sin(yaw) * .08 / 10;
                    mc.player.motionZ += MathHelper.cos(yaw) * .08 / 10;
                }
            }




                if (glide.getValBoolean()) {
                mc.player.motionY = -(glideSpeed.getValDouble() / 10000);
            }

        }
        if (autoTakeoff.getValBoolean()) {
            if (mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA && mc.gameSettings.keyBindJump.isKeyDown() && !mc.player.isElytraFlying()) {
                sendPacketDelay++;
                if (sendPacketDelay > 5) {
                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                    sendPacketDelay = 0;
                    //
                }
            }
        }

        if (mc.player.isElytraFlying() && mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.player.motionY = -.51;
        }


        if (!flyMode.getValString().equalsIgnoreCase("2b"))  {
        if (mc.player.isElytraFlying() && !mc.gameSettings.keyBindForward.isKeyDown() && !mc.gameSettings.keyBindRight.isKeyDown() && !mc.gameSettings.keyBindLeft.isKeyDown() && !mc.gameSettings.keyBindBack.isKeyDown() && !mc.gameSettings.keyBindSneak.isKeyDown() && !mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
            if (noGlideAFK.getValBoolean()) {
                mc.player.motionY = 0;
            }
        }


            //
            //
        }

    }
    /*
        thx ionar
    */
    private float GetRotationYawForCalc() {
        float rotationYaw = mc.player.rotationYaw;
        if (mc.player.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float n = 1.0f;
        if (mc.player.moveForward < 0.0f) {
            n = -0.5f;
        }
        else if (mc.player.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (mc.player.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (mc.player.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }


    @Override
    public String getHudInfo() {
        return "[" + ChatFormatting.WHITE + flyMode.getValString() + ChatFormatting.GRAY + "]";
    }
    //
}
