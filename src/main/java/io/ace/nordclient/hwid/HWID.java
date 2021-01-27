package io.ace.nordclient.hwid;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HWID {

    static List<String> hwids = new ArrayList<>();

    public static URL pastebin;

    static {
        try {
            pastebin = new URL("https://pastebin.com/raw/0PKUJaf5");
        } catch (MalformedURLException e) {

        }
    }


    public HWID() throws MalformedURLException {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(pastebin.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                hwids.add(inputLine);
            }
        } catch(Exception e){

        }
    }

    public static boolean isGoodHWID(String id){
        return hwids.contains(id);
    }

}
