package io.ace.nordclient.event;

import net.minecraft.entity.Entity;

public class EventPlayerApplyCollision extends EventCancellable {
    public Entity entity;
    
    public EventPlayerApplyCollision(Entity p_Entity) {

        entity = p_Entity;
    }
}