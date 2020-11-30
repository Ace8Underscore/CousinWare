package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.hacks.render.BlockHighlight;
import io.ace.nordclient.hacks.render.SkyColor;
import io.ace.nordclient.managers.HackManager;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.renderer.RenderGlobal.drawSelectionBoundingBox;
import static org.lwjgl.opengl.GL11.GL_LINE_SMOOTH;
import static org.lwjgl.opengl.GL11.glEnable;

@Mixin(RenderGlobal.class)
public abstract class MixinRenderGlobal {

    @Shadow
    private WorldClient world;

    /**
     * @author Ace________
     *
     *
     */

//
    private static final ResourceLocation SUN_TEXTURES = new ResourceLocation("cousinware:textures/loren.png");
    private TextureManager renderEngine;

/*    @Inject(method = "renderSky(FI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V"), cancellable = true)
    public void renderSky(float partialTicks, int pass, CallbackInfo info) {
        info.cancel();
        this.renderEngine.bindTexture(new ResourceLocation("textures/loren.PNG"));
    }
 */
    @Inject(method = "drawSelectionBox", at = @At("HEAD"), cancellable = true)
    public void drawSelectionBox(EntityPlayer player, RayTraceResult movingObjectPositionIn, int execute, float partialTicks, CallbackInfo info) {
        if (HackManager.getHackByName("BlockHighlight").isEnabled()) {
            if (execute == 0 && movingObjectPositionIn.typeOfHit == RayTraceResult.Type.BLOCK) {
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.glLineWidth(2.0F);
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask(false);
                glEnable(GL_LINE_SMOOTH);
                BlockPos blockpos = movingObjectPositionIn.getBlockPos();
                IBlockState iblockstate = this.world.getBlockState(blockpos);
                if (iblockstate.getMaterial() != Material.AIR && this.world.getWorldBorder().contains(blockpos)) {
                    double d3 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) partialTicks;
                    double d4 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) partialTicks;
                    double d5 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) partialTicks;
                    drawSelectionBoundingBox(iblockstate.getSelectedBoundingBox(this.world, blockpos).grow(0.0020000000949949026D).offset(-d3, -d4, -d5), (float) BlockHighlight.r.getValDouble() / 255, (float) BlockHighlight.g.getValDouble() / 255, (float) BlockHighlight.b.getValDouble() / 255, (float) BlockHighlight.a.getValDouble());
                }
                GlStateManager.depthMask(true);
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
            }

        }
    }

    @Redirect(method = "renderCloudsFancy", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"))
    public void colorClouds(final float red, final float blue, final float green, final float alpha) {
        if (HackManager.getHackByName("SkyColor").isEnabled()) {
            GlStateManager.color((float) SkyColor.r.getValDouble() / 255, (float) SkyColor.g.getValDouble() / 255, (float) SkyColor.b.getValDouble() / 255, 255);
        } else {
            GlStateManager.color(red, blue, green, alpha);
        }
    }

    }

