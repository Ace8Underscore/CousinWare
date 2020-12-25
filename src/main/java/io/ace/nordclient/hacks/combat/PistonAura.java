package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.hacks.Hack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class PistonAura extends Hack {

    Vec3d startPos;

    public PistonAura() {
        super("PistonAura", Category.COMBAT, 13632022);
    }

    public void onUpdate() {

    }

    public void onEnable() {
        startPos = mc.objectMouseOver.hitVec;
        BlockPos placePistonPos = new BlockPos(startPos.x, startPos.y, startPos.z);
        mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(placePistonPos, EnumFacing.SOUTH, EnumHand.MAIN_HAND, 0, 0, 0));
    }
}
