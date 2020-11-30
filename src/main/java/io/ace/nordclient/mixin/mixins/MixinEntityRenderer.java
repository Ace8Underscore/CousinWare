package io.ace.nordclient.mixin.mixins;

import com.google.common.base.Predicate;
import io.ace.nordclient.hacks.misc.NoEntityTrace;
import io.ace.nordclient.managers.HackManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = EntityRenderer.class, priority = 2147483647)
public class MixinEntityRenderer {

    @Shadow @Final public Minecraft mc;

    @Redirect(method = "getMouseOver", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
    public List<Entity> getEntitiesInAABBexcluding(WorldClient worldClient, Entity entityIn, AxisAlignedBB boundingBox, Predicate predicate) {
        if (((NoEntityTrace) HackManager.getHackByName("NoEntityTrace")).enabled && mc.player.getHeldItemMainhand().getItem().equals(Items.DIAMOND_PICKAXE)) {
            return new ArrayList<>();
        } else {
            return worldClient.getEntitiesInAABBexcluding(entityIn, boundingBox, predicate);
        }
        //
    }

}
