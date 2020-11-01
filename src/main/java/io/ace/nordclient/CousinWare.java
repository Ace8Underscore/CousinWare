package io.ace.nordclient;

import io.ace.nordclient.event.EventProcessor;
import io.ace.nordclient.gui.ClickGUI2;
import io.ace.nordclient.gui.Frame;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hud.ClickGuiHUD;
import io.ace.nordclient.managers.*;
import io.ace.nordclient.utilz.TpsUtils;
import io.ace.nordclient.utilz.configz.ConfigUtils;
import io.ace.nordclient.utilz.configz.ShutDown;
import io.ace.nordclient.utilz.font.CFontRenderer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.stiff.pomelo.EventManager;
import team.stiff.pomelo.impl.annotated.AnnotatedEventManager;

import java.awt.*;

@Mod(modid = CousinWare.MODID, name = CousinWare.NAME, version = CousinWare.VERSION)
public class CousinWare
{
    public static final String MODID = "cousinware";
    public static final String NAME = "CousinWare";
    public static final String VERSION = "v1.3.2";

    public static final Logger log = LogManager.getLogger(NAME);
    private EventManager eventManager;
    EventProcessor eventProcessor;
    public HackManager hackManager;
    public HudManager hudManager;
    public ConfigUtils configUtils;
    public FriendManager friends;
    public SettingsManager settingsManager;
    public ClickGUI2 clickGui2;
    public ClickGuiHUD clickGuiHUD;
    public CFontRenderer fontRenderer;

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
        fontRenderer = new CFontRenderer(new Font("Verdana", Font.PLAIN, 18), true, false);
        friends = new FriendManager();
        hudManager = new HudManager();
        hackManager = new HackManager();
        clickGui2 = new ClickGUI2();
        clickGuiHUD = new ClickGuiHUD();
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

    }

}
