package io.ace.nord.utilz.configz;

import com.mojang.realmsclient.gui.ChatFormatting;

import io.ace.nord.NordClient;
import io.ace.nord.command.Command;
import io.ace.nord.hacks.Hack;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class ConfigUtils {
    Minecraft mc = Minecraft.getMinecraft();
    public File Nord;
    public File Settings;

    public ConfigUtils() {
        this.Nord = new File(mc.gameDir + File.separator + "NordClient");
        if (!this.Nord.exists()) {
            this.Nord.mkdirs();
        }

       /* this.Settings = new File(mc.gameDir + File.separator + "NordClient" + File.separator + "Settings");
        if (!this.Settings.exists()) {
            this.Settings.mkdirs();
        } */

        loadMods();
        loadDrawn();
        loadBinds();
        loadPrefix();

    }


    public void saveBinds() {
        try {
            File file = new File(this.Nord.getAbsolutePath(), "Binds.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = NordClient.INSTANCE.hackManager.getHacks().iterator();

            while (var3.hasNext()) {
                Hack hack = (Hack) var3.next();
                out.write(hack.getName() + ":" + Keyboard.getKeyName(hack.getBind()));
                out.write("\r\n");
            }

            out.close();
        } catch (Exception var5) {
        }

    }

    public void loadBinds() {
        try {
            File file = new File(this.Nord.getAbsolutePath(), "Binds.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = br.readLine()) != null) {
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                String bind = curLine.split(":")[1];
                for (Hack h : NordClient.INSTANCE.hackManager.getHacks()) {
                    if (h != null && h.getName().equalsIgnoreCase(name)) {
                        h.setBind(Keyboard.getKeyIndex(bind));
                    }
                }
            }

            br.close();
        } catch (Exception var11) {
            var11.printStackTrace();
            //saveBinds();
        }

    }


    public void saveMods() {
        try {
            File file = new File(this.Nord.getAbsolutePath(), "EnabledHacks.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = NordClient.INSTANCE.hackManager.getHacks().iterator();

            while (var3.hasNext()) {
                Hack hack = (Hack) var3.next();
                if (hack.isEnabled()) {
                    out.write(hack.getName());
                    out.write("\r\n");
                }
            }

            out.close();
        } catch (Exception var5) {
        }

    }

 /*   public void saveFriends() {
        try {
            File file = new File(this.Nord.getAbsolutePath(), "Friends.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = Friends.getFriends().iterator();

            while(var3.hasNext()) {
                Friend f = (Friend)var3.next();
                out.write(f.getName());
                out.write("\r\n");
            }

            out.close();
        } catch (Exception var5) {
        }

    } */

 /*   public void loadFriends() {
        try {
            File file = new File(this.Nord.getAbsolutePath(), "Friends.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            Friends.friends.clear();
            String line;
            while((line = br.readLine()) != null) {
                NordClient.getInstance().friends.addFriend(line);
            }

            br.close();
        } catch (Exception var6) {
            var6.printStackTrace();
            //saveFriends();
        }

    }

    public void saveEnemies() {
        try {
            File file = new File(this.Nord.getAbsolutePath(), "Enemies.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = Enemies.getEnemies().iterator();

            while (var3.hasNext()) {
                Enemy e = (Enemy) var3.next();
                out.write(e.getName());
                out.write("\r\n");
            }

            out.close();
        } catch (Exception var5) {
        }
    }

   public void loadEnemies() {
        try {
            File file = new File(this.Nord.getAbsolutePath(), "Enemies.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            Enemies.enemies.clear();
            String line;
            while((line = br.readLine()) != null) {
                Enemies.addEnemy(line);
            }

            br.close();
        } catch (Exception var6) {
            var6.printStackTrace();
            //saveEnemies();
        }
    } */

 /*   public void saveGui() {
        try {
            File file = new File(this.Nord.getAbsolutePath(), "Gui.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = ClickGUI.panels.iterator();

            while(var3.hasNext()) {
                Panel p = (Panel)var3.next();
                out.write(p.title + ":" + p.x + ":" + p.y + ":" + p.extended);
                out.write("\r\n");
            }

            out.close();
        } catch (Exception var5) {
        }

    }

    public void loadGui() {
        try {
            File file = new File(this.Nord.getAbsolutePath(), "Gui.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while((line = br.readLine()) != null) {
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                String x = curLine.split(":")[1];
                String y = curLine.split(":")[2];
                String e = curLine.split(":")[3];
                double x1 = Double.parseDouble(x);
                double y1 = Double.parseDouble(y);
                boolean ext = Boolean.parseBoolean(e);
                Panel p = ClickGUI.getPanelByName(name);
                if (p != null) {
                    p.x = x1;
                    p.y = y1;
                    p.extended = ext;
                }
            }

            br.close();
        } catch (Exception var17) {
            var17.printStackTrace();
            //this.saveGui();
        }

    }

    public void saveHudComponents() {
        try {
            File file = new File(this.Nord.getAbsolutePath(), "HudComponents.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = HudComponentManager.hudComponents.iterator();

            while(var3.hasNext()) {
                Panel p = (Panel)var3.next();
                out.write(p.title + ":" + p.x + ":" + p.y + ":" + p.extended + ":" + p.isHudComponentPinned);
                out.write("\r\n");
            }

            out.close();
        } catch (Exception var5) {
        }

    }

    public void loadHudComponents() {
        try {
            File file = new File(this.Nord.getAbsolutePath(), "HudComponents.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while((line = br.readLine()) != null) {
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                String x = curLine.split(":")[1];
                String y = curLine.split(":")[2];
                String e = curLine.split(":")[3];
                String pin = curLine.split(":")[4];
                double x1 = Double.parseDouble(x);
                double y1 = Double.parseDouble(y);
                boolean ex = Boolean.parseBoolean(e);
                boolean pinned = Boolean.parseBoolean(pin);
                Panel p = HudComponentManager.getHudComponentByName(name);
                if (p != null) {
                    p.x = x1;
                    p.y = y1;
                    p.extended = ex;
                    p.isHudComponentPinned = pinned;
                }
            }

            br.close();
        } catch (Exception var17) {
            var17.printStackTrace();
            //this.saveHudComponents();
        }

    } */

    public void savePrefix() {
        try {
            File file = new File(this.Nord.getAbsolutePath(), "CommandPrefix.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(Command.getClientPrefix());
            out.write("\r\n");
            out.close();
        } catch (Exception var3) {
        }

    }

    public void loadPrefix() {
        try {
            File file = new File(this.Nord.getAbsolutePath(), "CommandPrefix.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = br.readLine()) != null) {
                Command.setClientPrefix(line);
            }

            br.close();
        } catch (Exception var6) {
            var6.printStackTrace();
            //savePrefix();
        }

    }

   /* public void saveFont() {
        try {
            File file = new File(this.Nord.getAbsolutePath(), "Font.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(NordClient.fontRenderer.getFontName() + ":" + NordClient.fontRenderer.getFontSize());
            out.write("\r\n");
            out.close();
        } catch (Exception var3) {
        }

    } */


    public void saveDrawn() {
        try {
            File file = new File(this.Nord.getAbsolutePath(), "Drawn.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = NordClient.INSTANCE.hackManager.getHacks().iterator();

            while (var3.hasNext()) {
                Hack hack = (Hack) var3.next();
                out.write(hack.getName() + ":" + hack.isDrawn());
                out.write("\r\n");
            }

            out.close();
        } catch (Exception var5) {
        }

    }

    public void loadDrawn() {
        try {
            File file = new File(this.Nord.getAbsolutePath(), "Drawn.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = br.readLine()) != null) {
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                String isOn = curLine.split(":")[1];
                boolean drawn = Boolean.parseBoolean(isOn);
                for (Hack h : NordClient.INSTANCE.hackManager.getHacks()) {
                    if (h.getName().equalsIgnoreCase(name)) {
                        h.drawn = drawn;
                    }
                }
            }

            br.close();
        } catch (Exception var11) {
            var11.printStackTrace();
            //saveDrawn();
        }

    }


    public void loadMods() {
        try {
            File file = new File(this.Nord.getAbsolutePath(), "EnabledHacks.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = br.readLine()) != null) {
                Iterator var6 = NordClient.INSTANCE.hackManager.getHacks().iterator();

                while (var6.hasNext()) {
                    Hack h = (Hack) var6.next();
                    if (h.getName().equals(line)) {
                        h.enable();
                    }
                }
            }

            br.close();
        } catch (Exception var8) {
            var8.printStackTrace();
            //this.saveHacks();
        }

    }
}

  /*  public void saveSettingsList() {
        File file;
        BufferedWriter out;
        Iterator var3;
        Setting i;
        try {
            file = new File(Settings.getAbsolutePath(), "Number.txt");
            out = new BufferedWriter(new FileWriter(file));
            var3 = me.finz0.Nord.NordClient.getInstance().settingsManager.getSettings().iterator();

            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.isSlider()) {
                    out.write(i.getId() + ":" + i.getValDouble() + ":" + i.getParentMod().getName() + "\r\n");
                }
            }

            out.close();
        } catch (Exception var7) {
        }

        try {
            file = new File(Settings.getAbsolutePath(), "Boolean.txt");
            out = new BufferedWriter(new FileWriter(file));
            var3 = me.finz0.Nord.NordClient.getInstance().settingsManager.getSettings().iterator();

            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.isCheck()) {
                    out.write(i.getId() + ":" + i.getValBoolean() + ":" + i.getParentMod().getName() + "\r\n");
                }
            }

            out.close();
        } catch (Exception var6) {
        }

        try {
            file = new File(Settings.getAbsolutePath(), "String.txt");
            out = new BufferedWriter(new FileWriter(file));
            var3 = me.finz0.Nord.NordClient.getInstance().settingsManager.getSettings().iterator();

            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.isCombo()) {
                    out.write(i.getId() + ":" + i.getValString() + ":" + i.getParentMod().getName() + "\r\n");
                }
            }

            out.close();
        } catch (Exception var5) {
        }

        try {
            file = new File(Settings.getAbsolutePath(), "Color.txt");
            out = new BufferedWriter(new FileWriter(file));
            var3 = me.finz0.Nord.NordClient.getInstance().settingsManager.getSettings().iterator();

            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.isColorPicker()) {
                    out.write(i.getId() + ":" + i.getValColor().getRGB() + ":" + i.getParentMod().getName() + "\r\n");
                }
            }

            out.close();
        } catch (Exception var7) {
        }

    }

    public void loadSettingsList() {
        File file;
        FileInputStream fstream;
        DataInputStream in;
        BufferedReader br;
        String line;
        String curLine;
        String name;
        String isOn;
        String m;
        Setting mod;
        int color;
        try {
            file = new File(Settings.getAbsolutePath(), "Number.txt");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            while((line = br.readLine()) != null) {
                curLine = line.trim();
                name = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : NordClient.getInstance().moduleManager.getModules()) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = me.finz0.Nord.NordClient.getInstance().settingsManager.getSettingByID(name);
                        mod.setValDouble(Double.parseDouble(isOn));
                    }
                }
            }

            br.close();
        } catch (Exception var13) {
            var13.printStackTrace();
            //saveSettingsList();
        }

        try {
            file = new File(Settings.getAbsolutePath(), "Color.txt");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            while((line = br.readLine()) != null) {
                curLine = line.trim();
                name = curLine.split(":")[0];
                color = Integer.parseInt(curLine.split(":")[1]);
                m = curLine.split(":")[2];
                for(Module mm : NordClient.getInstance().moduleManager.getModules()) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = me.finz0.Nord.NordClient.getInstance().settingsManager.getSettingByID(name);
                        mod.setValColor(new Color(color));
                    }
                }
            }

            br.close();
        } catch (Exception var13) {
            var13.printStackTrace();
            //saveSettingsList();
        }

        try {
            file = new File(Settings.getAbsolutePath(), "Boolean.txt");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            while((line = br.readLine()) != null) {
                curLine = line.trim();
                name = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : NordClient.getInstance().moduleManager.getModules()) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = me.finz0.Nord.NordClient.getInstance().settingsManager.getSettingByID(name);
                        mod.setValBoolean(Boolean.parseBoolean(isOn));
                    }
                }
            }

            br.close();
        } catch (Exception var12) {
            var12.printStackTrace();
            //saveSettingsList();
        }

        try {
            file = new File(Settings.getAbsolutePath(), "String.txt");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            while((line = br.readLine()) != null) {
                curLine = line.trim();
                name = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : NordClient.getInstance().moduleManager.getModules()) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = me.finz0.Nord.NordClient.getInstance().settingsManager.getSettingByID(name);
                        mod.setValString(isOn);
                    }
                }
            }

            br.close();
        } catch (Exception var11) {
            var11.printStackTrace();
            //aveSettingsList();
        }

    }
} */