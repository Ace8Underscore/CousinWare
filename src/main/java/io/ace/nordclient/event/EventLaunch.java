package io.ace.nordclient.event;

import io.ace.nordclient.hwid.HWID;

import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;

public class EventLaunch {

    public static void init() throws URISyntaxException {
        String currentHWID = String.valueOf(Runtime.getRuntime().availableProcessors() +
                //System.getenv("PROCESSOR_IDENTIFIER") +
                //System.getenv("PROCESSOR_ARCHITECTURE") +
                //System.getenv("PROCESSOR_ARCHITEW6432") +
                ////System.getenv("NUMBER_OF_PROCESSORS") +
                ((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize());
        if (!HWID.isGoodHWID(currentHWID)) {
            System.out.println(currentHWID);
            System.exit(0);
        }
        if (!HWID.pastebin.toString().equals("https://pastebin.com/raw/0PKUJaf5")) {
            System.out.println(currentHWID);
            System.exit(0);
        }


    }
}
