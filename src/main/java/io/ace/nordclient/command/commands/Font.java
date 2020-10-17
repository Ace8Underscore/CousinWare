package io.ace.nordclient.command.commands;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.utilz.font.CFontRenderer;


public class Font extends Command{


    @Override
    public String[] getClientAlias() {
        return new String[]{"font"};
    }

    @Override
    public String getClientSyntax() {
        return "font (fontname)";
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        String font = args[0].replace("_", " ");
        CousinWare.INSTANCE.fontRenderer = new CFontRenderer(new java.awt.Font(font, java.awt.Font.PLAIN, 18), true, false);
        CousinWare.INSTANCE.fontRenderer.setFontName(font);
        CousinWare.INSTANCE.fontRenderer.setFontSize(18);
            }
    }


