package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.PlayerJoinEvent;
import io.ace.nordclient.event.PlayerLeaveEvent;
import io.ace.nordclient.event.PlayerMoveEvent;
import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.FriendManager;
import io.ace.nordclient.utilz.Setting;
import io.ace.nordclient.utilz.TpsUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.concurrent.ThreadLocalRandom;

public class Spammer extends Hack {

    Setting movement;
    Setting delay;

    double updatedX = 0;
    double updatedZ = 0;
    double finalMovement;
    int msgDelay = 0;
    int placeDelay = 0;
    String statementBlock;
    boolean firstPlace = true;
    int timesPlaced = 0;
    String placedMessage;
    int joinLeaveMsg = 0;
    int eaten = 0;
    int eattingDelay;

    Item checkBlock;
    ItemStack checkBlock2;


    public Spammer() {
        super("Spammer", Category.MISC);
        CousinWare.INSTANCE.settingsManager.rSetting(movement = new Setting("Movement", this, true, "SpammerMovement"));
        CousinWare.INSTANCE.settingsManager.rSetting(delay = new Setting("Delay", this, 100, 1, 500, true, "SpammerDelay"));

    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        joinLeaveMsg++;
        msgDelay++;
        placeDelay++;
        eattingDelay++;
        int randomNum = ThreadLocalRandom.current().nextInt(1, 10 + 1);
        if (msgDelay >= delay.getValInt()) {
            if (finalMovement >= randomNum) {
                mc.player.sendChatMessage("> I Just Hopped " + Math.round(finalMovement * 10) / 10.00 + " Meters Like A Kangaroo Thanks To CousinWare!");
            }
                if (timesPlaced >= randomNum) {
                    mc.player.sendChatMessage(placedMessage);
                    firstPlace = true;
                    timesPlaced = 0;
                }
//
            firstPlace = true;
            timesPlaced = 0;
            finalMovement = 0;
            msgDelay = 0;
        }
        if (Mouse.isButtonDown(1)) {
            Item usedItem = mc.player.getHeldItemMainhand().getItem();
            ItemStack heldBlock = new ItemStack(mc.player.getHeldItemMainhand().getItem());
            if (usedItem instanceof ItemBlock) {
                if (mc.player.isSwingInProgress) {
                    if (placeDelay > 5) {
                        if (firstPlace) {
                            statementBlock = usedItem.getItemStackDisplayName(heldBlock);
                            checkBlock = usedItem;
                            checkBlock2 = heldBlock;
                            firstPlace = false;
                        } else {
                            if (usedItem.getItemStackDisplayName(heldBlock).equals(checkBlock.getItemStackDisplayName(checkBlock2))) {
                                timesPlaced += 1;
                                //mc.player.sendChatMessage(usedItem.getItemStackDisplayName(heldBlock) + "Placed " + timesPlaced);
                                //placedMessage = usedItem.getItemStackDisplayName(heldBlock) + "Placed " + timesPlaced;
                                placedMessage = "> I Just Placed " + timesPlaced + " " + usedItem.getItemStackDisplayName(heldBlock) +" Thanks To Cousin Ware!";
                                //}
                            }
                            placeDelay = 0;

                        }
                    }
                }
            }

        }
    }

        @Listener
        public void onPlayerMovement(PlayerMoveEvent event) {
            getMovement();

        }

        @Listener
        public void playerJoinEvent(PlayerJoinEvent event) {
            if (joinLeaveMsg > 30) {
                if (!FriendManager.isFriend(event.getName())) {
                    mc.player.sendChatMessage("> Welcome " + event.getName() + " To " + mc.getCurrentServerData().serverIP);
                } else {
                    mc.player.sendChatMessage("> My Friend " + event.getName() + " Has Just Joined!");
                }
                joinLeaveMsg = 0;
            }
        }

    @Listener
    public void playerLeaveEvent(PlayerLeaveEvent event) {
        if (joinLeaveMsg > 30) {
            if (!FriendManager.isFriend(event.getName())) {
                mc.player.sendChatMessage("> Fuck You " + event.getName() + " Im Glad you Left!");
            } else {
                mc.player.sendChatMessage("> My Friend " + event.getName() + " Just left ;(");
            }
            joinLeaveMsg = 0;
        }
    }

    @SubscribeEvent
    public void finishEating(LivingEntityUseItemEvent.Finish event) {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 10 + 1);
        if (event.getEntity() == mc.player) {
            if (event.getItem().getItem() instanceof ItemFood || event.getItem().getItem() instanceof ItemAppleGold) {
                eaten++;
                if (eaten >= randomNum) {
                            mc.player.sendChatMessage("> I Just Ate " + eaten + " GoldenApples Thanks To CousinWare!");

                        eaten = 0;
                        eattingDelay = 0;
                    }
                }
            }
        }

    public double getMovement() {
        if (movement.getValBoolean()) {
            double movementX = mc.player.motionX;
            double movementZ = mc.player.motionZ;

            if (movementX < 0) {
                updatedX = movementX * -1;
            } else {
                updatedX = movementX;
            }

            if (movementZ < 0) {
                updatedZ = movementZ * -1;
            } else {
                updatedZ = movementZ;
            }


            double updatedMovement = updatedX + updatedZ;
            finalMovement += updatedMovement;

        }
        return finalMovement;


    }

    public void onEnable() {
        firstPlace = true;
    }
}
