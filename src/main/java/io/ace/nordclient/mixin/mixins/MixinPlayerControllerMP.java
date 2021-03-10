package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.DestroyBlockEvent;
import io.ace.nordclient.event.EventPlayerClickBlock;
import io.ace.nordclient.event.EventPlayerDamageBlock;
import io.ace.nordclient.event.EventPlayerResetBlockRemoving;
import io.ace.nordclient.managers.HackManager;
import io.ace.nordclient.mixin.accessor.IPlayerControllerMP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public abstract class MixinPlayerControllerMP implements IPlayerControllerMP {
    float range;

 /*   @Accessor @Override public abstract void setBlockHitDelay(int delay);
    @Accessor @Override public abstract void setIsHittingBlock(boolean hittingBlock);
    @Accessor @Override public abstract float getCurBlockDamageMP();
    @Accessor @Override public abstract void setCurBlockDamageMP(float blockDamageMP); */


    @Shadow
    public float curBlockDamageMP;

    @Shadow public boolean isHittingBlock;

    @Accessor @Override public abstract void setIsHittingBlock(boolean hittingBlock);
    @Accessor @Override public abstract float getCurBlockDamageMP();
    @Accessor @Override public abstract void setCurBlockDamageMP(float blockDamageMP);

    @Inject(method = "onPlayerDestroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playEvent(ILnet/minecraft/util/math/BlockPos;I)V"), cancellable = true)
    private void onPlayerDestroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        CousinWare.INSTANCE.getEventManager().dispatchEvent(new DestroyBlockEvent(pos));
    }

    @Inject(method = "resetBlockRemoving", at = @At("HEAD"), cancellable = true)
    public void resetBlockRemoving(CallbackInfo p_Info) {
        EventPlayerResetBlockRemoving l_Event = new EventPlayerResetBlockRemoving();
        CousinWare.INSTANCE.getEventManager().dispatchEvent(l_Event);
        if (l_Event.isCanceled() || HackManager.getHackByName("NoBreakLoss").isEnabled()) {
            p_Info.cancel();

        }
    }

    @Inject(method = "clickBlock", at = @At("HEAD"), cancellable = true)
    public void clickBlock(BlockPos loc, EnumFacing face, CallbackInfoReturnable<Boolean> callback) {
        EventPlayerClickBlock l_Event = new EventPlayerClickBlock(loc, face);

        CousinWare.INSTANCE.getEventManager().dispatchEvent(l_Event);
        if (l_Event.isCanceled()) {
            callback.setReturnValue(false);
            callback.cancel();
        }
    }

    @Inject(method = "onPlayerDamageBlock", at = @At("HEAD"), cancellable = true)
    public void onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> p_Info) {
        EventPlayerDamageBlock l_Event = new EventPlayerDamageBlock(posBlock, directionFacing);
        CousinWare.INSTANCE.getEventManager().dispatchEvent(l_Event);
        if (l_Event.isCanceled()) {
            p_Info.setReturnValue(false);
            p_Info.cancel();
        }
    }

}