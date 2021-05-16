package io.ace.nordclient.mixin.accessor;

import net.minecraft.network.play.client.CPacketUseEntity;

public interface ICPacketUseEntity {

    void setEntityId(int entityId1);

    void setEntityAction(CPacketUseEntity.Action action1);
}
