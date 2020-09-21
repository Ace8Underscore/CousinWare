package io.ace.nordclient.event;

import net.minecraft.util.math.BlockPos;

public class DestroyBlockEvent {
    BlockPos pos;
    public DestroyBlockEvent(BlockPos blockPos){
        pos = blockPos;
    }

    public BlockPos getBlockPos(){
        return pos;
    }
}
