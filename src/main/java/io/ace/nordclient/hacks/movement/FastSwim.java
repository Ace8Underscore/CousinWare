package io.ace.nordclient.hacks.movement;

import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import net.minecraft.util.math.MathHelper;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class FastSwim extends Hack {

    /**
     * @author Ace________/Ace_#1233
     */

    public FastSwim() {
        super("FastSwim", Category.MOVEMENT);

    }
    int divider = 5;
    boolean only2b = true;
    boolean sprint = true;
    boolean water = false;
    boolean lava = true;
    boolean up = true;
    boolean down = true;
    @Listener
        public void onUpdate(UpdateEvent event) {
            if (only2b) {
                if (!mc.isSingleplayer()) {
                    if (mc.getCurrentServerData().serverIP.equalsIgnoreCase("2b2t.org")) {

                        if (sprint) {
                            if (mc.player.isInLava() || mc.player.isInWater()) {
                                mc.player.setSprinting(true);
                            }
                        }

                        if (mc.player.isInWater() || mc.player.isInLava()) {
                            if (mc.gameSettings.keyBindJump.isKeyDown() && up) {
                                mc.player.motionY = .725 / divider;
                            }
                        }
                        if (mc.player.isInWater() && water && !mc.player.onGround) {
                            if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
                                final float yaw = GetRotationYawForCalc();
                                //mc.player.motionX -= MathHelper.sin(yaw) * .25 / 10;
                                //mc.player.motionZ += MathHelper.cos(yaw) * .25 / 10;
                            }
                        }

                        if (mc.player.isInLava() && lava && !mc.player.onGround) {
                            if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
                                final float yaw = GetRotationYawForCalc();
                                //mc.player.motionX -= MathHelper.sin(yaw) * .7 / 10;
                               // mc.player.motionZ += MathHelper.cos(yaw) * .7 / 10;
                            }
                        }


                        if (mc.player.isInWater() && down) {
                            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                                int divider2 = divider * -1;
                                mc.player.motionY = 2.2 / divider2;
                            }
                        }
                        if (mc.player.isInLava() && down) {
                            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                                int divider2 = divider * -1;
                                mc.player.motionY = .91 / divider2;
                            }
                            //
                        }
                        if (!mc.player.isInWater() && !mc.player.isInLava()) {
                            //mc.player.motionX -= 0;
                            //mc.player.motionZ += 0;
                        }
                    }
                }
            }
            ///
            if (!only2b) {
                if (sprint) {
                    if (mc.player.isInLava() || mc.player.isInWater()) {
                        mc.player.setSprinting(true);
                    }
                }

                if (mc.player.isInWater() || mc.player.isInLava()) {
                    if (mc.gameSettings.keyBindJump.isKeyDown() && up) {
                        mc.player.motionY = .725 / divider;
                    }
                }
                if (mc.player.isInWater() && water) {
                    if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
                        final float yaw = GetRotationYawForCalc();
                        //mc.player.motionX -= MathHelper.sin(yaw) * .2 / 10;
                        //mc.player.motionZ += MathHelper.cos(yaw) * .2 / 10;
                    }
                }

                if (mc.player.isInLava() && lava && !mc.player.onGround) {
                    if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
                        final float yaw = GetRotationYawForCalc();
                        //mc.player.motionX -= MathHelper.sin(yaw) * .7 / 10;
                        //mc.player.motionZ += MathHelper.cos(yaw) * .7 / 10;
                    }
                }

                if (mc.player.isInWater() && down && !mc.player.onGround) {
                    if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                        int divider2 = divider * -1;
                        mc.player.motionY = 2.2 / divider2;
                    }
                }
                if (mc.player.isInLava() && down) {
                    if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                        int divider2 = divider * -1;
                        mc.player.motionY = .91 / divider2;
                    }
                }
                if (!mc.player.isInWater() && !mc.player.isInLava()) {
                    //mc.player.motionX -= 0;
                   // mc.player.motionZ += 0;
                }
            }
        }

    /**
     * @author ionar
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
    }


