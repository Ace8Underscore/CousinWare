package io.ace.nordclient.guinew;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snow {
    public Minecraft mc = Minecraft.getMinecraft();
    public double x,y;

    public static List<Snow> render = new ArrayList<>();

    public static List<Snow> getRender() {
        return render;
    }

    public Snow(double x,double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void drawSnow() {
        Gui.drawRect((int) x, (int) y, (int) x + 1, (int) y + 1, new Color(255, 255, 255).getRGB());
        Gui.drawRect((int) x + 2, (int) y, (int) x + 3, (int) y + 1, new Color(255, 255, 255).getRGB());
        Gui.drawRect((int) x + 1, (int) y + 1, (int) x + 2, (int) y + 2, new Color(255, 255, 255).getRGB());
        Gui.drawRect((int) x, (int) y + 2, (int) x + 1, (int) y + 3, new Color(255, 255, 255).getRGB());
        Gui.drawRect((int) x+2, (int) y + 2, (int) x+3, (int) y + 3, new Color(255, 255, 255).getRGB());
    }
}