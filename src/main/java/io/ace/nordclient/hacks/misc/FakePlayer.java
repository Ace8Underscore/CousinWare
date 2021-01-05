package io.ace.nordclient.hacks.misc;

import com.mojang.authlib.GameProfile;
import io.ace.nordclient.hacks.Hack;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.UUID;

public class FakePlayer extends Hack {

    public FakePlayer() {
        super("FakePlayer", Category.MISC, 3119447);
    }

    EntityOtherPlayerMP entity;

    public void onEnable() {
        entity = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("d8d5a923-7b20-43d8-883b-1150148d6955"), "Test"));
        entity.copyLocationAndAnglesFrom(mc.player);
        entity.rotationYaw = mc.player.rotationYaw;
        entity.rotationYawHead = mc.player.rotationYawHead;
        mc.world.addEntityToWorld(696984837, entity);
    }

    public void onDisable() {
        if (mc.world.loadedEntityList.contains(entity)) {
            mc.world.removeEntity(entity);
        }
    }
}
