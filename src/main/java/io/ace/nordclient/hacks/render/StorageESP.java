package io.ace.nordclient.hacks.render;

import io.ace.nordclient.NordClient;
import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.NordTessellator;
import io.ace.nordclient.utilz.clientutil.Setting;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import static net.minecraft.client.renderer.RenderGlobal.drawSelectionBoundingBox;
import static org.lwjgl.opengl.GL11.GL_LINE_SMOOTH;
import static org.lwjgl.opengl.GL11.glEnable;

public class StorageESP extends Hack {

    Setting eChest;
    Setting chest;
    Setting shulker;

    public StorageESP() {
        super("StorageESP", Category.RENDER);
        NordClient.INSTANCE.settingsManager.rSetting(eChest = new Setting("EChest", this, true, "StorageESPEChest"));
        NordClient.INSTANCE.settingsManager.rSetting(chest = new Setting("Chest", this, true, "StorageESPChest"));
        NordClient.INSTANCE.settingsManager.rSetting(shulker = new Setting("Shulker", this, true, "StorageESPShulker"));
    }

    @Listener
    public void onWorldRender(RenderEvent event) {
        for (TileEntity e : mc.world.loadedTileEntityList) {
            if (e instanceof TileEntityEnderChest && eChest.getValBoolean()) {
                BlockPos eChestPos = e.getPos();
                NordTessellator.drawBoundingBoxChestBlockPos(eChestPos, 1, 145, 43, 173, 255);
            }
            if (e instanceof TileEntityShulkerBox && shulker.getValBoolean()) {
                BlockPos shulkerPos = e.getPos();
                NordTessellator.drawBoundingBoxBlockPos(shulkerPos, 1, 243, 0, 127, 255);
            }
            if (e instanceof TileEntityChest && chest.getValBoolean()) {
                    BlockPos chestPos = e.getPos();
                    NordTessellator.drawBoundingBoxChestBlockPos(chestPos, 1, 255, 150, 60, 255);

                }
            }
        }
    }

