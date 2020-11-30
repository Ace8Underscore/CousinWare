package io.ace.nordclient.hacks.movement;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.network.play.client.CPacketPlayer;

import java.util.ArrayList;

/**
 * @author Ace________/Ace_#1233
 */

public class Step extends Hack {

    Setting speed;
    Setting block;
    Setting toggleOnStep;
    Setting stepOn;

    public Step() {
        super("Step", Category.MOVEMENT, 11731844);
        ArrayList<String> blocks = new ArrayList<>();
        blocks.add("2");
        blocks.add("3");
        blocks.add("4");
        CousinWare.INSTANCE.settingsManager.rSetting(block = new Setting("Height", this, "2", blocks, "StepBlock"));

        ArrayList<String> stepOns = new ArrayList<>();
        stepOns.add("Collide");
        stepOns.add("Jump");
        CousinWare.INSTANCE.settingsManager.rSetting(stepOn = new Setting("StepOn", this, "Collide", stepOns, "StepStepOns"));

        CousinWare.INSTANCE.settingsManager.rSetting(toggleOnStep = new Setting("ToggleStep", this, true, "StepToggleOnStop"));

    }


    @Override
    public void onUpdate() {

        if (stepOn.getValString().equalsIgnoreCase("collide") && mc.player.collidedVertically && mc.player.collidedHorizontally) {
            doVelocity();
        }
        if (stepOn.getValString().equalsIgnoreCase("jump") && mc.gameSettings.keyBindJump.isPressed()) {
            doVelocity();
        }
    }

    public void doVelocity() {
        if (block.getValString().equalsIgnoreCase("2")) {
            mc.player.setVelocity(.0, .56, .0);
           /* mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.22, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - .0927491, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.42, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - .0927491, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.88, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - .09175481, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.73, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - .09175491, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.61, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - .0918581, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.0, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - .0917481, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.31, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - .091713, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.55, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - .0815711, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.53, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - .9104814, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.66, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - .1, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.71, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - .129105, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.51, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - .091581851, mc.player.posZ, mc.player.onGround));
*/

            if (toggleOnStep.getValBoolean()) this.toggle();
        }
        if (block.getValString().equalsIgnoreCase("3")) {
            //mc.player.setVelocity(.01, .7, .01);

            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.41999998688698, mc.player.posZ, mc.player.onGround));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.7631999805212, mc.player.posZ, mc.player.onGround));
            mc.player.setPosition(mc.player.posX, mc.player.posY + 1.15, mc.player.posZ);
            //mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.41999998688698, mc.player.posZ, mc.player.onGround));
            //mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.7631999805212, mc.player.posZ, mc.player.onGround));
            //mc.player.setPosition(mc.player.posX, mc.player.posY + .1, mc.player.posZ);
             if (toggleOnStep.getValBoolean()) this.toggle();
        }
        if (block.getValString().equalsIgnoreCase("4")) {
            mc.player.setVelocity(.01, .84, .01);
            if (toggleOnStep.getValBoolean()) this.toggle();
        }

    }
}