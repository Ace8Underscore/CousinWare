package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.command.Command;
import io.ace.nordclient.event.EventRenderBlock;
import io.ace.nordclient.hacks.Hack;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class BedrockFinder extends Hack {

    public BedrockFinder() {
        super("IllegalFinder", Category.MISC, 1);

    }

    @Listener
    public void onWorldRender(EventRenderBlock eventRenderBlock) {
        if (eventRenderBlock.getBlockState().getBlock().equals(Blocks.BEDROCK)) {
            if (eventRenderBlock.getBlockPos().getY() >= 5) {
                Command.sendClientSideMessage("Found Illegal Bedrock! At X:" + eventRenderBlock.getBlockPos().getX() + " Y:" + eventRenderBlock.getBlockPos().getY() + " Z:" + eventRenderBlock.getBlockPos().getZ());
            }

        }
    }
    @Override
    public void onUpdate() {
        //if (Block.getBlockFromName("bedrock").)
    }
}



