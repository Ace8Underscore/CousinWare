package io.ace.nordclient;

import io.ace.nordclient.command.commands.*;
import io.ace.nordclient.event.EventProcessor;
import io.ace.nordclient.gui.ClickGUI2;
import io.ace.nordclient.gui.Frame;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.client.*;
import io.ace.nordclient.hacks.combat.*;
import io.ace.nordclient.hacks.exploit.*;
import io.ace.nordclient.hacks.misc.*;
import io.ace.nordclient.hacks.movement.*;
import io.ace.nordclient.hacks.player.*;
import io.ace.nordclient.hacks.render.*;
import io.ace.nordclient.hacks.render.Crystal;
import io.ace.nordclient.hud.ClickGuiHUD;
import io.ace.nordclient.hud.hudcomponets.*;
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
import org.lwjgl.opengl.Display;
import team.stiff.pomelo.EventManager;
import team.stiff.pomelo.impl.annotated.AnnotatedEventManager;

import java.awt.*;
import java.awt.Font;
import java.util.ArrayList;

@Mod(modid = CousinWare.MODID, name = CousinWare.NAME, version = CousinWare.VERSION)
public class CousinWare
{
    public static final String MODID = "cousinware";
    public static final String NAME = "CousinWare";
    public static final String VERSION = "v1.4.1";

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
        Display.setTitle("CousinWare " + VERSION);

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        eventProcessor = new EventProcessor();
        eventProcessor.init();
        loadClientCommands();
        TpsUtils tpsUtils = new TpsUtils();
        settingsManager = new SettingsManager();
        friends = new FriendManager();
        loadHuds();
        loadHacks();
        fontRenderer = new CFontRenderer(new Font("Verdana", Font.CENTER_BASELINE, 17), Core.antiAlias.getValBoolean(), Core.fractionalMetrics.getValBoolean());
        clickGui2 = new ClickGUI2();
        clickGuiHUD = new ClickGuiHUD();
        configUtils = new ConfigUtils();
        //Display.setTitle("CousinWare " + VERSION);
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

    public void loadClientCommands() {
        CommandManager.commands = new ArrayList<>();
        CommandManager.addCommand(new Help());
        CommandManager.addCommand(new Prefix());
        CommandManager.addCommand(new AllCommands());
        CommandManager.addCommand(new Toggle());
        CommandManager.addCommand(new Hacks());
        CommandManager.addCommand(new Drawn());
        CommandManager.addCommand(new Bind());
        CommandManager.addCommand(new Friend());
        CommandManager.addCommand(new FriendList());
        CommandManager.addCommand(new Ping());
        CommandManager.addCommand(new Description());
        CommandManager.addCommand(new Stack());
        CommandManager.addCommand(new Summon());
        CommandManager.addCommand(new Pyro());
        CommandManager.addCommand(new Set());
        CommandManager.addCommand(new Setting());
        CommandManager.addCommand(new ReloadSound());
        CommandManager.addCommand(new Spotify());
        CommandManager.addCommand(new RideEntity());
        CommandManager.addCommand(new io.ace.nordclient.command.commands.Font());
    }

    public void loadHuds() {
        HudManager.hudElement = new ArrayList<>();

        HudManager.addHud(new ArmorHud());
        HudManager.addHud(new Totem());
        HudManager.addHud(new Gapple());
        HudManager.addHud(new Exp());
        HudManager.addHud(new io.ace.nordclient.hud.hudcomponets.Crystal());
        HudManager.addHud(new Obsidian());
        HudManager.addHud(new GoonSquad());
    }

    public void loadHacks() {
        HackManager.hacks = new ArrayList<>();
        //client
        HackManager.addHack(new ClickGuiHack());
        HackManager.addHack(new ClickGuiHudHack());
        HackManager.addHack(new Core());
        //combat
        HackManager.addHack(new Aura());
        HackManager.addHack(new AutoBedBombDumb());
        HackManager.addHack(new AutoOffHand());
        HackManager.addHack(new AutoTntMinecart());
        HackManager.addHack(new AutoTotem());
        HackManager.addHack(new AutoTrap());
        HackManager.addHack(new Burrow());
        HackManager.addHack(new Criticals());
        HackManager.addHack(new CrystalAura());
        HackManager.addHack(new FastXp());
        HackManager.addHack(new SpeedMine());
        HackManager.addHack(new Surround());
        //exploit
        HackManager.addHack(new AutoMinecartRefill());
        HackManager.addHack(new LagBar());
        HackManager.addHack(new Lagger());
        HackManager.addHack(new NoBreakLoss());
        HackManager.addHack(new NoFallingAni());
        HackManager.addHack(new NoMinecartLag());
        //misc
        HackManager.addHack(new AntiRegear());
        HackManager.addHack(new AutoWither());
        //HackManager.addHack(new BedrockFinder());
        HackManager.addHack(new BoatBypass());
        HackManager.addHack(new DelayedSounds());
        HackManager.addHack(new DonkeyAlert());
        HackManager.addHack(new EnchantColor());
        HackManager.addHack(new FancyChat());
        HackManager.addHack(new LogoutCoords());
        HackManager.addHack(new MCF());
        HackManager.addHack(new NoEntityTrace());
        HackManager.addHack(new NoInteract());
        HackManager.addHack(new QuickDrop());
        HackManager.addHack(new ShulkerMod());
        HackManager.addHack(new Spammer());
        HackManager.addHack(new ToggleMsgs());
        HackManager.addHack(new TwoBeePacketLogger());
        //movement
        HackManager.addHack(new ElytraFly());
        HackManager.addHack(new FastSwim());
        HackManager.addHack(new FastWeb());
        HackManager.addHack(new Fly());
        HackManager.addHack(new Jesus());
        HackManager.addHack(new ReverseStep());
        HackManager.addHack(new Sprint());

        HackManager.addHack(new Step());
        HackManager.addHack(new Strafe());
        HackManager.addHack(new Velocity());
        //player
        HackManager.addHack(new AntiVoid());
        HackManager.addHack(new Freecam());
        HackManager.addHack(new GhostGap());
        HackManager.addHack(new NoSlow());
        HackManager.addHack(new NoSlow2b());
        HackManager.addHack(new Scaffold());
        //render
        HackManager.addHack(new AntiFog());
        HackManager.addHack(new io.ace.nordclient.hacks.render.ArrayList());
        HackManager.addHack(new BlockHighlight());
        HackManager.addHack(new ClientName());
        HackManager.addHack(new Crystal());
        HackManager.addHack(new FriendTab());
        HackManager.addHack(new FullBright());
        HackManager.addHack(new InfiniteChatlength());
        //HackManager.addHack(new ItemESP());
        //HackManager.addHack(new NameTags());
        HackManager.addHack(new NoLag());
        HackManager.addHack(new Overlay());
        HackManager.addHack(new PlayerESP());
        HackManager.addHack(new SelfParticle());
        HackManager.addHack(new SkyColor());
        HackManager.addHack(new StorageESP());
        HackManager.addHack(new Swing());
        HackManager.addHack(new ViewModelChanger());
        HackManager.addHack(new Welcomer());
    }

}
