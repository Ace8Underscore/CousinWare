package io.ace.nord.hacks.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nord.friend.Friends;
import io.ace.nord.hacks.Hack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class GoonSquad extends Hack {
    public GoonSquad() {
        super("GoonSquad", Category.RENDER);

    }

    public static String friends;
    private String str;

    @SubscribeEvent
    public void onClientRender(TickEvent.RenderTickEvent event) {
        if (mc.world == null)
            return;
        AtomicInteger y = new AtomicInteger(2);
        mc.fontRenderer.drawStringWithShadow(ChatFormatting.BOLD + "GoonSquad", 100, 100 - 10, 16777215);
        for (Object o : mc.world.getLoadedEntityList()) {
            if (o instanceof EntityPlayer) {
                if (((EntityPlayer) o).getName() != mc.player.getName()) {
                    Friends.getFriends()
                            .stream()
                            .forEach(friend -> {
                                if (friend.getName().contains(((EntityPlayer) o).getName())) {
                                    friends = ((EntityPlayer) o).getGameProfile().getName();
                                    str = " " + friends;
                                    mc.fontRenderer.drawStringWithShadow(str, 100, y.get() + 100, 16755200);
                                    y.addAndGet(12);
                                    //}
                                }

                            });
                }

            }
        }
    }
}



