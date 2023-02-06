package com.candy.utils;

public class VersionUtils {
    public static final String MINECRAFT_VERSION = "1.1.7";
    public static final int MINECRAFT_PROTOCOL = 113;
    public static final String[] VERSION_LIST = {
            "1.1.0",
            "1.1.5",
            "1.1.7"
    };

    public static String getVersion() {
        return MINECRAFT_VERSION;
    }

    public static int getMinecraftProtocol() {
        return MINECRAFT_PROTOCOL;
    }

    public static String[] getVersionList() {
        return VERSION_LIST;
    }

}
