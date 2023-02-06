package com.candy.utils;

public class SweetUtils extends VersionUtils {

    public static final String SOFTWARE_NAME = "SweetProxy";

    public static final String SOFTWARE_VERSION = "1.0+alpha";

    public static final String DATA_PATH = System.getProperty("user.dir") + "/";

    public static String getSoftwareName() {
        return SOFTWARE_NAME;
    }

    public static String getSoftwareVersion() {
        return SOFTWARE_VERSION;
    }

    public String getDataPath() {
        return DATA_PATH;
    }
}
