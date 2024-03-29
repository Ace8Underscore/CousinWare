package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.HoleUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

/**
 * Created by Ace on 9/12/2020.
 */
public class AutoOffHand extends Hack {

    int totems;
    Item offhandItem;
    boolean moving = false;
    boolean returnI = false;

    private boolean soft = false;
    public Setting mode;
    public Setting switchToTotemCrystal;
    public Setting switchToTotemGap;
    public Setting switchBackFromTotem;
    public Setting holeCheck;
    public Setting onlyInventory;


    public AutoOffHand() {
        super("AutoOffHand", Category.COMBAT, 4074266);
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Crystal");
        modes.add("Gapple");
        modes.add("Crapple");
        modes.add("Shield");
        modes.add("GapRightClick");
        CousinWare.INSTANCE.settingsManager.rSetting(mode = new Setting("Mode", this, "Crystal", modes, "AutoOffHandMode"));
        CousinWare.INSTANCE.settingsManager.rSetting(switchToTotemCrystal = new Setting("CrystalHealth", this, 16, 0, 36, true, "AutoOffHandCrystalHealth"));
        CousinWare.INSTANCE.settingsManager.rSetting(switchToTotemGap = new Setting("GapHealth", this, 6, 0, 36, true, "AutoOffHandGapHealth"));
        CousinWare.INSTANCE.settingsManager.rSetting(switchBackFromTotem = new Setting("ItemRenable", this, 20, 0, 36, true, "AutoOffHandItemRenable"));
        CousinWare.INSTANCE.settingsManager.rSetting(holeCheck = new Setting("HoleCheck", this, true, "AutoOffHandHoleCheck"));
        CousinWare.INSTANCE.settingsManager.rSetting(onlyInventory = new Setting("NoHotBar", this, true, "AutoOffHandInventoryOnly"));
    }//

    @Override
    public void onUpdate() {

        if (mc.player.getHealth() + mc.player.getAbsorptionAmount() <= switchToTotemCrystal.getValDouble() && mode.getValString().equalsIgnoreCase("Crystal")) {
            offhandItem = Items.TOTEM_OF_UNDYING;
        }
        if (mc.player.getHealth() + mc.player.getAbsorptionAmount() <= switchToTotemGap.getValDouble() && mode.getValString().equalsIgnoreCase("Gapple") || mode.getValString().equalsIgnoreCase("Crapple")) {
            offhandItem = Items.TOTEM_OF_UNDYING;
        }
        if (mode.getValString().equalsIgnoreCase("GapRightClick")) {
            if (holeCheck.getValBoolean()) {
                if (!mc.player.onGround || mc.player.getHealth() + mc.player.getAbsorptionAmount() <= switchToTotemGap.getValDouble() || !HoleUtil.isPlayerInHole() || !(mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) || !Mouse.isButtonDown(1)) {
                    offhandItem = Items.TOTEM_OF_UNDYING;
                }
            }
            if (!mc.player.onGround || mc.player.getHealth() + mc.player.getAbsorptionAmount() <= switchToTotemGap.getValDouble() || !(mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) || !Mouse.isButtonDown(1) && !holeCheck.getValBoolean()) {
                offhandItem = Items.TOTEM_OF_UNDYING;
            }
            if (mc.player.onGround && mc.player.getHealth() + mc.player.getAbsorptionAmount() > switchToTotemGap.getValDouble() && holeCheck.getValBoolean() && HoleUtil.isPlayerInHole() && Mouse.isButtonDown(1) && mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) {
                offhandItem = Items.GOLDEN_APPLE;
            }
            if (mc.player.onGround && mc.player.getHealth() + mc.player.getAbsorptionAmount() > switchToTotemGap.getValDouble() && !holeCheck.getValBoolean() && Mouse.isButtonDown(1) && mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) {
                offhandItem = Items.GOLDEN_APPLE;
            }

        }

        if (mc.player.getHealth() + mc.player.getAbsorptionAmount() >= switchBackFromTotem.getValDouble()) {
            if (mode.getValString().equalsIgnoreCase("Crystal")) {
                offhandItem = Items.END_CRYSTAL;
            }
            if (mode.getValString().equalsIgnoreCase("Gapple")) {
                offhandItem = Items.GOLDEN_APPLE;
            }

            if (mode.getValString().equalsIgnoreCase("Crapple")) {
                offhandItem = Item.getItemById(ItemAppleGold.getIdFromItem(Items.GOLDEN_APPLE));
            }
            if (mode.getValString().equalsIgnoreCase("Shield")) {
                offhandItem = Items.SHIELD;
            }


        }

       /* if (gapOnSword.getValBoolean()) {
            if (mc.player.isSwingInProgress && mc.player.inventory.getCurrentItem().getItem() == Items.DIAMOND_SWORD && mc.player.onGround) {
                // if (//
                //!HackManager.getHack("AutoCrystal").isToggled() && !HackManager.getHack("CrystalAura").isToggled()
                //) {
                mode.getMode("Gapple").setToggled(true);
                mode.getMode("Crystal").setToggled(false);
                //}
            }
            if (!mc.player.onGround
                // || HackManager.getHack("AutoCrystal").isToggled() || HackManager.getHack("CrystalAura").isToggled()
            ) {
                mode.getMode("Crystal").setToggled(true);
                mode.getMode("Gapple").setToggled(false);
            }
        } */


        if (Minecraft.getMinecraft().currentScreen instanceof GuiContainer) return;
        if (returnI) {
            int t = -1;
            for (int i = onlyInventory.getValBoolean() ? 9 : 0; onlyInventory.getValBoolean() ? i < 36 : i < 45; i++)
                if (Minecraft.getMinecraft().player.inventory.getStackInSlot(i).isEmpty()) {
                    t = i;
                    break;
                }
            if (t == -1) return;
            Minecraft.getMinecraft().playerController.windowClick(0, onlyInventory.getValBoolean() ? t : t < 9 ? t + 36 : t, 0, ClickType.PICKUP, Minecraft.getMinecraft().player);
            returnI = false;
        }
        totems = Minecraft.getMinecraft().player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == offhandItem).mapToInt(ItemStack::getCount).sum();
        if (Minecraft.getMinecraft().player.getHeldItemOffhand().getItem() == offhandItem) totems++;
        else {
            if (soft && !Minecraft.getMinecraft().player.getHeldItemOffhand().isEmpty()) return;
            if (moving) {
                Minecraft.getMinecraft().playerController.windowClick(0, 45, 0, ClickType.PICKUP, Minecraft.getMinecraft().player);
                moving = false;
                if (!Minecraft.getMinecraft().player.inventory.getItemStack().isEmpty()) returnI = true;
                return;
            }
            if (Minecraft.getMinecraft().player.inventory.getItemStack().isEmpty()) {
                if (totems == 0) return;
                int t = -1;
                for (int i = onlyInventory.getValBoolean() ? 9 : 0;onlyInventory.getValBoolean() ? i < 36 : i < 45; i++)
                    if (Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem() == offhandItem) {
                        t = i;
                        break;
                    }
                if (t == -1) return; // Should never happen!
                Minecraft.getMinecraft().playerController.windowClick(0, onlyInventory.getValBoolean() ? t : t < 9 ? t + 36 : t, 0, ClickType.PICKUP, Minecraft.getMinecraft().player);
                moving = true;
            } else if (!soft) {
                int t = -1;
                for (int i = onlyInventory.getValBoolean() ? 9 : 0;onlyInventory.getValBoolean() ? i < 36 : i < 45; i++)
                    if (Minecraft.getMinecraft().player.inventory.getStackInSlot(i).isEmpty()) {
                        t = i;
                        break;
                    }
                if (t == -1) return;
                Minecraft.getMinecraft().playerController.windowClick(0, onlyInventory.getValBoolean() ? t : t < 9 ? t + 36 : t, 0, ClickType.PICKUP, Minecraft.getMinecraft().player);
            }
        }

    }

    @Override
    public String getHudInfo() {
        return "\u00A77[\u00A7f" + mode.getValString() + "\u00A77]";
    }

}

