package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.HackManager;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.NordTessellator;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class CrystalAura extends Hack {

    int delay = 0;
    double breakX;
    double breakY;
    double breakZ;

    Setting breakDelay;
    Setting breakRange;

    public CrystalAura() {
        super("CrystalAura", Category.COMBAT, 1);
        CousinWare.INSTANCE.settingsManager.rSetting(breakDelay = new Setting("BreakDelay", this, 2, 0, 20, true, "CrystalAuraBreakDelay"));
        CousinWare.INSTANCE.settingsManager.rSetting(breakRange = new Setting("BreakRange", this, 5.5, 0, 8, false, "CrystalAuraBreakRange"));


    }
    @Override
    public void onUpdate() {
        delay++;
        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (entity instanceof EntityEnderCrystal) {
                if (mc.player.getDistance(entity) <= breakRange.getValDouble()) {
                    if (delay >= breakDelay.getValInt()) {
                        breakX = entity.posX;
                        breakY = entity.posY;
                        breakZ = entity.posZ;
                        mc.playerController.attackEntity(mc.player, entity);
                        mc.player.swingArm(HackManager.getHackByName("Swing").isEnabled() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                        delay = 0;

                    }
                }
            }
        }
    }
    @Override
    public void onWorldRender(RenderEvent event) {
        BlockPos breakPos = new BlockPos(breakX, breakY, breakZ);
        NordTessellator.drawBoundingBoxBlockPos(breakPos.down(), 2, 255, 1, 1, 255);
    }
}
