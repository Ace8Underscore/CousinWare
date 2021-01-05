package io.ace.nordclient.mixin.accessor;

import net.minecraft.network.play.server.SPacketPlayerPosLook;

import java.util.Set;

public interface ISPacketPlayerPosLook {

    Set<SPacketPlayerPosLook.EnumFlags> getFlags();

    double getX();

    double getY();

    double getZ();

    void setX(double x);

    void setY(double y);

    void setZ(double z);

    float getYaw();

    float getPitch();

    void setYaw(float yaw);

    void setPitch(float pitch);

    int getTeleportId();
}
