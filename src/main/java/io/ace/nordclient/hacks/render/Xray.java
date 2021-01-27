package io.ace.nordclient.hacks.render;

import io.ace.nordclient.hacks.Hack;

import java.util.ArrayList;

public class Xray extends Hack {

    public Xray() {
        super("Xray", Category.RENDER, 1232313);
    }

    public static ArrayList<String> xrayBlocks;

    @Override
    public void onEnable() {
        mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onUpdate() {
    }

    @Override
    public void onDisable() {
        mc.renderGlobal.loadRenderers();
    }
}
