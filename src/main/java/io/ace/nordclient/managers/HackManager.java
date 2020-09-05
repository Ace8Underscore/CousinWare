package io.ace.nordclient.managers;

import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.combat.AutoTotem;
import io.ace.nordclient.hacks.combat.FastXp;
import io.ace.nordclient.hacks.misc.LogoutCoords;
import io.ace.nordclient.hacks.misc.QuickDrop;
import io.ace.nordclient.hacks.movement.*;
import io.ace.nordclient.hacks.player.AntiVoid;
import io.ace.nordclient.hacks.render.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HackManager {
    public static ArrayList<Hack> hacks;
    private static String allHackNames = "Hacks: ";
    private static String officialAllHackNames;

    public HackManager(){
        hacks = new ArrayList<>();
        addHack(new TestCommand());
        addHack(new io.ace.nordclient.hacks.render.ArrayList());
        addHack(new AutoTotem());
        addHack(new Jesus());
        addHack(new AntiFog());
        addHack(new GoonSquad());
        addHack(new Swing());
        addHack(new Velocity());
        addHack(new LogoutCoords());
        addHack(new AntiVoid());
        addHack(new FastWeb());
        addHack(new FastXp());
        addHack(new FastSwim());
        addHack(new QuickDrop());
        addHack(new Hand());
        addHack(new SelfParticle());
        addHack(new ElytraFly());
        //addHack(new ElytraFly2());
        addHack(new SkyColor());
        addHack(new ClickGuiHack());



    }
    public static void addHack(Hack h){
        hacks.add(h);
    }

    public static ArrayList<Hack> getHacks() {
        return hacks;
    }

//help m e plea ssse aa


    public static Hack getHackByName(String name){
        return getHacks().stream().filter(hm->hm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static void onUpdate() {
        hacks.stream().filter(Hack::isEnabled).forEach(Hack::onUpdate);
    }

    public static String getAllHackList() {
        HackManager.getHacks()
                .forEach(hack -> {
                    officialAllHackNames = allHackNames + " " + hack.name;

                });

        return officialAllHackNames;
    }

    public static ArrayList<Hack> getHacksInCategory(Hack.Category c){
        return (ArrayList<Hack>) getHacks().stream().filter(h -> h.getCategory().equals(c)).collect(Collectors.toList());
    }


    public static void onBind(int key) {
        if (key == 0) return;
        hacks.forEach(hack -> {
            if(hack.getBind() == key){
                hack.toggle();
            }
        });
    }
}
