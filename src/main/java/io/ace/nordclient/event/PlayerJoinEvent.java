package io.ace.nordclient.event;

public class PlayerJoinEvent extends EventCancellable {
    private final String name;

    public PlayerJoinEvent(String n){
        super();
        name = n;
    }

    public String getName(){
        return name;
    }
}
