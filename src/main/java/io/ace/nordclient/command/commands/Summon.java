package io.ace.nordclient.command.commands;

import io.ace.nordclient.command.Command;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.EnumParticleTypes;

public class Summon extends Command{


    /**
     * @author Ace________/Ace_#1233
     */

    @Override
    public String[] getClientAlias() { return new String[] {"Summon"};}

    @Override
    public String getClientSyntax() { return "Summon (helditem/entity/particle) (amount/id)"; }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        if (args[0].equalsIgnoreCase("helditem")) {
            mc.player.dropItem(mc.player.getHeldItemMainhand().getItem(), Integer.parseInt(args[1]));
        }
        if (args[0].equalsIgnoreCase("entity")) {
            EntityArmorStand fakePlayer = new EntityArmorStand(mc.world);
            mc.world.addEntityToWorld(1, fakePlayer);
        }
        if (args[0].equalsIgnoreCase("particle")) {
            double x = mc.player.posX;
            double y = mc.player.posY;
            double z = mc.player.posZ;

            mc.world.spawnParticle(EnumParticleTypes.getParticleFromId(Integer.parseInt(args[1])), x, y + 2, z, 0, .5, 0, 10000);
        }

        if (!args[0].equalsIgnoreCase("helditem") && !args[0].equalsIgnoreCase("entity") && !args[0].equalsIgnoreCase("particle")) {
            Command.sendClientSideMessage(getClientSyntax());
        }

        }

//


    }

