package io.ace.nordclient.command.commands;

import io.ace.nordclient.command.Command;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.network.play.client.CPacketEntityAction;

public class RideEntity extends Command {
    @Override
    public String[] getClientAlias() {
        return new String[]{"ride"};
    }

    @Override
    public String getClientSyntax() {
        return "Ride";
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        for (Entity entity : mc.world.loadedEntityList)
            if (entity instanceof EntityDonkey) {
                //mc.getConnection().sendPacket(new CPacketUseEntity(CPacketUseEntity.Action.INTERACT, entity, EnumHand.MAIN_HAND, entity.getPositionVector()));
                mc.getConnection().sendPacket(new CPacketEntityAction(entity, CPacketEntityAction.Action.START_RIDING_JUMP, entity.getEntityId()));
                Command.sendClientSideMessage(String.valueOf(entity.getEntityId()));

            }
    }
}
