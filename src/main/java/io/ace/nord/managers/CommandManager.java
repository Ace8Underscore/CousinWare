package io.ace.nord.managers;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nord.command.Command;
import io.ace.nord.command.commands.*;


import java.util.ArrayList;

public class CommandManager {
    private static ArrayList<Command> commands;
    boolean b;

    public static void initClientCommands(){
        commands = new ArrayList<>();
        addCommand(new Help());
        addCommand(new Prefix());
        addCommand(new AllCommands());
        addCommand(new Toggle());
        addCommand(new Hacks());
        addCommand(new Drawn());
        addCommand(new Bind());

    }

    public static void addCommand(Command c){
        commands.add(c);
    }

    public static ArrayList<Command> getClientCommands(){
        return commands;
    }

    public void callClientCommand(String input){
        String[] split = input.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        String command = split[0];
        String args = input.substring(command.length()).trim();
        b = false;
        commands.forEach(c ->{
            for(String s : c.getClientAlias()) {
                if (s.equalsIgnoreCase(command)) {
                    b = true;
                    try {
                        c.onClientCommand(args, args.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"));
                    } catch (Exception e) {
                        Command.sendClientSideMessage(ChatFormatting.RED + c.getClientSyntax());
                    }
                }
            }
        });
        if(!b) Command.sendClientSideMessage(ChatFormatting.RED + "Unknown command!");
    }

}