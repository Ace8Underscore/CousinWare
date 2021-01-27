package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class IllegalFinder extends Hack {

    public static boolean illegal = false;
    public static BlockPos illegalPos;
    int delay2 = 0;

    Setting range;
    Setting delay;

    public IllegalFinder() {
        super("IllegalFinder", Category.MISC, 15795671);
        CousinWare.INSTANCE.settingsManager.rSetting(range = new Setting("Range", this, 60, 15, 100, true, "IllegalFinderRange"));
        CousinWare.INSTANCE.settingsManager.rSetting(delay = new Setting("Delay", this, 100, 0, 200, true, "IllegalFinderDelay"));

    }

    @Override
    public void onUpdate() {
        delay2++;
       /* if (illegal) {
            if (illegalPos != null) {//
                if (delay > 200) {
                    Command.sendClientSideMessage("ILLEGAL BEDROCK FOUND AT :" + illegalPos.getX() + " " + illegalPos.getX() + " " + illegalPos.getZ());
                    delay = 0;
                }
            }
        } */
        if (delay2 > delay.getValInt()) {
            for (BlockPos pos : BlockPos.getAllInBox((int) mc.player.getPositionVector().x - range.getValInt(), 0, (int) mc.player.getPositionVector().z - range.getValInt(), (int) mc.player.getPositionVector().x + range.getValInt(), 256, (int) mc.player.getPositionVector().z + range.getValInt())) {
                if (mc.player.dimension == 0) {
                    if (mc.world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK) && pos.getY() > 5) {
                            Command.sendClientSideMessage("ILLEGAL BEDROCK FOUND AT :" + pos.getX() + " " + pos.getY() + " " + pos.getZ());

                    }

                }
                if (mc.player.dimension == 1) {
                    if (mc.world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK) && pos.getY() > 5 && pos.getY() != 124 && pos.getY() != 125 && pos.getY() != 126 && pos.getY() != 127 && pos.getY() != 128) {
                            Command.sendClientSideMessage("ILLEGAL BEDROCK FOUND AT :" + pos.getX() + " " + pos.getY() + " " + pos.getZ());

                    }
                }
                if (mc.player.dimension == 2) {
                    if (mc.world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK)) {
                            Command.sendClientSideMessage("ILLEGAL BEDROCK FOUND AT :" + pos.getX() + " " + pos.getY() + " " + pos.getZ());
                    }
                }
            }
            delay2 = 0;
        }
    }

}
