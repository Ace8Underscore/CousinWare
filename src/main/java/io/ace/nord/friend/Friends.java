package io.ace.nord.friend;

import java.util.ArrayList;
import java.util.List;

public class Friends {

    public static List<Friend> friends;
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
        return true;
    }
    public static Friend getFriendByName(String name){
        Friend fr = null;
        for(Friend f : getFriends()){
            if(f.getName().equalsIgnoreCase(name)) fr = f;
        }
        return fr;
    }



    }

