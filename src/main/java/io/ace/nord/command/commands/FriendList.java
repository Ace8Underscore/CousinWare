package io.ace.nord.command.commands;

import io.ace.nord.command.Command;
import io.ace.nord.friend.Friends;

public class FriendList extends Command {

    @Override
    public String[] getClientAlias() {
        return new String[]{"friendlist", "friendslist"};
    }

    @Override
    public String getClientSyntax() {
        return "FriendList";
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        Friends.getFriends()
                .stream()
                .forEach(friend ->  {
                    Command.sendClientSideMessage(friend.getName());
                });



            }
        }
