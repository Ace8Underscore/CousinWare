package io.ace.nord.friend;

import java.util.ArrayList;
import java.util.List;

public class Friends {

    public static List<Friend> friends;
    private static boolean friendValue;
    private static String friened;
    public Friends(){
        friends = new ArrayList<>();

    }

    public static List<Friend> getFriends() {
        return friends;
    }

    public static void addFriend(String name) {
        friends.add(new Friend(name));
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



    public static Friend getFriendByName(String name){
        Friend friend = null;
        for(Friend f : getFriends()){
            if(f.getName().equalsIgnoreCase(name)) friend = f;
        }
        return friend;
    }



    }

