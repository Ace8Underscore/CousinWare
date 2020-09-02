package io.ace.nordclient.hacks;

import io.ace.nordclient.NordClient;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

public class Hack {
    public static final Minecraft mc = Minecraft.getMinecraft();

    public String name;
    public String description;
    public static Category category;
    public int bind;
    public boolean enabled;
    public boolean drawn;

    public Hack(String hackName, Category hackCategory) {
        name = hackName;
        description = " ";
        category = hackCategory;
        bind = Keyboard.KEY_NONE;
        enabled = false;
        drawn = true;

    }

    public Hack(String hackName, Category hackCategory, String hackDescription) {
        name = hackName;
        description = hackDescription;
        category = hackCategory;
        bind = Keyboard.KEY_NONE;
        enabled = false;
        drawn = true;

    }

    public int getBind(){
        return bind;
    }

    public void setBind(int b){
        bind = b;
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

    public boolean isDisabled(){ return !enabled; }


    public void setEnabled(boolean e){
        enabled = e;
    }


    public void toggle(){
        if(isEnabled()) {
            disable();
        } else if (!isEnabled()){
            enable();
        }
    }

    public void enable() {
        NordClient.INSTANCE.getEventManager().addEventListener(this);
        MinecraftForge.EVENT_BUS.register(this);
        setEnabled(true);
        //MinecraftForge.EVENT_BUS.register(this);
        onEnable();
    }

    public void disable() {
        NordClient.INSTANCE.getEventManager().removeEventListener(this);
        MinecraftForge.EVENT_BUS.unregister(this);
        setEnabled(false);
        //MinecraftForge.EVENT_BUS.unregister(this);
        onDisable();
    }
    public boolean isDrawn() {return drawn;}

    public static Category getCategory(){
        return category;
    }

    public void setCategory(Category c){
        category = c;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getHudInfo(){
        return "";
    }






    public enum Category {
        COMBAT,
        PLAYER,
        MOVEMENT,
        MISC,
        RENDER,
        CLIENT
    }
}


