package io.ace.nordclient.event;

public class EventPlayerPushOutOfBlocks extends EventCancellable {
    public double X, Y, Z;
    
    public EventPlayerPushOutOfBlocks(double X, double Y, double Z) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }
}