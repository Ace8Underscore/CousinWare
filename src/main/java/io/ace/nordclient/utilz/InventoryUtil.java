package io.ace.nordclient.utilz;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * @author Ace________/Ace_#1233
 */

public class InventoryUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static int findBlockInHotbar(Block blockToFind) {

        // search blocks in hotbar
        int slot = -1;
        for (int i = 0; i < 9; i++) {

            // filter out non-block items
            ItemStack stack = mc.player.inventory.getStackInSlot(i);

            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
                continue;
            }

            Block block = ((ItemBlock) stack.getItem()).getBlock();
            if (block.equals(blockToFind)) {
                slot = i;
                break;
            }

        }

        return slot;

    }

    public static int findItemInHotbar(Item itemToFind) {

        // search blocks in hotbar
        int slot = -1;
        for (int i = 0; i < 9; i++) {

            ItemStack stack = mc.player.inventory.getStackInSlot(i);

            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof Item)) {
                continue;
            }

            Item item = (stack.getItem());
            if (item.equals(itemToFind)) {
                slot = i;
                break;
            }

        }

        return slot;

    }
    public static int getItems(Item i) {
        return mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == i).mapToInt(ItemStack::getCount).sum() + mc.player.inventory.offHandInventory.stream().filter(itemStack -> itemStack.getItem() == i).mapToInt(ItemStack::getCount).sum();
    }

    public static int getBlocks(Block block) {
        return getItems(Item.getItemFromBlock(block));
    }
}


