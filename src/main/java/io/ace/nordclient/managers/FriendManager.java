package io.ace.nordclient.managers;

import io.ace.nordclient.utilz.FriendUtil;

import java.util.ArrayList;
import java.util.List;

public class  FriendManager {
    /**
     * @author Ace________/Ace_#1233
     *
     */

    public static List<FriendUtil> friends;
    private static boolean friendValue;
    private static String friened;
    public FriendManager(){
        friends = new ArrayList<>();

    }

    public static List<FriendUtil> getFriends() {
        return friends;
    }

    public static void addFriend(String name) {
        friends.add(new FriendUtil(name));
    }

    public static void removeFriend(String name){
        friends.remove(getFriendByName(name));
    }

    public static boolean isFriend(String name) {
        getFriends()
                .stream()
                .forEach(friend -> {
                    friened = friend.getName();
                });

        if (!friened.equals(name)) {
            friendValue = true;
        } else {
            friendValue = false;
        }
        return friendValue;
    }



    public static FriendUtil getFriendByName(String name){
        FriendUtil friend = null;
        for(FriendUtil f : getFriends()){
            if(f.getName().equalsIgnoreCase(name)) friend = f;
        }
        return friend;
    }



    }

