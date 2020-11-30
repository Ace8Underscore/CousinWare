package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.FriendManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;

public class MCF extends Hack {

    public MCF() {
        super("MCF", Category.MISC, 15756204);

    }

    private int delay = 0;

    @Override
    public void onUpdate() {
        delay++;
        RayTraceResult object = mc.objectMouseOver;
        if(object == null) {
            return;
        }
        if(object.typeOfHit == RayTraceResult.Type.ENTITY) {
            Entity entity = object.entityHit;
            if(entity instanceof EntityPlayer && !(entity instanceof EntityArmorStand) && !mc.player.isDead && mc.player.canEntityBeSeen(entity)){
                EntityPlayer player = (EntityPlayer)entity;
                String ID = entity.getName();

                if(Mouse.isButtonDown(2) && mc.currentScreen == null && !FriendManager.isFriend(ID) && delay > 10) {
                    FriendManager.addFriend(ID);
                    delay = 0;
                }
                if(Mouse.isButtonDown(2) && mc.currentScreen == null && FriendManager.isFriend(ID) && delay > 10) {
                    FriendManager.removeFriend(ID);
                    delay = 0;
//
                }
            }
        }

    }

}


