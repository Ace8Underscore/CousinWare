package io.ace.nordclient.managers;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.mixin.accessor.ICPacketPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

/**
 * @author Ace________/Ace_#1233
 */

public class RotationManager {

    private static final Minecraft mc = Minecraft.getMinecraft();

    private static boolean rotate = false;
    private static double yaw = 0;
    private static double pitch = 0;
    private static int buffer = 0;


    public RotationManager() {
        CousinWare.INSTANCE.getEventManager().addEventListener(this);
    }

    public static double[] calculateLookAt(final double px, final double py, final double pz, final EntityPlayer me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;
        final double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        dirx /= len;
        diry /= len;
        dirz /= len;
        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);
        pitch = pitch * 180.0 / 3.141592653589793;
        yaw = yaw * 180.0 / 3.141592653589793;
        yaw += 90.0;
        return new double[] { yaw, pitch };
    }

    private static void packetFlow() {
        mc.player.rotationPitch += 0.0004;
    }

    public static void setRotations(BlockPos pos) {
        yaw = calculateLookAt(pos.getX(), pos.getY(), pos.getZ(), mc.player)[0];
        pitch = calculateLookAt(pos.getX(), pos.getY(), pos.getZ(), mc.player)[1];
    }

    public static void setRotations(float yaw1, float pitch1) {
        yaw = yaw1;
        pitch = pitch1;
    }

    private static void resetRotations() {
        yaw = mc.player.rotationYaw;
        pitch = mc.player.rotationPitch;
    }

    @Listener
    public void onUpdate(PacketEvent.Send event) {
        if (rotate) {
            if (event.getPacket() instanceof CPacketPlayer) {
                ((ICPacketPlayer) event.getPacket()).setPitch((float) pitch);
                ((ICPacketPlayer) event.getPacket()).setYaw((float) yaw);
            }
        }
    }

    public static void startRotate() {
        packetFlow();
        rotate = true;
        packetFlow();

    }

    public static void endRotate() {
            resetRotations();
            packetFlow();
            rotate = false;
    }
}
