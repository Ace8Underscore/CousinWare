package io.ace.nordclient.event;

public class EventPlayerTravel extends EventCancellable{
    public float Strafe;
    public float Vertical;
    public float Forward;

    public EventPlayerTravel(float p_Strafe, float p_Vertical, float p_Forward) {
        Strafe = p_Strafe;
        Vertical = p_Vertical;
        Forward = p_Forward;
    }
}