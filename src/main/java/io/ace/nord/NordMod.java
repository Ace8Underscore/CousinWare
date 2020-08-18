package io.ace.nord;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = NordMod.MODID, name = NordMod.NAME, version = NordMod.VERSION)
public class NordMod
{
    public static final String MODID = "nordclient";
    public static final String NAME = "Nord Client";
    public static final String VERSION = "b1.0";



    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {


    }
}
