package io.ace.nord.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nord.command.Command;
import io.ace.nord.friend.Friends;

public class Friend extends Command {

    @Override
    public String[] getClientAlias() {
        return new String[]{"Friend", "Friends"};
    }

    @Override
    public String getClientSyntax() {
        return "Friend (add/del) (Name)";
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        if (args[0].equals("add")) {
                Friends.addFriend(args[1]);
                Command.sendClientSideMessage(ChatFormatting.GREEN + args[1] + ChatFormatting.WHITE + " Has Been Added To The Friends List ");
            }


        if (args[0].equals("del")) {
            Friends.getFriends()
                    .stream()
                    .forEach(friend -> {
                        if (friend.getName().contains(args[1])) {
                            Friends.removeFriend(args[1]);
                            Command.sendClientSideMessage(ChatFormatting.DARK_RED + args[1] + " Has Been UnFriended");

                        }
                    });

        }
    }
}
