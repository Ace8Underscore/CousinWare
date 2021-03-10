package io.ace.nordclient.hacks.render;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.HoleUtil;
import io.ace.nordclient.utilz.NordTessellator;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class HoleESP extends Hack {

    Setting radius;
    Setting rObi;
    Setting gObi;
    Setting bObi;
    Setting rRock;
    Setting gRock;
    Setting bRock;
    Setting alpha;
    Setting outlineAlpha;
    Setting width;

    public HoleESP() {
        super("HoleESP", Category.RENDER, 3639503);
        CousinWare.INSTANCE.settingsManager.rSetting(radius = new Setting("Radius", this, 5, 1, 20, false, "HoleESPRadius"));
        CousinWare.INSTANCE.settingsManager.rSetting(rObi = new Setting("RedObi", this, 255, 0, 255, true, "HoleESPRedObi"));
        CousinWare.INSTANCE.settingsManager.rSetting(gObi = new Setting("GreenObi", this, 26, 0, 255, true, "HoleESPGreenObi"));
        CousinWare.INSTANCE.settingsManager.rSetting(bObi = new Setting("BlueObi", this, 255, 0, 255, true, "HoleESPBlueObi"));
        CousinWare.INSTANCE.settingsManager.rSetting(rRock = new Setting("RedRock", this, 0, 0, 255, true, "HoleESPRedRock"));
        CousinWare.INSTANCE.settingsManager.rSetting(gRock = new Setting("GreenRock", this, 255, 0, 255, true, "HoleESPGreenRock"));
        CousinWare.INSTANCE.settingsManager.rSetting(bRock = new Setting("BlueRock", this, 0, 0, 255, true, "HoleESPBlueRock"));
        CousinWare.INSTANCE.settingsManager.rSetting(alpha = new Setting("Alpha", this, 60, 0, 255, true, "HoleESPAlpha"));
        CousinWare.INSTANCE.settingsManager.rSetting(outlineAlpha = new Setting("OutlineAlpha", this, 200, 0, 255, true, "HoleESPOutlineAlpha"));
        CousinWare.INSTANCE.settingsManager.rSetting(width = new Setting("Width", this, 2, 0, 10, true, "HoleESPWidth"));
    }



    @Override
    public void onWorldRender(RenderEvent event) {
        double x = mc.player.posX;
        double y = mc.player.posY;
        double z = mc.player.posZ;
        BlockPos playerPos = new BlockPos(x, y, z);
        List<BlockPos> blocks = (BlockInteractionHelper.getSphere(playerPos, (float) radius.getValDouble(), (int) radius.getValDouble(), false, true, 0));
        for (BlockPos block : blocks) {
            if (block == null)
                return;
            if (HoleUtil.isBedrockHole(block)) {
                NordTessellator.prepare(7);
                NordTessellator.drawBoxBottom(block, rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), alpha.getValInt());
                NordTessellator.release();
                NordTessellator.drawBoundingBoxBottomBlockPos(block, width.getValInt(), rRock.getValInt(), gRock.getValInt(), bRock.getValInt(), outlineAlpha.getValInt());
            }
            if (HoleUtil.isObiHole(block)) {
                NordTessellator.prepare(7);
                NordTessellator.drawBoxBottom(block, rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), alpha.getValInt());
                NordTessellator.release();
                NordTessellator.drawBoundingBoxBottomBlockPos(block, width.getValInt(), rObi.getValInt(), gObi.getValInt(), bObi.getValInt(), outlineAlpha.getValInt());
            }
//
        }

    }

}
