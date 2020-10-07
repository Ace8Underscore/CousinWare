package io.ace.nordclient.event;

public class PlayerLeaveEvent extends EventCancellable {

    private final String name;

    public PlayerLeaveEvent(String n){
        super();
        name = n;
    }

    public String getName(){
        return name;
    }


}
