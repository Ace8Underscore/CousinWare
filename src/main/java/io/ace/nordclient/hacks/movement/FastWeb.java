package io.ace.nordclient.hacks.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.AddCollisionBoxToListEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.mixin.accessor.ICPacketPlayer;
import io.ace.nordclient.mixin.accessor.IEntity;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

/**
 * @author Ace________/Ace_#1233
 */

public class FastWeb extends Hack {

    Setting downMode;

    private static final AxisAlignedBB webFloat = new AxisAlignedBB(0.D, 0.D, 0.D, 1.D, 0.999D, 1.D);

    public FastWeb() {
        super("FastWeb", Category.MOVEMENT, 13032102);

        ArrayList<String> downModes = new ArrayList<>();
        downModes.add("2b");
        downModes.add("Other");
        downModes.add("Float");
        CousinWare.INSTANCE.settingsManager.rSetting(downMode = new Setting("DownMode", this, "float", downModes, "FastWebDownMode"));

    }

    @Override
    public void onUpdate() {
        if (mc.world == null || mc.player == null)
            return;

        if (((IEntity) mc.player).getIsInWeb()) {
            if (downMode.getValString().equalsIgnoreCase("2b")) {
                mc.player.motionY = 1.1 / -5;
            }
            if (downMode.getValString().equalsIgnoreCase("Other")) {
                mc.player.motionY = 20.7 / -5;
            }

        }

    }
    @Listener
    public void AddCollisionToBlock(AddCollisionBoxToListEvent event) {
    if (downMode.getValString().equalsIgnoreCase("float")) {
        if (event.getBlock().equals(Blocks.WEB)) {
            //mc.player.posY = mc.player.posY + .1;
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
}
