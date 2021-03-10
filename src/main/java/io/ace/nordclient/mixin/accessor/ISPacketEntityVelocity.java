package io.ace.nordclient.mixin.accessor;

public interface ISPacketEntityVelocity {

    void setMotionX(int x);

    void setMotionY(int y);

    void setMotionZ(int z);

    int getMotionX();

    int getMotionY();

    int getMotionZ();

    int getEntityID();
}
