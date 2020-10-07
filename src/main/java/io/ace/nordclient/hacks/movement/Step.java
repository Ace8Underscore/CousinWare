package io.ace.nordclient.hacks.movement;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

public class Step extends Hack {

    Setting speed;
    Setting block;
    Setting toggleOnStep;
    Setting stepOn;

    public Step() {
        super("Step", Category.MOVEMENT);
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


    @Listener
    public void onUpdate() {

        if (stepOn.getValString().equalsIgnoreCase("collide") && mc.player.collidedVertically && mc.player.collidedHorizontally) {
            doVelocity();
        }
        if(stepOn.getValString().equalsIgnoreCase("jump") && mc.gameSettings.keyBindJump.isPressed()) {
            doVelocity();
        }
    }

    public void doVelocity() {
        if (block.getValString().equalsIgnoreCase("2")) {
            mc.player.setVelocity(.01, .56, .01);
            if(toggleOnStep.getValBoolean()) this.toggle();
        }
        if (block.getValString().equalsIgnoreCase("3")) {
            mc.player.setVelocity(.01, .7, .01);
            if(toggleOnStep.getValBoolean()) this.toggle();
        }
        if (block.getValString().equalsIgnoreCase("4")) {
            mc.player.setVelocity(.01, .84, .01);
            if(toggleOnStep.getValBoolean()) this.toggle();
        }

    }
   /* public static void damagePlayer(int damage) {
        /* capping it just in case anybody has an autism attack */
     /*   if (damage < 1)
            damage = 1;
        if (damage > MathHelper.floor(mc.player.getMaxHealth()))
            damage = MathHelper.floor(mc.player.getMaxHealth());

        double offset = 0.0625;
        if (mc.player != null && mc.getConnection() != null && mc.player.onGround) {
            for (int i = 0; i <= ((3 + damage) / offset); i++) { // TODO: teach rederpz (and myself) how math works
                mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + offset, mc.player.posZ, false));
                mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + offset, mc.player.posZ, (i == ((3 + damage) / offset))));
                //MINECRAFT.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MINECRAFT.thePlayer.posX, MINECRAFT.thePlayer.posY + offset, MINECRAFT.thePlayer.posZ, false));
                //MINECRAFT.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MINECRAFT.thePlayer.posX, MINECRAFT.thePlayer.posY, MINECRAFT.thePlayer.posZ, (i == ((3 + damage) / offset))));
            }
        }
    } */

    public void onEnable() {
        //damagePlayer(1);
      /*  for (int i = 0; i < 81; i++) {
            mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.01D, mc.player.posZ, false));
            mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
*/

        }


}
