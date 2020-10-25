package io.ace.nordclient.hud;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.RenderEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author Ace________/Ace_#1233
 */

public class Hud {
    public static final Minecraft mc = Minecraft.getMinecraft();
    public Category category = Category.HUD;

    public String name;
    public String held = "Waiting";
    public int x;
    public int y;
    public int width;
    public boolean enabled;
    public ScaledResolution resolution = new ScaledResolution(mc);

    public Hud(String hudName, int x, int y) {
        name = hudName;
        enabled = false;
        this.x = x;
        this.y = y;



    }

    public int getX() {
        return this.x;
    }

    public void setX(int newx) {
        this.x = newx;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int newy) {
        this.y = newy;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int newwidth) {
        this.width = newwidth;
    }

    public String getHeld() {
        return held;
    }

    public void setHeld(String name) {
        this.held = this.getName();
    }




    public void onUpdate(){}

    public void onRender(){}

    public void onWorldRender(RenderEvent event) {}


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
        CousinWare.INSTANCE.getEventManager().addEventListener(this);
        MinecraftForge.EVENT_BUS.register(this);
        setEnabled(true);
        onEnable();
    }

    public void disable() {
        CousinWare.INSTANCE.getEventManager().removeEventListener(this);
        MinecraftForge.EVENT_BUS.unregister(this);
        setEnabled(false);

        onDisable();
    }

    public Category getCategory(){
        return category;
    }

    public void setCategory(Category c){
        category = c;
    }

    public String getName() {
        return name;
    }

    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
    }



    public enum Category {
        HUD

    }




}


