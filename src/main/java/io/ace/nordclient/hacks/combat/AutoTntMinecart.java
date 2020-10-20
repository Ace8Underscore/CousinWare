package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.FriendManager;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.InventoryUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class AutoTntMinecart extends Hack {


    private boolean switchedToRail;
    private boolean switchedToMinecart;
    private boolean mineRail;
    private boolean railPlaced;
    private  boolean canMineRail;
    private  boolean fireInDaHole;
    private  int delay = 0;
    private int delayTNT = 0;
    private int mineDelay = 0;

    Setting range;
    Setting placeDelay;
    Setting stopPlaceTicks;



    public AutoTntMinecart() {
        super("AutoTNTMinecart", Category.COMBAT);
        CousinWare.INSTANCE.settingsManager.rSetting(stopPlaceTicks = new Setting("StopPlaceTicks", this, 20, 1, 60,true, "AutoTNTMinecartStopPlacingTicks"));
        CousinWare.INSTANCE.settingsManager.rSetting(placeDelay = new Setting("PlaceDelay", this, 1, 0, 10,true, "AutoTNTMinecartPlaceDelay"));
        CousinWare.INSTANCE.settingsManager.rSetting(range = new Setting("Range", this, 1, 0, 7,false, "AutoTNTMinecartRange"));
    }

    @Override
    public void onUpdate() {
        Item railItem = Item.getItemFromBlock(Blocks.ACTIVATOR_RAIL);
        Block railBlock = Blocks.RAIL;
        Block activatorRailBlock = Blocks.ACTIVATOR_RAIL;
        Block detectorRailBlock = Blocks.DETECTOR_RAIL;
        Block goldenRailBlock = Blocks.GOLDEN_RAIL;
        int railSlot = InventoryUtil.findBlockInHotbar(Blocks.ACTIVATOR_RAIL);
        int tntSlot = InventoryUtil.findItemInHotbar(Items.TNT_MINECART);
        int pickaxeSlot = InventoryUtil.findItemInHotbar(Items.DIAMOND_PICKAXE);
        int flintStealStot = InventoryUtil.findItemInHotbar(Items.FLINT_AND_STEEL);

        for (Entity e : mc.world.loadedEntityList) {
            if (e instanceof EntityPlayer && !e.getName().equalsIgnoreCase(mc.player.getName()) && !FriendManager.isFriend(e.getName())) {
                if (e.getDistance(e) <= range.getValDouble()) {
                    BlockPos placeLocation = new BlockPos(e.posX, e.posY, e.posZ);
                    if (!mc.world.getBlockState(placeLocation).getBlock().equals(railBlock) && !mc.world.getBlockState(placeLocation).getBlock().equals(activatorRailBlock) && !mc.world.getBlockState(placeLocation).getBlock().equals(detectorRailBlock) && !mc.world.getBlockState(placeLocation).getBlock().equals(goldenRailBlock)) {
                        railPlaced = false;
                    }
                    if (!switchedToRail) {
                        if (railSlot == -1) {
                            Command.sendClientSideMessage("Rail not in inventory");
                            this.toggle();
                        } else {
                            mc.player.inventory.currentItem = railSlot;
                            switchedToRail = true;
                        }
                    }

                    if (!switchedToMinecart && switchedToRail && railPlaced) {
                        if (tntSlot == -1) {
                            Command.sendClientSideMessage("TNT Minecart not in inventory");
                            this.toggle();
                        } else {
                            mc.player.inventory.currentItem = tntSlot;
                            switchedToMinecart = true;
                            canMineRail = true;
                        }
                    }

                    if (mc.player.inventory.getCurrentItem().getItem().equals(railItem) && !mc.world.getBlockState(placeLocation).getBlock().equals(activatorRailBlock)) {
                        BlockInteractionHelper.placeBlockScaffold(placeLocation);
                    }
                    if (mc.world.getBlockState(placeLocation).getBlock().equals(railBlock)) {
                        //Command.sendClientSideMessage("Rail in Spot");
                        railPlaced = true;

                    }

                      /*  if (railPlaced = true) {
                            if (mc.player.inventory.getCurrentItem().getItem().equals(Items.TNT_MINECART)) {
                                BlockInteractionHelper.placeBlockScaffold(placeLocation);
                            }
                        }*/

                    if (mc.player.inventory.getCurrentItem().getItem().equals(Items.TNT_MINECART) && railPlaced && !mineRail &&mc.world.getBlockState(placeLocation).getBlock().equals(railBlock) || mc.world.getBlockState(placeLocation).getBlock().equals(activatorRailBlock) || mc.world.getBlockState(placeLocation).getBlock().equals(detectorRailBlock) || mc.world.getBlockState(placeLocation).getBlock().equals(goldenRailBlock)) {
                        delay++;
                        delayTNT++;
                        if (delay >= placeDelay.getValInt()) {
                            if (tntSlot == -1) {
                                Command.sendClientSideMessage("TNT Minecart not in inventory");
                                this.toggle();
                            } else {
                                mc.player.inventory.currentItem = tntSlot;
                                //BlockInteractionHelper.placeBlockScaffold(placeLocation.up());
                                mc.getConnection().sendPacket(new CPacketPlayerTryUseItemOnBlock(placeLocation, EnumFacing.NORTH, EnumHand.MAIN_HAND, 0, 0, 0));

                                delay = 0;
                            }
                        }

                            if (delayTNT >= stopPlaceTicks.getValInt()) {
                                mineRail = true;
                            }
                        }



                    if (mineRail) {
                        mineDelay++;

                            //mining code
                            //shouldPlaceTNT = false;
                        if (pickaxeSlot == -1) {
                            Command.sendClientSideMessage("pickaxeSlot Not In HotBar");
                            this.toggle();
                        } else {
                            mc.player.inventory.currentItem = pickaxeSlot;

                            mc.player.connection.sendPacket(new CPacketPlayerDigging(
                                    CPacketPlayerDigging.Action.START_DESTROY_BLOCK, placeLocation, EnumFacing.NORTH));
                            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK,
                                    placeLocation, EnumFacing.NORTH));
                            //shouldPlaceTNT = false;
                            if (mineDelay >= 15) {
                                fireInDaHole = true;
                                mineDelay = 0;

                            }
                        }

                    }
                    if (mc.world.getBlockState(placeLocation).getBlock().canPlaceBlockAt(mc.world, placeLocation) && fireInDaHole) {
                        if (flintStealStot == -1) {
                            Command.sendClientSideMessage("Flint and Steal Not In HotBar");
                            this.toggle();
                        } else {
                            mc.player.inventory.currentItem = flintStealStot;
                        }

                        BlockInteractionHelper.placeBlockScaffold(placeLocation);
                        this.toggle();
                    }

                    }
                }
            }
        //Command.sendClientSideMessage(String.valueOf(railSlot));
        }

        public void onEnable() {
        delayTNT = 0;
        //shouldPlaceTNT = true;
            canMineRail = false;
        switchedToRail = false;
        switchedToMinecart = false;

        mineRail = false;
        fireInDaHole = false;
        }
    }

