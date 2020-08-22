package io.ace.nordclient.mixin;

import io.ace.nordclient.NordClient;


import io.ace.nordclient.event.EventPlayerTravel;
import io.ace.nordclient.event.PlayerJumpEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase {
    @Shadow public abstract String getName();

    public MixinEntityPlayer(World worldIn) {
        super(worldIn);
    }

    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    public void onJump(CallbackInfo ci) {
        if(Minecraft.getMinecraft().player.getName() == this.getName()){
            NordClient.INSTANCE.getEventManager().dispatchEvent(new PlayerJumpEvent());
        }
    }

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    public void travel(float strafe, float vertical, float forward, CallbackInfo info) {
        EventPlayerTravel event = new EventPlayerTravel(strafe, vertical, forward);
        NordClient.INSTANCE.getEventManager().dispatchEvent(new EventPlayerTravel(strafe, vertical, forward));
        if (event.isCanceled()) {
            move(MoverType.SELF, motionX, motionY, motionZ);
            info.cancel();
        }
    }
}
