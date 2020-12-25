package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.FriendManager;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;

public class VisualRange extends Hack {

    Setting noFriend;
    Setting antiSpam;

    public VisualRange() {
        super("VisualRange", Category.MISC, 1);
        //CousinWare.INSTANCE.settingsManager.rSetting(noFriend = new Setting("NoFriend", this, true, "VisualRangeNoFriend"));
        CousinWare.INSTANCE.settingsManager.rSetting(antiSpam = new Setting("AntiSpam", this, true, "VisualRangeAntiSpam"));
    }

    ArrayList<String> seenPlayers = new ArrayList<>();
    int delay = 0;

    public void onUpdate() {

        for (Entity player : mc.world.loadedEntityList) {
            if (player instanceof EntityPlayer) {
                if (!FriendManager.isFriend(player.getName())) {
                    if (antiSpam.getValBoolean()) delay++;
                    if (!seenPlayers.contains(player.getName())) {
                        if (antiSpam.getValBoolean()) {
                            if (delay > 10) {
                                Command.sendClientSideMessage(player.getName() + " Has Entered Visual Range");
                                seenPlayers.add(player.getName());
                                delay = 0;
                            }
                        } else {
                            Command.sendClientSideMessage(player.getName() + " Has Entered Visual Range");
                            seenPlayers.add(player.getName());
                        }
                    }

                    if (!isInVisualRange(player.getName())) {
                        if (seenPlayers.toString().contains(player.getName())) {
                            seenPlayers.remove(player.getName());
                        }
                    }
                }
            }
        }
    }
    //

        public boolean isInVisualRange(String player) {
        return mc.world.playerEntities.toString().contains(player);
        }

    }


