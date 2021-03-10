package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.FriendManager;
import io.ace.nordclient.managers.RotationManager;
import io.ace.nordclient.utilz.NordTessellator;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.Explosion;
import org.lwjgl.opengl.GL11;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CrystalAura extends Hack {
    Setting place;
    Setting explode;
    Setting breakDelay;
    Setting breakRange;
    Setting autoSwitch;
    Setting antiWeakness;

    public CrystalAura() {
        super("CrystalAura", Category.COMBAT, 2794140);
        CousinWare.INSTANCE.settingsManager.rSetting(place = new Setting("Place", this, true, "CrystalAuraPlace"));
        CousinWare.INSTANCE.settingsManager.rSetting(explode = new Setting("Explode", this, true, "CrystalAuraExplode"));
        CousinWare.INSTANCE.settingsManager.rSetting(breakDelay = new Setting("Delay", this, 250, 0, 1000, true, "CrystalAuraBreakDelay"));
        CousinWare.INSTANCE.settingsManager.rSetting(breakRange = new Setting("Range", this, 5.5, 0, 8, false, "CrystalAuraBreakRange"));
        CousinWare.INSTANCE.settingsManager.rSetting(autoSwitch = new Setting("AutoSwitch", this, true, "CrystalAuraRotate"));
        CousinWare.INSTANCE.settingsManager.rSetting(antiWeakness = new Setting("AntiWeakness", this, true, "CrystalAuraAntiWeakness"));
//
    }

    private BlockPos render;
    private Entity renderEnt;
    private long systemTime = -1;
    private static boolean togglePitch = false;
    // we need this cooldown to not place from old hotbar slot, before we have switched to crystals
    private boolean switchCooldown = false;
    private boolean isAttacking = false;
    private int oldSlot = -1;
    private int newSlot;

    @Override
    public void onUpdate() {
        EntityEnderCrystal crystal = mc.world.loadedEntityList.stream()
                .filter(entity -> entity instanceof EntityEnderCrystal)
                .map(entity -> (EntityEnderCrystal) entity)
                .min(Comparator.comparing(c -> mc.player.getDistance(c)))
                .orElse(null);
        if (explode.getValBoolean() && crystal != null && mc.player.getDistance(crystal) <= breakRange.getValDouble()) {
            //Added delay to stop ncp from flagging "hitting too fast"
            if (((System.nanoTime() / 30000) - systemTime) >= breakDelay.getValInt()) {
                if (antiWeakness.getValBoolean() && mc.player.isPotionActive(MobEffects.WEAKNESS)) {
                    if (!isAttacking) {
                        // save initial player hand
                        oldSlot = mc.player.inventory.currentItem;
                        isAttacking = true;
                    }
                    // search for sword and tools in hotbar
                    newSlot = -1;
                    for (int i = 0; i < 9; i++) {
                        ItemStack stack = mc.player.inventory.getStackInSlot(i);
                        if (stack == ItemStack.EMPTY) {
                            continue;
                        }
                        if ((stack.getItem() instanceof ItemSword)) {
                            newSlot = i;
                            break;
                        }
                        if ((stack.getItem() instanceof ItemTool)) {
                            newSlot = i;
                            break;
                        }
                    }
                    // check if any swords or tools were found
                    if (newSlot != -1) {
                        mc.player.inventory.currentItem = newSlot;
                        switchCooldown = true;
                    }
                }
                lookAtPacket(crystal.posX, crystal.posY, crystal.posZ, mc.player);
                mc.playerController.attackEntity(mc.player, crystal);
                mc.player.swingArm(EnumHand.MAIN_HAND);
                systemTime = System.nanoTime() /30000;
            }

        } else {
            resetRotation();
            if (oldSlot != -1) {
                mc.player.inventory.currentItem = oldSlot;
                oldSlot = -1;
            }
            isAttacking = false;
        }

        int crystalSlot = mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL ? mc.player.inventory.currentItem : -1;
        if (crystalSlot == -1) {
            for (int l = 0; l < 9; ++l) {
                if (mc.player.inventory.getStackInSlot(l).getItem() == Items.END_CRYSTAL) {
                    crystalSlot = l;
                    break;
                }
            }
        }

        boolean offhand = false;
        if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            offhand = true;
        } else if (crystalSlot == -1) {

        }

        List<BlockPos> blocks = findCrystalBlocks();
        List<Entity> entities = new ArrayList<>();
            entities.addAll(mc.world.playerEntities.stream().filter(entityPlayer -> !FriendManager.isFriend(entityPlayer.getName())).collect(Collectors.toList()));


        BlockPos q = null;
        double damage = .25;
        for (Entity entity : entities) {
            if (entity == mc.player || ((EntityLivingBase) entity).getHealth() <= 0) {
                continue;
            }
            for (BlockPos blockPos : blocks) {
                double b = entity.getDistanceSq(blockPos);
                if (b >= 169) {
                    continue; // If this block if further than 13 (3.6^2, less calc) blocks, ignore it. It'll take no or very little damage
                }
                double d = calculateDamage(blockPos.x + .5, blockPos.y + 1, blockPos.z + .5, entity);
                if (d > damage) {
                    double self = calculateDamage(blockPos.x + .5, blockPos.y + 1, blockPos.z + .5, mc.player);
                    // If this deals more damage to ourselves than it does to our target, continue. This is only ignored if the crystal is sure to kill our target but not us.
                    // Also continue if our crystal is going to hurt us.. alot
                    if ((self > d && !(d < ((EntityLivingBase) entity).getHealth())) || self - .5 > mc.player.getHealth()) {
                        continue;
                    }
                    damage = d;
                    q = blockPos;
                    renderEnt = entity;
                }
            }
        }
        if (damage == .25) {
            render = null;
            renderEnt = null;
            resetRotation();

        }
        render = q;

        if (place.getValBoolean()) {
            if (!offhand && mc.player.inventory.currentItem != crystalSlot) {
                if (autoSwitch.getValBoolean()) {
                    mc.player.inventory.currentItem = crystalSlot;
                    resetRotation();
                    switchCooldown = true;
                }

            }
            lookAtPacket(q.x + .5, q.y - .5, q.z + .5, mc.player);
            RayTraceResult result = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(q.x + .5, q.y - .5d, q.z + .5));
            EnumFacing f;
            if (result == null || result.sideHit == null) {
                f = EnumFacing.UP;
            } else {
                f = result.sideHit;
            }
            // return after we did an autoswitch
            if (switchCooldown) {
                switchCooldown = false;

            }
            //mc.playerController.processRightClickBlock(mc.player, mc.world, q, f, new Vec3d(0, 0, 0), EnumHand.MAIN_HAND);
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(q, f, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0, 0, 0));
        }
        //this sends a constant packet flow for default packets
        if (isSpoofingAngles) {
            if (togglePitch) {
                mc.player.rotationPitch += 0.0004;
                togglePitch = false;
            } else {
                mc.player.rotationPitch -= 0.0004;
                togglePitch = true;
            }
        }


    }

    @Override
    public void onWorldRender(RenderEvent event) {
        if (render != null) {
            NordTessellator.prepare(GL11.GL_QUADS);
            NordTessellator.drawBox(render, 255, 255, 255, 255, 7);
            NordTessellator.release();
            if (renderEnt != null) {
                //Vec3d p = EntityUti.getInterpolatedRenderPos(renderEnt, mc.getRenderPartialTicks());
                //Tracers.drawLineFromPosToPos(render.x - mc.getRenderManager().renderPosX + .5d, render.y - mc.getRenderManager().renderPosY + 1, render.z - mc.getRenderManager().renderPosZ + .5d, p.x, p.y, p.z, renderEnt.getEyeHeight(), 1, 1, 1, 1);
            }
        }
    }

    private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = RotationManager.calculateLookAt(px, py, pz, me);
        setYawAndPitch((float) v[0], (float) v[1]);
    }

    private boolean canPlaceCrystal(BlockPos blockPos) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 2, 0);
        if ((mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK
                && mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN)
                || mc.world.getBlockState(boost).getBlock() != Blocks.AIR
                || mc.world.getBlockState(boost2).getBlock() != Blocks.AIR
                || !mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost)).isEmpty()) {
            return false;
        }
        return true;
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    private List<BlockPos> findCrystalBlocks() {
        NonNullList<BlockPos> positions = NonNullList.create();
        positions.addAll(getSphere(getPlayerPos(), (float) breakRange.getValDouble(), breakRange.getValInt(), false, true, 0).stream().filter(this::canPlaceCrystal).collect(Collectors.toList()));
        return positions;
    }

    public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        List<BlockPos> circleblocks = new ArrayList<>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        for (int x = cx - (int) r; x <= cx + r; x++) {
            for (int z = cz - (int) r; z <= cz + r; z++) {
                for (int y = (sphere ? cy - (int) r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }

    public static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
        float doubleExplosionSize = 6.0F * 2.0F;
        double distancedsize = entity.getDistance(posX, posY, posZ) / (double) doubleExplosionSize;
        Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = (double) entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        double v = (1.0D - distancedsize) * blockDensity;
        float damage = (float) ((int) ((v * v + v) / 2.0D * 7.0D * (double) doubleExplosionSize + 1.0D));
        double finald = 1;
        /*if (entity instanceof EntityLivingBase)
            finald = getBlastReduction((EntityLivingBase) entity,getDamageMultiplied(damage));*/
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase) entity, getDamageMultiplied(damage), new Explosion(mc.world, null, posX, posY, posZ, 6F, false, true));
        }
        return (float) finald;
    }

    public static float getBlastReduction(EntityLivingBase entity, float damage, Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer ep = (EntityPlayer) entity;
            DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float) ep.getTotalArmorValue(), (float) ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());

            int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            float f = MathHelper.clamp(k, 0.0F, 20.0F);
            damage = damage * (1.0F - f / 25.0F);

            if (entity.isPotionActive(Potion.getPotionById(11))) {
                damage = damage - (damage / 4);
            }

            damage = Math.max(damage - ep.getAbsorptionAmount(), 0.0F);
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float) entity.getTotalArmorValue(), (float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }

    private static float getDamageMultiplied(float damage) {
        int diff = mc.world.getDifficulty().getId();
        return damage * (diff == 0 ? 0 : (diff == 2 ? 1 : (diff == 1 ? 0.5f : 1.5f)));
    }

    public static float calculateDamage(EntityEnderCrystal crystal, Entity entity) {
        return calculateDamage(crystal.posX, crystal.posY, crystal.posZ, entity);
    }

    //Better Rotation Spoofing System:

    private static boolean isSpoofingAngles;
    private static double yaw;
    private static double pitch;

    //this modifies packets being sent so no extra ones are made. NCP used to flag with "too many packets"
    private static void setYawAndPitch(float yaw1, float pitch1) {
        yaw = yaw1;
        pitch = pitch1;
        isSpoofingAngles = true;
    }

    private static void resetRotation() {
        if (isSpoofingAngles) {
            yaw = mc.player.rotationYaw;
            pitch = mc.player.rotationPitch;
            isSpoofingAngles = false;
        }
    }


    @Listener
    public void onUpdate(PacketEvent.Send event) {
        Packet packet = event.getPacket();
        if (packet instanceof CPacketPlayer) {
            if (isSpoofingAngles) {
                ((CPacketPlayer) packet).yaw = (float) yaw;
                ((CPacketPlayer) packet).pitch = (float) pitch;
            }
        }
    }

    @Override
    public void onDisable() {
        render = null;
        renderEnt = null;
        resetRotation();

    }
}

