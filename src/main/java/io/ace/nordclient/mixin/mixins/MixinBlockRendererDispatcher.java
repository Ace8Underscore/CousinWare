package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.hacks.misc.IllegalFinder;
import io.ace.nordclient.managers.HackManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockRendererDispatcher.class)
public abstract class MixinBlockRendererDispatcher {

    @Shadow
    @Final
    private BlockModelRenderer blockModelRenderer;

    @Shadow
    public abstract IBakedModel getModelForState(IBlockState state);

    private static final Minecraft mc = Minecraft.getMinecraft();

    @Inject(method = "renderBlock", at = @At("HEAD"), cancellable = true)
    public void renderBlock(IBlockState state, BlockPos pos, IBlockAccess blockAccess, BufferBuilder bufferBuilderIn, CallbackInfoReturnable<Boolean> info) {
        if (HackManager.getHackByName("Xray").isEnabled()) {
            if (mc.world.getBlockState(pos).getBlock().equals(Blocks.PORTAL)) {
                IBakedModel model = this.getModelForState(state);
                info.setReturnValue(this.blockModelRenderer.renderModel(blockAccess, model, state, pos, bufferBuilderIn, false));
            } else {
                info.setReturnValue(false);
            }

            // if (mc.world.getBlockState(pos).getBlock().equals(Block.getBlockFromName(Xray.xrayBlocks.get(i)))) {
            //     info.setReturnValue(false);

            //}
        }
        if (HackManager.getHackByName("IllegalFinder").isEnabled()) {
            if (mc.player.dimension == 0) {
                if (pos.getY() > 5) {
                    if (mc.world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK)) {
                        IllegalFinder.illegalPos = pos;
                        IllegalFinder.illegal = true;

                    }
                } else {
                    IllegalFinder.illegal = false;
                }
            }
            if (mc.player.dimension == 1) {
                if (mc.world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK) && pos.getY() > 5 && pos.getY() != 124 && pos.getY() != 125 && pos.getY() != 126 && pos.getY() != 127 && pos.getY() != 128) {
                    IllegalFinder.illegalPos = pos;
                    IllegalFinder.illegal = true;
                } else {
                    IllegalFinder.illegal = false;
                }


            }
            if (mc.player.dimension == 2) {
                if (mc.world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK)) {
                    IllegalFinder.illegalPos = pos;
                    IllegalFinder.illegal = true;
                } else {
                    IllegalFinder.illegal = false;
                }
            }

        }

    }
}
