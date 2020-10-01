package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class AutoTntMinecart extends Hack {

    Setting range;

    public AutoTntMinecart() {
        super("AutoTNTMinecart", Category.COMBAT);

        CousinWare.INSTANCE.settingsManager.rSetting(range = new Setting("Range", this, 1, 0, 7,false, "AutoTNTMinecartRange"));
    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        for (Entity e : mc.world.loadedEntityList) {
            if (e instanceof EntityPlayer && !e.getName().equalsIgnoreCase(mc.player.getName())) {
                if (e.getDistance(e) <= range.getValDouble()) {
                    BlockPos placeLocation = e.getPosition();
                    BlockInteractionHelper.placeBlockScaffold(placeLocation);
                }
            }
        }
    }
}
