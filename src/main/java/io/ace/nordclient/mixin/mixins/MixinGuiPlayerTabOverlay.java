package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.hacks.render.FriendTab;
import io.ace.nordclient.managers.FriendManager;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


//Credit 086
@Mixin(GuiPlayerTabOverlay.class)
public abstract class MixinGuiPlayerTabOverlay {

    @Inject(method = "getPlayerName", at = @At("HEAD"), cancellable = true)
    public void getPlayerName(NetworkPlayerInfo networkPlayerInfoIn, CallbackInfoReturnable returnable) {
            returnable.cancel();
            returnable.setReturnValue(getPlayerNames(networkPlayerInfoIn));
    }

    public String getPlayerNames(NetworkPlayerInfo networkPlayerInfoIn) {
        String dname = networkPlayerInfoIn.getDisplayName() != null ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName(networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
        if (FriendManager.isFriend(dname)) return FriendTab.colorchoice() + dname;
        else return dname;
    }

}
