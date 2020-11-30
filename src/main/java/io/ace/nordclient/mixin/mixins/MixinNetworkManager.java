package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.PacketEvent;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager {

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo callbackInfo) {
        PacketEvent.Send event = new PacketEvent.Send(packet);
        CousinWare.INSTANCE.getEventManager().dispatchEvent(event);
        if (event.isCanceled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void onChannelRead(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callbackInfo) {
        PacketEvent.Receive event = new PacketEvent.Receive(packet);
        CousinWare.INSTANCE.getEventManager().dispatchEvent(event);
        if (event.isCanceled()) {
            callbackInfo.cancel();
        }
    }

}
