package io.ace.nordclient.hacks.player;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.AddCollisionBoxToListEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

/**
 * @author Ace________/Ace_#1233
 */

public class AntiVoid extends Hack {

    Setting downMode;
    private static final AxisAlignedBB voidFloat = new AxisAlignedBB(0.D, 0.D, 0.D, 1.D, 1.D, 1.D);


    public AntiVoid() {
        super("AntiVoid", Category.PLAYER, 8263634);
        ArrayList<String> downModes = new ArrayList<>();
        downModes.add("LagBack");
        downModes.add("Strict");
        downModes.add("Float");
        CousinWare.INSTANCE.settingsManager.rSetting(downMode = new Setting("Mode", this, "lagBack", downModes, "AntiVoidDownMode"));

    }

    @Override
    public void onUpdate() {
        Double yLevel = mc.player.posY;
        if (downMode.getValString().equalsIgnoreCase("lagBack")) {
        if (yLevel <=.5) {
            mc.player.jump();
            //Command.sendClientSideMessage("Attempting to get " + mc.player.getName() + " Out of the Void!");
             }
            }
        if (downMode.getValString().equalsIgnoreCase("strict")) {
            if (yLevel <=.9) {
                mc.player.jump();
                //Command.sendClientSideMessage("Attempting to get " + mc.player.getName() + " Out of the Void!");
            }
        }
        }

    @Listener
    public void AddCollisionToBlock(AddCollisionBoxToListEvent event) {
        if (downMode.getValString().equalsIgnoreCase("float")) {
            if (event.getBlock().equals(Blocks.AIR)) {
                if (event.getPos().getY() == 0) {
                    AxisAlignedBB axisalignedbb = voidFloat.offset(event.getPos());
                    if (event.getEntityBox().intersects(axisalignedbb)) event.getCollidingBoxes().add(axisalignedbb);
                    event.setCanceled(true);
                }
            }
        }
    }
}
