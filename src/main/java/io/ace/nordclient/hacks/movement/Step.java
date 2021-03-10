package io.ace.nordclient.hacks.movement;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;

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
        stepOns.add("Vanilla");
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
        if (stepOn.getValString().equals("Vanilla")){
            mc.player.stepHeight=mc.player.onGround?2.0f:0.6f;
        }
    }

    @Override
    public void onDisable(){
        mc.player.stepHeight=0.6f;
    }

    public void doVelocity() {
        if (block.getValString().equalsIgnoreCase("2")) {
            mc.player.setVelocity(.0, .56, .0);

            if (toggleOnStep.getValBoolean()) this.toggle();
        }
        if (block.getValString().equalsIgnoreCase("3")) {
            mc.player.setVelocity(.01, .7, .01);
             if (toggleOnStep.getValBoolean()) this.toggle();
        }
        if (block.getValString().equalsIgnoreCase("4")) {
            mc.player.setVelocity(.01, .84, .01);
            if (toggleOnStep.getValBoolean()) this.toggle();
        }

    }
}