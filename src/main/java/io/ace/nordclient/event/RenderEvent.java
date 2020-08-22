package io.ace.nordclient.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.Vec3d;

public class RenderEvent {
        private final Tessellator tessellator;
        private final Vec3d renderPos;
        private final float partialTicks;
    ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        public RenderEvent(Tessellator tessellator, Vec3d renderPos, float ticks) {
            this.tessellator = tessellator;
            this.renderPos = renderPos;
            partialTicks = ticks;
        }

    public double getScreenWidth() {
        return resolution.getScaledWidth_double();
    }

    public double getScreenHeight() {
        return resolution.getScaledHeight_double();
    }

    public BufferBuilder getBuffer() {
            return tessellator.getBuffer();
        }

    public void setTranslation(Vec3d translation) {
            getBuffer().setTranslation(-translation.x, -translation.y, -translation.z);
        }

        public void resetTranslation() {
            setTranslation(renderPos);
        }

        public float getPartialTicks(){
            return partialTicks;
        }
}
