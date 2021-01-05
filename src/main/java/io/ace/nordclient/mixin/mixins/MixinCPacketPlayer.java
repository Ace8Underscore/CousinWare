package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.mixin.accessor.ICPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CPacketPlayer.class)
public abstract class MixinCPacketPlayer implements ICPacketPlayer {

    @Shadow
    protected double x;
    @Shadow
    protected double y;
    @Shadow
    protected double z;

    @Shadow
    protected float yaw;
    @Shadow
    protected float pitch;



    @Shadow
    protected boolean onGround;

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    @Override
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    @Override
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

}
