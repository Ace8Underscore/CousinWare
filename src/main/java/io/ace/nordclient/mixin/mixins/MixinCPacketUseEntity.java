package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.mixin.accessor.ICPacketUseEntity;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CPacketUseEntity.class)
public abstract class MixinCPacketUseEntity implements ICPacketUseEntity {

    @Shadow
    protected int entityId;

    @Shadow
    protected CPacketUseEntity.Action action;

    @Shadow
    protected Vec3d hitVec;

    @Shadow
    protected EnumHand hand;

    @Override
    public void setEntityId(int entityId1) {
        entityId = entityId1;
    }

    @Override
    public void setEntityAction(CPacketUseEntity.Action action1) {
        action = action1;
    }
}
