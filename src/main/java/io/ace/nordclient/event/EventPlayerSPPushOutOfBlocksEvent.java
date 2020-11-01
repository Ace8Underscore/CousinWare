package io.ace.nordclient.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nonnull;

public class EventPlayerSPPushOutOfBlocksEvent extends EventCancellable {
    private AxisAlignedBB entityBoundingBox;

    public EventPlayerSPPushOutOfBlocksEvent(EntityPlayer player, AxisAlignedBB entityBoundingBox) {
        super();
        this.entityBoundingBox = entityBoundingBox;
    }

    public AxisAlignedBB getEntityBoundingBox() {
        return this.entityBoundingBox;
    }

    public void setEntityBoundingBox(@Nonnull AxisAlignedBB entityBoundingBox) {
        this.entityBoundingBox = entityBoundingBox;
    }
}