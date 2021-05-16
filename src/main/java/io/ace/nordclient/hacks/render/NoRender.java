package io.ace.nordclient.hacks.render;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;

public class NoRender extends Hack {

    public static Setting suffocateScreen;
    public static Setting minecart;
    public static Setting fallingAni;
    public static Setting eTable;
    public static Setting fire;
    public static Setting blockBreakEffect;
    public static Setting blockMineEffect;

    public NoRender() {
        super("NoRender", Category.RENDER, 13466128);

        CousinWare.INSTANCE.settingsManager.rSetting(suffocateScreen = new Setting("Suffocate", this, true, "NoRenderSuffocate"));
        CousinWare.INSTANCE.settingsManager.rSetting(minecart = new Setting("Minecart", this, false, "NoRenderMinecart"));
        CousinWare.INSTANCE.settingsManager.rSetting(fallingAni = new Setting("FallingBlocks", this, true, "NoRenderFallingAni"));
        CousinWare.INSTANCE.settingsManager.rSetting(eTable = new Setting("ETable", this, false, "NoRenderETable"));
        CousinWare.INSTANCE.settingsManager.rSetting(fire = new Setting("Fire", this, false, "NoRenderFire"));
        CousinWare.INSTANCE.settingsManager.rSetting(blockBreakEffect = new Setting("BreakParticles", this, true, "NoRenderBreakParticles"));
        CousinWare.INSTANCE.settingsManager.rSetting(blockMineEffect = new Setting("MineParticles", this, true, "NoRenderMineParticles"));


    }
}
