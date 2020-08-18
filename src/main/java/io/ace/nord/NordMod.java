package io.ace.nord;

import io.ace.nord.event.EventProcessor;
import io.ace.nord.hacks.TestCommand;
import io.ace.nord.managers.CommandManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import team.stiff.pomelo.EventManager;
import team.stiff.pomelo.impl.annotated.AnnotatedEventManager;

@Mod(modid = NordMod.MODID, name = NordMod.NAME, version = NordMod.VERSION)
public class NordMod
{
    public static final String MODID = "nordclient";
    public static final String NAME = "Nord Client";
    public static final String VERSION = "b1.0";

    private EventManager eventManager;
    EventProcessor eventProcessor;

    @Mod.Instance
    public static NordMod INSTANCE;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        eventProcessor = new EventProcessor();
        eventProcessor.init();
        CommandManager.initClientCommands();
    }

    public EventManager getEventManager() {
        if (this.eventManager == null) {
            this.eventManager = new AnnotatedEventManager();
        }

        return this.eventManager;
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new TestCommand());
    }

}
