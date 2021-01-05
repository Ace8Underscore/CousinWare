package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.mixin.accessor.ISPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;

@Mixin(SPacketPlayerPosLook.class)
public abstract class MixinSPacketPlayerPosLook implements ISPacketPlayerPosLook {


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
    protected Set<SPacketPlayerPosLook.EnumFlags> flags;

    @Shadow
    protected int teleportId;

    @Override
    public Set<SPacketPlayerPosLook.EnumFlags> getFlags() { return this.flags; }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getZ() {
        return this.z;
    }

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
    public float getYaw() {return this.yaw;}

    @Override
    public float getPitch() {return this.pitch;}

    @Override
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    @Override
    public void setPitch(float pitch) { this.pitch = pitch;}

    @Override
    public int getTeleportId() {
        return teleportId;
    }
}
