package io.ace.nordclient.event;

import net.minecraft.util.EnumFacing;

public class EventPlayerDamageBlock extends EventCancellable {
    private final net.minecraft.util.math.BlockPos BlockPos;
    private EnumFacing Direction;

    public EventPlayerDamageBlock(net.minecraft.util.math.BlockPos posBlock, EnumFacing directionFacing) {
        BlockPos = posBlock;
        setDirection(directionFacing);
    }

    public net.minecraft.util.math.BlockPos getPos()
    {
        return BlockPos;
    }

    /**
     * @return the direction
     */
    public EnumFacing getDirection()
    {
        return Direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(EnumFacing direction)
    {
        Direction = direction;
    }

}