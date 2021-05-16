package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.FriendManager;
import io.ace.nordclient.managers.RotationManager;
import io.ace.nordclient.mixin.accessor.ICPacketUseEntity;
import io.ace.nordclient.mixin.accessor.IEntity;
import io.ace.nordclient.utilz.InventoryUtil;
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
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
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
    Setting breakRangeWall;
    Setting placeDelay;
    Setting placeRange;
    Setting rotate;
    Setting minDmg;
    Setting maxDmg;
    Setting multiPlace;
    Setting fastBreak;
    Setting autoSwitch;
    Setting antiWeakness;
    Setting enemyRange;
    Setting renderPlace;
    Setting renderBreak;

    public CrystalAura() {
        super("CrystalAura", Category.COMBAT, 2794140);
        CousinWare.INSTANCE.settingsManager.rSetting(place = new Setting("Place", this, true, "CrystalAuraPlace"));
        CousinWare.INSTANCE.settingsManager.rSetting(explode = new Setting("Explode", this, true, "CrystalAuraExplode"));
        CousinWare.INSTANCE.settingsManager.rSetting(breakDelay = new Setting("BreakDelay", this, 0, 0, 20, true, "CrystalAuraBreakDelay"));
        CousinWare.INSTANCE.settingsManager.rSetting(breakRange = new Setting("BreakRange", this, 5.5, 0, 8, false, "CrystalAuraBreakRange"));
        CousinWare.INSTANCE.settingsManager.rSetting(breakRangeWall = new Setting("BreakRangeWall", this, 3.5, 0, 8, false, "CrystalAuraBreakRangeWall"));

        CousinWare.INSTANCE.settingsManager.rSetting(placeDelay = new Setting("PlaceDelay", this, 0, 0, 20, true, "CrystalAuraPlaceDelay"));
        CousinWare.INSTANCE.settingsManager.rSetting(placeRange = new Setting("PlaceRange", this, 5.5, 0, 8, false, "CrystalAuraPlaceRange"));
        CousinWare.INSTANCE.settingsManager.rSetting(rotate = new Setting("Rotate", this, true, "CrystalAuraRotate"));
        CousinWare.INSTANCE.settingsManager.rSetting(minDmg = new Setting("MinDmg", this, 3, 0, 20, false, "CrystalAuraMinDmg"));
        CousinWare.INSTANCE.settingsManager.rSetting(maxDmg = new Setting("MaxSelfDmg", this, 5, 0, 20, false, "CrystalAuraMaxDmg"));
        CousinWare.INSTANCE.settingsManager.rSetting(multiPlace = new Setting("MultiPlace", this, false, "CrystalAuraMultiPlace"));
        CousinWare.INSTANCE.settingsManager.rSetting(fastBreak = new Setting("FastBreak", this, true, "CrystalAuraFastBreak"));
        CousinWare.INSTANCE.settingsManager.rSetting(autoSwitch = new Setting("AutoSwitch", this, true, "CrystalAuraRotate"));
        CousinWare.INSTANCE.settingsManager.rSetting(antiWeakness = new Setting("AntiWeakness", this, true, "CrystalAuraAntiWeakness"));
        CousinWare.INSTANCE.settingsManager.rSetting(enemyRange = new Setting("EnemyRange", this, 5.5, 0, 8, false, "CrystalAuraEnemyRange"));
        CousinWare.INSTANCE.settingsManager.rSetting(renderPlace = new Setting("RenderPlace", this, true, "CrystalAuraRenderPlace"));
        CousinWare.INSTANCE.settingsManager.rSetting(renderBreak = new Setting("RenderBreak", this, true, "CrystalAuraRenderBreak"));
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
    BlockPos placePos;
    boolean offhand = false;
    int delayBreak = 0;
    int delayPlace = 0;


    @Override
    public void onUpdate() {
        delayBreak++;
        delayPlace++;
        if (isSpoofingAngles) {
            mc.player.rotationPitch += 0.0004;
            // rotate
        } else {
            mc.player.rotationPitch += 0.0004;
            resetRotation();
        }

        if (mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) offhand = false;
        else if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) offhand = true;

        if (place.getValBoolean()) {
            if (findGreatestDamage() != null) {
                if (!multiPlace.getValBoolean() && getBestBreak() == null) place();
                else if (multiPlace.getValBoolean()) place();

            } else {
                isSpoofingAngles = false;
            }
        }//


        if (explode.getValBoolean()) {
            if (delayBreak >= breakDelay.getValInt()) {
                if (getBestBreak() != null) {
                    render = new BlockPos(getBestBreak().posX, getBestBreak().posY, getBestBreak().posZ);
                    if (rotate.getValBoolean()) isSpoofingAngles = true;
                    if (rotate.getValBoolean()) lookAtPacket(getBestBreak().posX, getBestBreak().posY, getBestBreak().posZ, mc.player);
                    mc.playerController.attackEntity(mc.player, getBestBreak());
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    delayBreak = 0;
                }
            } else {
                isSpoofingAngles = false;
            }
        }
    }

    public void place() {
        if (delayPlace >= placeDelay.getValInt()) {
            if (mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
                BlockPos callBack = findGreatestDamage();
                if (rotate.getValBoolean()) isSpoofingAngles = true;
                if (rotate.getValBoolean()) lookAtPacket(callBack.getX(), callBack.getY(), callBack.getZ(), mc.player);
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(callBack, EnumFacing.UP, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));

                placePos = callBack;
                delayPlace = 0;
            }
        }
    }//


    @Override
    public void onWorldRender(RenderEvent event) {
        if (renderBreak.getValBoolean()) {
            if (render != null) {

                NordTessellator.drawBoundingBoxBlockPos(render, 3, 255, 255, 255, 255);

                if (renderEnt != null) {
                    //Vec3d p = EntityUti.getInterpolatedRenderPos(renderEnt, mc.getRenderPartialTicks());
                    //Tracers.drawLineFromPosToPos(render.x - mc.getRenderManager().renderPosX + .5d, render.y - mc.getRenderManager().renderPosY + 1, render.z - mc.getRenderManager().renderPosZ + .5d, p.x, p.y, p.z, renderEnt.getEyeHeight(), 1, 1, 1, 1);
                }
            }
        }
        if (renderPlace.getValBoolean()) {
            if (placePos != null) {
                NordTessellator.prepare(7);
                NordTessellator.drawBox(placePos, 0, 255, 0, 100, 63);
                NordTessellator.release();
            }
        }
    }

    private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = RotationManager.calculateLookAt(px, py, pz, me);
        setYawAndPitch((float) v[0], (float) v[1]);
    }

    private Entity getBestBreak() {
        List<Entity> entities = new ArrayList<>();
        entities.addAll(mc.world.playerEntities.stream().filter(entityPlayer -> !FriendManager.isFriend(entityPlayer.getName())).collect(Collectors.toList()));
        for (Entity entity1 : entities) {
            if (!entity1.getName().equalsIgnoreCase(mc.player.getName())) {
                EntityEnderCrystal crystal = mc.world.loadedEntityList.stream()
                        .filter(entity -> entity instanceof EntityEnderCrystal)
                        .filter(entity -> mc.player.getDistance(entity) <= breakRange.getValDouble())
                        .filter(entity -> mc.player.canEntityBeSeen(entity))
                        .map(entity -> (EntityEnderCrystal) entity)
                        .max(Comparator.comparing(c -> calculateDamage(c.posX, c.posY, c.posZ, entity1)))
                        .orElse(null);
                if (crystal != null) {
                    return crystal;
                }
            }


                EntityEnderCrystal crystal1 = mc.world.loadedEntityList.stream()
                        .filter(entity -> entity instanceof EntityEnderCrystal)
                        .filter(entity -> mc.player.getDistance(entity) <= breakRangeWall.getValDouble())
                        .filter(entity -> !mc.player.canEntityBeSeen(entity))
                        .map(entity -> (EntityEnderCrystal) entity)
                        .max(Comparator.comparing(c -> calculateDamage(c.posX, c.posY, c.posZ, entity1)))
                        .orElse(null);
                if (crystal1 != null) {
                    return crystal1;
                }
            }


        return null;

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

    public static float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity) {
        final float doubleExplosionSize = 12.0f;
        final double distancedsize = entity.getDistance(posX, posY, posZ) / doubleExplosionSize;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        if (((IEntity) mc.player).getIsInWeb()) {
            blockDensity = 1;
            Command.sendClientSideMessage("in web");
        }
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * doubleExplosionSize + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion(mc.world, (Entity)null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }

    public BlockPos findGreatestDamage() {
        BlockPos retVal = null;
        float t = 0;
        if (mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity instanceof EntityPlayer) {
                if (!entity.isDead) {
                if (mc.player.getDistance(entity) <= enemyRange.getValDouble()) {
                    if (!(entity.getName() == mc.player.getName())) {
                        for (BlockPos pos : findCrystalBlocks()) {

                            float dmgs = calculateDamage(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, entity);
                            if (dmgs > minDmg.getValDouble() && dmgs > t && calculateDamage(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, mc.player) < maxDmg.getValDouble()) {
                                t = dmgs;
                                retVal = pos;
                            }
                        }


                        }
                    }
                }
            }
            }

        }
        return retVal;

    }

    public static float getBlastReduction(final EntityLivingBase entity, float damage, final Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            final DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float)ep.getTotalArmorValue(), (float)ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            final int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            final float f = MathHelper.clamp((float)k, 0.0f, 20.0f);
            damage *= 1.0f - f / 25.0f;
            if (entity.isPotionActive(Potion.getPotionById(11))) {
                damage -= damage / 4.0f;
            }
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float)entity.getTotalArmorValue(), (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
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

    @Listener
    public void onUpdate(PacketEvent.Receive event) {
        SPacketSpawnObject packet;
        if (fastBreak.getValBoolean() && delayBreak > breakDelay.getValInt()) {
        if (event.getPacket() instanceof SPacketSpawnObject && (packet = (SPacketSpawnObject) event.getPacket()).getType() == 51 && fastBreak.getValBoolean()) {
            try {
                for (EntityPlayer entity : mc.world.playerEntities) {
                    if (mc.player.getDistance(entity) <= enemyRange.getValDouble() && mc.player.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= breakRange.getValDouble())
                    if (!entity.getName().equals(mc.player.getName())) {
                        if (calculateDamage(packet.getX(), packet.getY(), packet.getZ(), entity) >= minDmg.getValDouble() && calculateDamage(packet.getX(), packet.getY(), packet.getZ(), mc.player) <= maxDmg.getValDouble()) {
                            CPacketUseEntity fastPacket = new CPacketUseEntity();
                            if (findGreatestDamage() != null) {
                                if (!multiPlace.getValBoolean() && getBestBreak() == null) place();
                                else if (multiPlace.getValBoolean()) place();

                            }
                            ((ICPacketUseEntity) fastPacket).setEntityId(packet.getEntityID());
                            ((ICPacketUseEntity) fastPacket).setEntityAction(CPacketUseEntity.Action.ATTACK);
                            mc.player.connection.sendPacket(fastPacket);
                            mc.player.swingArm(EnumHand.MAIN_HAND);
                            delayBreak = 0;
                            if (findGreatestDamage() != null) {
                                if (!multiPlace.getValBoolean() && getBestBreak() == null) place();
                                else if (multiPlace.getValBoolean()) place();

                            }
                            //mc.world.removeEntityFromWorld(packet.getEntityID());
                        }

                    }
                }
            } catch (Exception e) {
                e.getCause();
            }
            if (event.getPacket() instanceof SPacketDestroyEntities) {
                SPacketDestroyEntities packet_ = (SPacketDestroyEntities) event.getPacket();

                try {
                    mc.world.removeEntityFromWorld(packet.getEntityID());
                    if (findGreatestDamage() != null) {
                        if (!multiPlace.getValBoolean() && getBestBreak() == null) place();
                        else if (multiPlace.getValBoolean()) place();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            }
        }

    }

    @Override
    public void onDisable() {
        render = null;
        renderEnt = null;
        resetRotation();

    }

    @Override
    public void onEnable() {
        if (autoSwitch.getValBoolean()) {
            int i = InventoryUtil.findItemInHotbar(Items.END_CRYSTAL);
            if (i != -1) {
                mc.player.inventory.currentItem = i;
            }
        }
    }
}

