package io.ace.nordclient;

import io.ace.nordclient.event.EventProcessor;
import io.ace.nordclient.gui.ClickGUI;
import io.ace.nordclient.gui.DrawSnow;
import io.ace.nordclient.guinew.RubyClickGui;
import io.ace.nordclient.managers.CommandManager;
import io.ace.nordclient.managers.FriendManager;
import io.ace.nordclient.managers.HackManager;
import io.ace.nordclient.managers.SettingsManager;
import io.ace.nordclient.utilz.TpsUtils;
import io.ace.nordclient.utilz.configz.ConfigUtils;
import io.ace.nordclient.utilz.configz.ShutDown;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.stiff.pomelo.EventManager;
import team.stiff.pomelo.impl.annotated.AnnotatedEventManager;

@Mod(modid = CousinWare.MODID, name = CousinWare.NAME, version = CousinWare.VERSION)
public class CousinWare
{
    public static final String MODID = "cousinware";
    public static final String NAME = "CousinWare";
    public static final String VERSION = "v1.1.1";

    public static final Logger log = LogManager.getLogger(NAME);
    private EventManager eventManager;
    EventProcessor eventProcessor;
    public HackManager hackManager;
    public ConfigUtils configUtils;
    public FriendManager friends;
    public SettingsManager settingsManager;
    public ClickGUI clickGui;
    public DrawSnow drawSnow;

    //public ClickGUI clickGui;





    @Mod.Instance
    public static CousinWare INSTANCE;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        eventProcessor = new EventProcessor();
        eventProcessor.init();
        CommandManager.initClientCommands();
        TpsUtils tpsUtils = new TpsUtils();
        settingsManager = new SettingsManager();
        friends = new FriendManager();
        hackManager = new HackManager();
        clickGui = new ClickGUI();
        //drawSnow = new DrawSnow();
        configUtils = new ConfigUtils();
        Runtime.getRuntime().addShutdownHook(new ShutDown());
    }

    public EventManager getEventManager() {
        if (this.eventManager == null) {
            this.eventManager = new AnnotatedEventManager();
        }

        return this.eventManager;
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        //MinecraftForge.EVENT_BUS.register(new TestCommand());
    }

}
