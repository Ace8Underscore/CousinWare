package io.ace.nordclient.mixin;

import io.ace.nordclient.NordClient;
import io.ace.nordclient.event.EventStageable;

import io.ace.nordclient.event.PlayerMoveEvent;
import io.ace.nordclient.event.UpdateEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.MoverType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayerSP.class, priority = 998)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer {
    public MixinEntityPlayerSP() {
        super(null, null);
    }

    @Redirect(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;move(Lnet/minecraft/entity/MoverType;DDD)V"))
    public void move(AbstractClientPlayer player, MoverType type, double x, double y, double z) {
        PlayerMoveEvent moveEvent = new PlayerMoveEvent(type, x, y, z);
        NordClient.INSTANCE.getEventManager().dispatchEvent(moveEvent);
        super.move(type, moveEvent.x, moveEvent.y, moveEvent.z);
    }

    @Inject(method = "onUpdate()V", at = @At(value = "FIELD", target = "net/minecraft/client/entity/EntityPlayerSP.connection:Lnet/minecraft/client/network/NetHandlerPlayClient;", ordinal = 0, shift = At.Shift.BEFORE))
    public void onUpdatePre(CallbackInfo ci) { //support for haram pigs: makes it so that the event still runs when riding entities, or bad shit will happen lol
        UpdateEvent event = new UpdateEvent(EventStageable.EventStage.PRE, this.rotationYaw, this.rotationPitch, this.posX, this.getEntityBoundingBox().minY, this.posZ, this.onGround);
        NordClient.INSTANCE.getEventManager().dispatchEvent(event);
        if (event.isCanceled()) ci.cancel();
    }

    @Inject(method = "onUpdate()V", at = @At(value = "INVOKE", target = "net/minecraft/client/network/NetHandlerPlayClient.sendPacket(Lnet/minecraft/network/Packet;)V", ordinal = 0, shift = At.Shift.AFTER))
    public void onUpdatePost(CallbackInfo ci) {
        UpdateEvent event = new UpdateEvent(EventStageable.EventStage.POST, this.rotationYaw, this.rotationPitch, this.posX, this.getEntityBoundingBox().minY, this.posZ, this.onGround);
        NordClient.INSTANCE.getEventManager().dispatchEvent(event);

    }


    @Inject(method = "onUpdate()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;onUpdateWalkingPlayer()V", ordinal = 0, shift = At.Shift.AFTER)) //death by sex
    public void onUpdateElse(CallbackInfo ci) {
        UpdateEvent event = new UpdateEvent(EventStageable.EventStage.POST, this.rotationYaw, this.rotationPitch, this.posX, this.getEntityBoundingBox().minY, this.posZ, this.onGround);
        NordClient.INSTANCE.getEventManager().dispatchEvent(event);
    }




}
