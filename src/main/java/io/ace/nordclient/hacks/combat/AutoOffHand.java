package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
    public Setting gapOnSword;


    public AutoOffHand() {
        super("AutoOffHand", Category.COMBAT, 4);
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Crystal");
        modes.add("Gapple");
        modes.add("Shield");
        CousinWare.INSTANCE.settingsManager.rSetting(mode = new Setting("Mode", this, "Crystal", modes, "AutoOffHandMode"));
        CousinWare.INSTANCE.settingsManager.rSetting(switchToTotemCrystal = new Setting("CrystalHealth", this, 16, 0, 36, false, "AutoOffHandCrystalHealth"));
        CousinWare.INSTANCE.settingsManager.rSetting(switchToTotemGap = new Setting("GapHealth", this, 6, 0, 36, false, "AutoOffHandGapHealth"));
        CousinWare.INSTANCE.settingsManager.rSetting(switchBackFromTotem = new Setting("ItemRenable", this, 20, 0, 36, false, "AutoOffHandItemRenable"));
        //NordClient.INSTANCE.settingsManager.rSetting(gapOnSword = new Setting("GapOnSword", this, false, "AutoOffHandGapOnSword"));

    }

    @Override
    public void onUpdate() {

        if (mc.player.getHealth() + mc.player.getAbsorptionAmount() <= switchToTotemCrystal.getValDouble() && mode.getValString().equalsIgnoreCase("Crystal")) {
            offhandItem = Items.TOTEM_OF_UNDYING;
        }
        if (mc.player.getHealth() + mc.player.getAbsorptionAmount() <= switchToTotemGap.getValDouble() && mode.getValString().equalsIgnoreCase("Gapple")) {
            offhandItem = Items.TOTEM_OF_UNDYING;
        }

        if (mc.player.getHealth() + mc.player.getAbsorptionAmount() >= switchBackFromTotem.getValDouble()) {
            if (mode.getValString().equalsIgnoreCase("Crystal")) {
                offhandItem = Items.END_CRYSTAL;
            }
            if (mode.getValString().equalsIgnoreCase("Gapple")) {
                offhandItem = Items.GOLDEN_APPLE;
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
            for (int i = 0; i < 45; i++)
                if (Minecraft.getMinecraft().player.inventory.getStackInSlot(i).isEmpty()) {
                    t = i;
                    break;
                }
            if (t == -1) return;
            Minecraft.getMinecraft().playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, Minecraft.getMinecraft().player);
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
                for (int i = 0; i < 45; i++)
                    if (Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem() == offhandItem) {
                        t = i;
                        break;
                    }
                if (t == -1) return; // Should never happen!
                Minecraft.getMinecraft().playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, Minecraft.getMinecraft().player);
                moving = true;
            } else if (!soft) {
                int t = -1;
                for (int i = 0; i < 45; i++)
                    if (Minecraft.getMinecraft().player.inventory.getStackInSlot(i).isEmpty()) {
                        t = i;
                        break;
                    }
                if (t == -1) return;
                Minecraft.getMinecraft().playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, Minecraft.getMinecraft().player);
            }
        }

    }



}

