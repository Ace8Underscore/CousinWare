package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.FriendManager;
import io.ace.nordclient.managers.HackManager;
import io.ace.nordclient.managers.RotationManager;
import io.ace.nordclient.mixin.accessor.ICPacketPlayer;
import io.ace.nordclient.utilz.BlockInteractionHelper;
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
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.List;

public class CrystalAura extends Hack {

    int delay = 0;

    Entity closestTarget;

    Setting breakDelay;
    Setting breakRange;
    Setting rotate;
    Setting minDmg;

    public CrystalAura() {
        super("CrystalAura", Category.COMBAT, 2794140);
        CousinWare.INSTANCE.settingsManager.rSetting(breakDelay = new Setting("Delay", this, 2, 0, 20, true, "CrystalAuraBreakDelay"));
        CousinWare.INSTANCE.settingsManager.rSetting(breakRange = new Setting("BreakRange", this, 5.5, 0, 8, false, "CrystalAuraBreakRange"));
        CousinWare.INSTANCE.settingsManager.rSetting(rotate = new Setting("Rotate", this, true, "CrystalAuraRotate"));
        CousinWare.INSTANCE.settingsManager.rSetting(minDmg = new Setting("MinDmg", this, 5.5, 0, 36, false, "CrystalAuraMinDmg"));
//
    }

    public void onUpdate() {
        delay++;
        findClosestTarget();
        double x = mc.player.posX;
        double y = mc.player.posY;
        double z = mc.player.posZ;
        BlockPos playerPos = new BlockPos(x, y, z);
        List<BlockPos> blocks = (BlockInteractionHelper.getSphere(playerPos, (float) breakRange.getValDouble(), (int) breakRange.getValDouble(), false, true, 0));
        for (BlockPos block : blocks) {
            if (block == null)
                return;
            if (crystalPlacePos(block)) {
                if (mc.player.getHeldItemMainhand().getItem().equals(Items.END_CRYSTAL)) {
                    if (calculateDamage(block.x + .5, block.y + 1, block.z + .5, closestTarget) > minDmg.getValDouble()) {
                        for (Entity entity : mc.world.loadedEntityList) {
                            if (entity instanceof EntityEnderCrystal) ;
                            //Command.sendClientSideMessage(String.valueOf(calculateDamage((EntityEnderCrystal) entity, mc.player)));
                            //mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(block, EnumFacing.UP, EnumHand.MAIN_HAND, 1, 1, 1));
                            //calculateDamage((EntityEnderCrystal) entity, mc.player);
                        }
                    }
                }
                    //}
                    if (mc.player.getHeldItemOffhand().getItem().equals(Items.END_CRYSTAL)) {
                        if (delay > breakDelay.getValInt()) {
                            mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(block, EnumFacing.UP, EnumHand.OFF_HAND, 1, 1, 1));
                            //BlockInteractionHelper.placeBlockScaffold(block);
                            delay = 0;
                        }
                    }
                }
            }

        }


    private boolean crystalPlacePos(BlockPos pos) {
        boolean retVal = false;
        if (mc.world.getBlockState(pos).getBlock().equals(Blocks.OBSIDIAN) || mc.world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK))
        if (mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR))
        if (mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR))
        retVal = true;

        return retVal;
    }
    public static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
        float doubleExplosionSize = 12.0F;
        double distancedsize = entity.getDistance(posX, posY, posZ) / (double) doubleExplosionSize;
        Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = (double) entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        double v = (1.0D - distancedsize) * blockDensity;
        float damage = (float) ((int) ((v * v + v) / 2.0D * 7.0D * (double) doubleExplosionSize + 1.0D));
        double finald = 1.0D;
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
            damage *= 1.0F - f / 25.0F;

            if (entity.isPotionActive(Potion.getPotionById(11))) {
                damage = damage - (damage / 4);
            }
            //   damage = Math.max(damage - ep.getAbsorptionAmount(), 0.0F);
            return damage;
        } else {
            damage = CombatRules.getDamageAfterAbsorb(damage, (float) entity.getTotalArmorValue(), (float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            return damage;
        }
    }

    private static float getDamageMultiplied(float damage) {
        int diff = mc.world.getDifficulty().getId();
        return damage * (diff == 0 ? 0 : (diff == 2 ? 1 : (diff == 1 ? 0.5f : 1.5f)));
    }

    public static float calculateDamage(EntityEnderCrystal crystal, Entity entity) {
        return calculateDamage(crystal.posX, crystal.posY, crystal.posZ, entity);
    }

    private void findClosestTarget() {
        final List<EntityPlayer> playerList = (List<EntityPlayer>)mc.world.playerEntities;
        this.closestTarget = null;
        for (final EntityPlayer target : playerList) {
            if (target == mc.player) {
                continue;
            }
            if (FriendManager.isFriend(target.getName())) {
                continue;
            }
            if (target.getHealth() <= 0.0f) {
                continue;
            }
            if (this.closestTarget == null) {
                this.closestTarget = target;
            }
            else {
                if (mc.player.getDistance((Entity)target) >= mc.player.getDistance((Entity)this.closestTarget)) {
                    continue;
                }
                this.closestTarget = target;
            }
        }
    }


}
