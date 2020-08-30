package io.ace.nordclient.command.commands;

import io.ace.nordclient.command.Command;
import io.ace.nordclient.managers.FriendManager;

public class FriendList extends Command {

    /**
     * @author Ace________/Ace_#1233
     */

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
        FriendManager.getFriends()
                .stream()
                .forEach(friend ->  {
                    Command.sendClientSideMessage(friend.getName());
                });



            }
        }
