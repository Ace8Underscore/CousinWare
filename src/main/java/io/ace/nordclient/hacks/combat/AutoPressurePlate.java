package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.InventoryUtil;
import io.ace.nordclient.utilz.PlayerUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.init.Blocks;

public class AutoPressurePlate extends Hack {

    Setting rotate;

    int startingHand;

    public AutoPressurePlate() {
        super("AutoPressurePlate", Category.COMBAT, 11827056);
        CousinWare.INSTANCE.settingsManager.rSetting(rotate = new Setting("Rotate", this, false, "AutoPressurePlateRotate"));

    }

    @Override
    public void onUpdate() {
        if (!mc.world.getBlockState(PlayerUtil.getPlayerPos().up()).equals(Blocks.WOODEN_PRESSURE_PLATE) && !mc.world.getBlockState(PlayerUtil.getPlayerPos().up()).equals(Blocks.STONE_PRESSURE_PLATE)) {
            if (InventoryUtil.findBlockInHotbar(Blocks.WOODEN_PRESSURE_PLATE) != -1) {
                mc.player.inventory.currentItem = InventoryUtil.findBlockInHotbar(Blocks.WOODEN_PRESSURE_PLATE);
                if (rotate.getValBoolean()) BlockInteractionHelper.placeBlockScaffold(PlayerUtil.getPlayerPos());
                else BlockInteractionHelper.placeBlockScaffoldNoRotate(PlayerUtil.getPlayerPos());
                mc.player.inventory.currentItem = startingHand;
            }
            if (InventoryUtil.findBlockInHotbar(Blocks.STONE_PRESSURE_PLATE) != -1) {
                mc.player.inventory.currentItem = InventoryUtil.findBlockInHotbar(Blocks.STONE_PRESSURE_PLATE);
                if (rotate.getValBoolean()) BlockInteractionHelper.placeBlockScaffold(PlayerUtil.getPlayerPos());
                else BlockInteractionHelper.placeBlockScaffoldNoRotate(PlayerUtil.getPlayerPos());
                mc.player.inventory.currentItem = startingHand;
            }
        }
    }

    @Override
    public void onEnable() {
        startingHand = mc.player.inventory.currentItem;
        //
    }
}
