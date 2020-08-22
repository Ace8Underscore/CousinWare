package io.ace.nordclient.managers;

import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.combat.AutoTotem;
import io.ace.nordclient.hacks.misc.LogoutCoords;
import io.ace.nordclient.hacks.movement.Jesus;
import io.ace.nordclient.hacks.movement.Velocity;
import io.ace.nordclient.hacks.render.AntiFog;
import io.ace.nordclient.hacks.render.GoonSquad;
import io.ace.nordclient.hacks.render.Swing;
import io.ace.nordclient.hacks.render.TestCommand;

import java.util.ArrayList;

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

    public static void onBind(int key) {
        if (key == 0) return;
        hacks.forEach(hack -> {
            if(hack.getBind() == key){
                hack.toggle();
            }
        });
    }
}
