package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.FriendManager;
import io.ace.nordclient.utilz.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

public class AutoBedBombDumb extends Hack {

    Setting range;
    Setting spoofPlace;
    Setting autoSwitch;
    Setting rotate;
    Setting placeDelay;
    Setting breakDelay;
    Setting breakMode;
    Setting debug;
    Setting r;
    Setting g;
    Setting b;
    Setting a;
    Setting rainbow;

    private float yaw;
    private int delay = 0;
    private int delayBreak = 0;

    public BlockPos placing;

    public boolean east;
    public boolean west;
    public boolean north;
    public boolean south;

    public AutoBedBombDumb() {
        super("AutoBedBomb", Category.COMBAT);
        CousinWare.INSTANCE.settingsManager.rSetting(range = new Setting("Range", this, 1, 0, 7, false, "AutoBedBombRange"));
        CousinWare.INSTANCE.settingsManager.rSetting(spoofPlace = new Setting("SpoofPlace", this, true, "AutoBedBombSpoofPlace"));
        CousinWare.INSTANCE.settingsManager.rSetting(autoSwitch = new Setting("AutoSwitch", this, true, "AutoBedBombAutoSwitch"));
        CousinWare.INSTANCE.settingsManager.rSetting(rotate = new Setting("Rotate", this, false, "AutoBedBombRotate"));
        CousinWare.INSTANCE.settingsManager.rSetting(placeDelay = new Setting("PlaceDelay", this, 1, 0, 20, false, "AutoBedBombPlaceDelay"));
        CousinWare.INSTANCE.settingsManager.rSetting(breakDelay = new Setting("BreakDelay", this, 1, 0, 10, false, "AutoBedBombBreakDelay"));
        ArrayList<String> breaklModes = new ArrayList<>();
        breaklModes.add("Own");
        breaklModes.add("All");
        CousinWare.INSTANCE.settingsManager.rSetting(breakMode = new Setting("Mode", this, "Own", breaklModes, "AutoBedBombBreakModes"));
        CousinWare.INSTANCE.settingsManager.rSetting(debug = new Setting("Debug", this, false, "AutoBedBombDebug"));

        CousinWare.INSTANCE.settingsManager.rSetting(r = new Setting("Red", this, 1, 0, 255, false, "BlockHighlightRed"));
        CousinWare.INSTANCE.settingsManager.rSetting(g = new Setting("Green", this, 1, 0, 255, false, "BlockHighlightGreen"));
        CousinWare.INSTANCE.settingsManager.rSetting(b = new Setting("Blue", this, 1, 0, 255, false, "BlockHighlightBlue"));
        CousinWare.INSTANCE.settingsManager.rSetting(a = new Setting("alpha", this, 1, 0, 1, false, "BlockHighlightAlpha"));
        CousinWare.INSTANCE.settingsManager.rSetting(rainbow = new Setting("Rainbow", this, true, "BlockHighlightRainbow"));

//
    }

    @Override
    public void onUpdate() {
        delay++;
        delayBreak++;
        for (Entity e : mc.world.loadedEntityList) {
            if (e instanceof EntityPlayer) {
                if (!e.getName().equalsIgnoreCase(mc.player.getName()) && !FriendManager.isFriend(e.getName())) {
                    if (e.getDistance(mc.player) <= range.getValDouble()) {
                        if (debug.getValBoolean()) {
                            Command.sendClientSideMessage(e.getName());
                            Command.sendClientSideMessage(String.valueOf(mc.player.getDistance(e)));
                        }
                        BlockPos eLocation = new BlockPos(e.posX, e.posY, e.posZ);
                        if (mc.world.getBlockState(eLocation).getBlock().canPlaceBlockAt(mc.world, eLocation)) {
                            placing = eLocation;
                            if (mc.world.getBlockState(eLocation.south()).getBlock().canPlaceBlockAt(mc.world, eLocation.south()) && mc.world.getBlockState(eLocation.south().down()).getBlock() != Blocks.AIR) {
                                if (delay >= placeDelay.getValInt()) {
                                    if (rotate.getValBoolean()) {
                                        BlockInteractionHelper.placeBlockScaffold(eLocation);
                                        south = true;
                                        north = false;
                                        east = false;
                                        west = false;
                                    } else {
                                        BlockInteractionHelper.placeBlockScaffoldNoRotate(eLocation);
                                        south = true;
                                        north = false;
                                        east = false;
                                        west = false;
                                    }
                                    yaw = 0;
                                    delay = 0;
                                }
                            }

                            if (mc.world.getBlockState(eLocation.north()).getBlock().canPlaceBlockAt(mc.world, eLocation.north()) && mc.world.getBlockState(eLocation.north().down()).getBlock() != Blocks.AIR) {
                                if (delay >= placeDelay.getValInt()) {
                                    if (rotate.getValBoolean()) {
                                        BlockInteractionHelper.placeBlockScaffold(eLocation);
                                        south = false;
                                        north = true;
                                        east = false;
                                        west = false;
                                    } else {
                                        BlockInteractionHelper.placeBlockScaffoldNoRotate(eLocation);
                                        south = false;
                                        north = true;
                                        east = false;
                                        west = false;
                                    }
                                    yaw = 180;
                                    delay = 0;
                                }
                            }

                            if (mc.world.getBlockState(eLocation.east()).getBlock().canPlaceBlockAt(mc.world, eLocation.east()) && mc.world.getBlockState(eLocation.east().down()).getBlock() != Blocks.AIR) {
                                if (delay >= placeDelay.getValInt()) {
                                    if (rotate.getValBoolean()) {
                                        south = false;
                                        north = false;
                                        east = true;
                                        west = false;
                                        BlockInteractionHelper.placeBlockScaffold(eLocation);
                                    } else {
                                        BlockInteractionHelper.placeBlockScaffoldNoRotate(eLocation);
                                        south = false;
                                        north = false;
                                        east = true;
                                        west = false;
                                    }
                                    delay = 0;
                                    yaw = 270;
                                }
                            }

                                if (mc.world.getBlockState(eLocation.west()).getBlock().canPlaceBlockAt(mc.world, eLocation.west()) && mc.world.getBlockState(eLocation.west().down()).getBlock() != Blocks.AIR) {
                                    if (delay >= placeDelay.getValInt()) {

                                        if (rotate.getValBoolean()) {
                                            south = false;
                                            north = false;
                                            east = false;
                                            west = true;
                                            BlockInteractionHelper.placeBlockScaffold(eLocation);
                                        } else {
                                            BlockInteractionHelper.placeBlockScaffoldNoRotate(eLocation);
                                            south = false;
                                            north = false;
                                            east = false;
                                            west = true;
                                        }
                                        delay = 0;
                                        yaw = 90;
                                    }
                                }
                            }
                        if (breakMode.getValString().equalsIgnoreCase("own")) {
                            if (mc.world.getBlockState(eLocation).getBlock().equals(Blocks.BED)) {
                                if (delayBreak >= breakDelay.getValInt()) {
                                    mc.getConnection().sendPacket(new CPacketPlayerTryUseItemOnBlock(eLocation, EnumFacing.SOUTH, EnumHand.MAIN_HAND, 0, 0, 0));
                                    delayBreak = 0;
                                }
                            }
                        } else {
                           for (TileEntity bedloc : mc.world.loadedTileEntityList) {
                               if (bedloc instanceof  TileEntityBed) {
                                   if (delayBreak >= breakDelay.getValInt()) {
                                       mc.getConnection().sendPacket(new CPacketPlayerTryUseItemOnBlock(bedloc.getPos(), EnumFacing.SOUTH, EnumHand.MAIN_HAND, 0, 0, 0));
                                       delayBreak = 0;
                                   }
                               }
                           }
                        }
                        }
                    }
                }
            }
        if (rainbow.getValBoolean()) {
            RainbowUtil.settingRainbow(r, g, b);
        }
        }



    @Listener
    public void onUpdate(PacketEvent.Send event) {
        Packet packet = event.getPacket();
        if (packet instanceof CPacketPlayer) {
            if (spoofPlace.getValBoolean()) {
                ((CPacketPlayer) packet).yaw = (float) yaw;
                //((CPacketPlayer) packet).pitch = (float) pitch;

            }
        }
    }

        @Override
        public void onWorldRender(RenderEvent event) {


            if (west) {
                NordTessellator.drawBoundingBoxBlockPosHalf(placing.west(),1 , r.getValInt(),g.getValInt() ,b.getValInt() ,255);
                NordTessellator.drawBoundingBoxBlockPosHalf(placing,1 , r.getValInt(),g.getValInt() ,b.getValInt() ,255);
            }
            if (east) {
                NordTessellator.drawBoundingBoxBlockPosHalf(placing.east(),1 , r.getValInt(),g.getValInt() ,b.getValInt() ,255);
                NordTessellator.drawBoundingBoxBlockPosHalf(placing,1 , r.getValInt(),g.getValInt() ,b.getValInt() ,255);
            }
            if (north) {
                NordTessellator.drawBoundingBoxBlockPosHalf(placing.north(),1 , r.getValInt(),g.getValInt() ,b.getValInt() ,255);
                NordTessellator.drawBoundingBoxBlockPosHalf(placing,1 , r.getValInt(),g.getValInt() ,b.getValInt() ,255);
            }

            if (south) {
                NordTessellator.drawBoundingBoxBlockPosHalf(placing.east(),1 , r.getValInt(),g.getValInt() ,b.getValInt() ,255);
                NordTessellator.drawBoundingBoxBlockPosHalf(placing,1 , r.getValInt(),g.getValInt() ,b.getValInt() ,255);
            }
        // South = 90
        //North = 180
        //EAST = 0
        // West = 180
    }

    public void onEnable() {
        south = false;
        north = false;
        east = false;
        west = false;
        if (autoSwitch.getValBoolean()) {
            int bedSlot = InventoryUtil.findItemInHotbar(Items.BED);
            if (bedSlot == -1) {
                Command.sendClientSideMessage("No Beds Found Toggling!");
                this.toggle();
            } else {
                mc.player.inventory.currentItem = bedSlot;
            }
        }
    }
}
