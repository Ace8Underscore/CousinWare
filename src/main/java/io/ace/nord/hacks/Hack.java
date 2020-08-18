package io.ace.nord.hacks;

import io.ace.nord.NordClient;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

public class Hack {
    public static final Minecraft mc = Minecraft.getMinecraft();

    public String name;
    public String description;
    public Category category;
    public int bind;
    public boolean enabled;

    public Hack(String hackName, Category hackCategory) {
        name = hackName;
        description = " ";
        category = hackCategory;
        bind = Keyboard.KEY_NONE;
        enabled = false;

    }

    public Hack(String hackName, Category hackCategory, String hackDescription) {
        name = hackName;
        description = hackDescription;
        category = hackCategory;
        bind = Keyboard.KEY_NONE;
        enabled = false;

    }
    public void onUpdate(){}

    public void onRender(){}

    protected void onEnable(){
    }

    protected void onDisable(){
    }


    public boolean isEnabled(){
        return enabled;
    }

    public void setEnabled(boolean e){
        enabled = e;
    }


    public void toggle(){
        if(isEnabled()) {
            disable();
        }
        if(!isEnabled()){
            enable();
        }
    }

    public void enable() {
        NordClient.INSTANCE.getEventManager().addEventListener(this);
        setEnabled(true);
        //MinecraftForge.EVENT_BUS.register(this);
        onEnable();
    }

    public void disable() {
        NordClient.INSTANCE.getEventManager().removeEventListener(this);
        setEnabled(false);
        //MinecraftForge.EVENT_BUS.unregister(this);
        onDisable();
    }

    public Category getCategory(){
        return category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public enum Category {
        Test,
        //PLAYER,
        //MOVEMENT,
        //MISC,
        //WORLD,
        //RENDER,
        GUI
    }
}


