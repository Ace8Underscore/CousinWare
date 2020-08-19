package io.ace.nord.managers;

import io.ace.nord.hacks.Hack;
import io.ace.nord.hacks.combat.AutoTotem;
import io.ace.nord.hacks.test.TestCommand;

import java.util.ArrayList;

public class HackManager {
    public static ArrayList<Hack> hacks;

    public HackManager(){
        hacks = new ArrayList<>();
        addHack(new TestCommand());
        addHack(new io.ace.nord.hacks.render.ArrayList());
        addHack(new AutoTotem());



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

    public static void onBind(int key) {
        if (key == 0) return;
        hacks.forEach(hack -> {
            if(hack.getBind() == key){
                hack.toggle();
            }
        });
    }
}
