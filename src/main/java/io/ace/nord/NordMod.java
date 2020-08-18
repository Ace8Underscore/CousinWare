package io.ace.nord;

import io.ace.nord.hacks.TestCommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = NordMod.MODID, name = NordMod.NAME, version = NordMod.VERSION)
public class NordMod
{
    public static final String MODID = "nordclient";
    public static final String NAME = "Nord Client";
    public static final String VERSION = "b1.0";



    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {


    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {


    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new TestCommand());
    }

}
