package io.ace.nordclient.hacks.movement;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.AddCollisionBoxToListEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.mixin.accessor.IEntity;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

/**
 * @author Ace________/Ace_#1233
 */

public class FastWeb extends Hack {

    public static Setting downMode;
    public static Setting speed;

    private static final AxisAlignedBB webFloat = new AxisAlignedBB(0.D, 0.D, 0.D, 1.D, 0.999D, 1.D);

    boolean collided;
    int time = 0;
    int delay = 0;

    public FastWeb() {
        super("FastWeb", Category.MOVEMENT, 13032102);

        ArrayList<String> downModes = new ArrayList<>();
        downModes.add("2b");
        downModes.add("Packet");
        downModes.add("Timer");
        downModes.add("Other");
        downModes.add("Float");
        CousinWare.INSTANCE.settingsManager.rSetting(downMode = new Setting("DownMode", this, "float", downModes, "FastWebDownMode"));
        CousinWare.INSTANCE.settingsManager.rSetting(speed = new Setting("Speed", this, 10, 1, 50, false, "FastWebSpeed"));

    }

    @Override
    public void onUpdate() {
        if (mc.world == null || mc.player == null)
            return;

        BlockPos currentPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);


        if (((IEntity) mc.player).getIsInWeb()) {
            delay++;
            if (downMode.getValString().equalsIgnoreCase("2b")) {
                mc.player.motionY = 1.1 / -5;

            }
            if (downMode.getValString().equalsIgnoreCase("Other")) {
                mc.player.motionY = 20.7 / -5;
            }
            if (downMode.getValString().equalsIgnoreCase("Packet")) {
                if (!mc.player.collidedHorizontally && !mc.player.collidedVertically && !(time >= 9)) {
                    time++;
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ - .1, mc.player.onGround));
                    mc.player.setPosition(mc.player.posX, mc.player.posY - .09, mc.player.posZ);
                }
            }
            if (downMode.getValString().equalsIgnoreCase("Packet2")) {

                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ - .2, mc.player.onGround));
                mc.player.motionY = 5.5 / -5;

            }
            if (downMode.getValString().equalsIgnoreCase("Timer")) {
                if (!(mc.player.onGround)) {
                    if (delay > 5) {
                     //   ((ITimer)mc.timer).setTickLength(50f / (float) speed.getValDouble());
                        mc.player.motionX *= .0001;
                        mc.player.motionZ *= .0001;

                    }
                } else {
                  //  ((ITimer)mc.timer).setTickLength(50f);
                    delay = 0;
                }


            }
        } else {
           // ((ITimer)mc.timer).setTickLength(50f);
            delay = 0;
        }
        if (!downMode.getValString().equalsIgnoreCase("Timer")) {
           // if (!(((ITimer)mc.timer).getTickLength() == 50)) {
               // ((ITimer)mc.timer).setTickLength(50f);
           // }
        }

    }
    @Listener
    public void AddCollisionToBlock(AddCollisionBoxToListEvent event) {
    if (downMode.getValString().equalsIgnoreCase("float")) {
        if (event.getBlock().equals(Blocks.WEB)) {
            AxisAlignedBB axisalignedbb = webFloat.offset(event.getPos());
            if (event.getEntityBox().intersects(axisalignedbb)) event.getCollidingBoxes().add(axisalignedbb);
            event.setCanceled(true);
        }
        }
    }


    @Override
    public String getHudInfo() {
        return  "\u00A77[\u00A7f" + downMode.getValString() + "\u00A77]";
    }

    @Override
    public void onEnable() {
        collided = false;
    }

    @Override
    public void onDisable() {
      ///  if (!(((ITimer)mc.timer).getTickLength() == 50)) {
           // ((ITimer)mc.timer).setTickLength(50f);
         //   }
        }

}
