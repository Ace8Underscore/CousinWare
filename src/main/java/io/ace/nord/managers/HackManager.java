package io.ace.nord.managers;

import io.ace.nord.hacks.Hack;
import io.ace.nord.hacks.TestCommand;

import java.util.ArrayList;

public class HackManager {
    public static ArrayList<Hack> hacks;

    public HackManager(){
        hacks = new ArrayList<>();
        addHack(new TestCommand());



    }
    public static void addHack(Hack h){
        hacks.add(h);
    }

    public static ArrayList<Hack> getHacks() {
        return hacks;
    }


    public static Hack getHackByName(String name){
        return getHacks().stream().filter(mm->mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
