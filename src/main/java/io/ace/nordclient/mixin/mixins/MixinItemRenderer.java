package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.hacks.render.NoRender;
import io.ace.nordclient.hacks.render.ViewModelChanger;
import io.ace.nordclient.managers.HackManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemRenderer.class, priority = 5000)
public abstract class MixinItemRenderer {
    private final Minecraft mc = Minecraft.getMinecraft();
    public final RenderManager renderManager;

    protected MixinItemRenderer(RenderManager renderManager) {
        this.renderManager = renderManager;
    }

    /**
     * @author Ace_______
     */
    @Overwrite
    private void renderArm(EnumHandSide p_187455_1_) {
        this.mc.getTextureManager().bindTexture(this.mc.player.getLocationSkin());
        Render<AbstractClientPlayer> render = this.renderManager.getEntityRenderObject(this.mc.player);
        RenderPlayer renderplayer = (RenderPlayer) render;
        GlStateManager.pushMatrix();
        float f = p_187455_1_ == EnumHandSide.RIGHT ? 1.0F : -1.0F;
        GlStateManager.rotate(92.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(f * -41.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(f * 0.3F, -1.1F, 0.45F);
        if (p_187455_1_ == EnumHandSide.RIGHT) {
            renderplayer.renderRightArm(this.mc.player);
        } else {
            renderplayer.renderLeftArm(this.mc.player);
        }

        GlStateManager.popMatrix();
    }

    /**
     * @author
     */
    @Inject(method = "renderArms", at = @At("HEAD"), cancellable = true)
    private void renderArms(CallbackInfo info) {
        if (HackManager.getHackByName("QuickDrop").isEnabled()) {
            if (!this.mc.player.isInvisible()) {
                GlStateManager.disableCull();
                GlStateManager.pushMatrix();
                GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                this.renderArm(EnumHandSide.RIGHT);
                this.renderArm(EnumHandSide.LEFT);
                GlStateManager.popMatrix();
                GlStateManager.enableCull();
            }

        } else {
            info.cancel();
        }

    }
    /**
     * @author
     */
  /*  @Inject(method = "renderItemInFirstPerson(F)V", at = @At("HEAD"), cancellable = true)
    public void renderItemInFirstPerson(float partialTicks, CallbackInfo info) {
        if (HackManager.getHackByName("QuickDrop").isEnabled()) {
            AbstractClientPlayer abstractclientplayer = this.mc.player;
            float f = abstractclientplayer.getSwingProgress(partialTicks);
            EnumHand enumhand = (EnumHand) MoreObjects.firstNonNull(abstractclientplayer.swingingHand, EnumHand.MAIN_HAND);
            float f1 = abstractclientplayer.prevRotationPitch + (abstractclientplayer.rotationPitch - abstractclientplayer.prevRotationPitch) * partialTicks;
            float f2 = abstractclientplayer.prevRotationYaw + (abstractclientplayer.rotationYaw - abstractclientplayer.prevRotationYaw) * partialTicks;
            boolean flag = true;
            boolean flag1 = true;
            if (abstractclientplayer.isHandActive()) {
                ItemStack itemstack = abstractclientplayer.getActiveItemStack();
                if (!itemstack.isEmpty() && itemstack.getItem() == Items.BOW) {
                    EnumHand enumhand1 = abstractclientplayer.getActiveHand();
                    flag = enumhand1 == EnumHand.MAIN_HAND;
                    flag1 = !flag;
                }
            }
        }
    }
    */



  /*  @Inject(method = "renderMapFirstPersonSide", at = @At("HEAD"), cancellable = true)
    public void renderMapFirstPersonSide(float p_187465_1_, EnumHandSide hand, float p_187465_3_, ItemStack stack, CallbackInfo info) {
        float f = hand == EnumHandSide.RIGHT ? 1.0F : -1.0F;
        GlStateManager.translate(f * 0.125F, -0.125F, 0.0F);
        if (!this.mc.player.isInvisible()) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(f * 10.0F, 0.0F, 0.0F, 1.0F);
            //this.renderArmFirstPerson(p_187465_1_, p_187465_3_, hand);
            GlStateManager.popMatrix();
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(f * 0.51F, -0.08F + p_187465_1_ * -1.2F, -0.75F);
        float f1 = MathHelper.sqrt(p_187465_3_);
        float f2 = MathHelper.sin(f1 * 3.1415927F);
        float f3 = -0.5F * f2;
        float f4 = 0.4F * MathHelper.sin(f1 * 6.2831855F);
        float f5 = -0.3F * MathHelper.sin(p_187465_3_ * 3.1415927F);
        GlStateManager.translate(f * f3, f4 - 0.3F * f2, f5);
        GlStateManager.rotate(f2 * -45.0F, 1.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f * f2 * -30.0F, 0.0F, 1.0F, 0.0F);
        //this.renderMapFirstPerson(stack);
        GlStateManager.popMatrix();
    } */
    @Inject(method = "transformEatFirstPerson", at = @At("HEAD"), cancellable = true)
    private void transformEatFirstPerson(float p_187454_1_, EnumHandSide hand, ItemStack stack, CallbackInfo ci) {
        if (HackManager.getHackByName("ViewModelChanger").isEnabled()) {
            float f = (float) this.mc.player.getItemInUseCount() - p_187454_1_ + 1.0F;
            float f1 = f / (float) stack.getMaxItemUseDuration();
            float f3;
            if (f1 < 0.8F) {
                f3 = MathHelper.abs(MathHelper.cos(f / 4.0F * 3.1415927F) * 0.1F);
                GlStateManager.translate(0.0F, f3, 0.0F);
            }

            f3 = 1.0F - (float) Math.pow((double) f1, 27.0D);
            int i = hand == EnumHandSide.RIGHT ? 1 : -1;
            GlStateManager.translate(f3 * (ViewModelChanger.sizeX.getValDouble() * .6F) * (float) i, f3 * (-ViewModelChanger.sizeYMain.getValDouble() *.5), f3 * ViewModelChanger.sizeZMain.getValDouble());
            GlStateManager.rotate((float) i * f3 * 90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(f3 * 10.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate((float) i * f3 * 30.0F, 0.0F, 0.0F, 1.0F);
        }
    }

    @Inject(method = "transformFirstPerson", at = @At("HEAD"), cancellable = true)
    public void transformFirstPerson(EnumHandSide hand, float p_187453_2_, CallbackInfo info) {
        if (HackManager.getHackByName("ViewModelChanger").isEnabled()) {
            int i = hand == EnumHandSide.RIGHT ? 1 : -1;
            float f = MathHelper.sin(p_187453_2_ * p_187453_2_ * 3.1415927F);
            GlStateManager.rotate((float) i * (-45.0f + f * -20.0F), 0.0F, 100.0F, 0.0F);
            float f1 = MathHelper.sin(MathHelper.sqrt(p_187453_2_) * 3.1415927F);
            GlStateManager.rotate((float)i * f1 * -20.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(f1 * -80.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate((float) i * ViewModelChanger.rotateZ.getValInt(), 0.0F, 1.0F, 0.0F);
            // still gotta find what does pitch
            GlStateManager.scale(ViewModelChanger.sizeXMain.getValDouble(), ViewModelChanger.sizeYMain.getValDouble(), ViewModelChanger.sizeZMain.getValDouble());
        }
    }

    @Inject(method = "transformSideFirstPerson", at = @At("HEAD"), cancellable = true)
    public void transformSideFirstPerson(EnumHandSide hand, float p_187459_2_, CallbackInfo info) {
        if (HackManager.getHackByName("ViewModelChanger").isEnabled()) {
            int i = hand == EnumHandSide.RIGHT ? 1 : -1;
            GlStateManager.translate((float) i * ViewModelChanger.sizeX.getValDouble(), -ViewModelChanger.sizeY.getValDouble() + p_187459_2_ * .6, ViewModelChanger.sizeZ.getValDouble() * -1);

        }
    }

    @Inject(method = "renderSuffocationOverlay", at = @At("HEAD"), cancellable = true)
    public void renderSuffocationOverlay(TextureAtlasSprite sprite, CallbackInfo info) {
        if (NoRender.suffocateScreen.getValBoolean() && HackManager.getHackByName("NoRender").isEnabled()) {
            info.cancel();
        }
    }

    @Inject(method = "renderFireInFirstPerson", at = @At("HEAD"), cancellable = true)
    public void renderFireInFirstPerson(CallbackInfo ci) {
        if (NoRender.fire.getValBoolean() && HackManager.getHackByName("NoRender").isEnabled()) {
            ci.cancel();
        }
    }




}


