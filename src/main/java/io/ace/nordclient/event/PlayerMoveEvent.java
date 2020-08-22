package io.ace.nordclient.event;

import net.minecraft.entity.MoverType;

public class PlayerMoveEvent {
    MoverType type;
    public double x;
    public double y;
    public double z;
    public PlayerMoveEvent(MoverType moverType, double xx, double yy, double zz){
        type = moverType;
        x = xx;
        y = yy;
        z =zz;
    }

    public MoverType getType(){
        return type;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getZ(){
        return z;
    }

    public void setX(double xx){
        x = xx;
    }

    public void setY(double yy){
        y = yy;
    }

    public void setZ(double zz){
        z = zz;
    }
}
