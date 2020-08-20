package io.ace.nord.managers;

import io.ace.nord.hacks.Hack;
import io.ace.nord.hacks.combat.AutoTotem;
import io.ace.nord.hacks.movement.Jesus;
import io.ace.nord.hacks.render.AntiFog;
import io.ace.nord.hacks.render.GoonSquad;
import io.ace.nord.hacks.render.TestCommand;

import java.util.ArrayList;

public class HackManager {
    public static ArrayList<Hack> hacks;
    private static String allHackNames = "Hacks: ";
    private static String officialAllHackNames;

    public HackManager(){
        hacks = new ArrayList<>();
        addHack(new TestCommand());
        addHack(new io.ace.nord.hacks.render.ArrayList());
        addHack(new AutoTotem());
        addHack(new Jesus());
        addHack(new AntiFog());
        addHack(new GoonSquad());



    }
    public static void addHack(Hack h){
        hacks.add(h);
    }

    public static ArrayList<Hack> getHacks() {
        return hacks;
    }




    public static Hack getHackByName(String name){
        return getHacks().stream().filter(hm->hm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
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
