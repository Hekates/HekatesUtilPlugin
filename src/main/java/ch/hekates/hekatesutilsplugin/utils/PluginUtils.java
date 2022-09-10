package ch.hekates.hekatesutilsplugin.utils;

public class PluginUtils {
    public static String timeConvert(int time) {
        return time / 24 / 60 + "d, " + time / 60 % 24 + "h, " + time % 60 + "min";
    }
}
