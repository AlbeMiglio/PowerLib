package it.mycraft.powerlib.common.utils;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ColorAPI {

    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + '§' + "[0-9A-FK-OR]");

    /**
     * Colors a String
     *
     * @param s The string to color
     * @return The colored string
     */
    public static String color(String s) {
        return s.replace("&", "§");
    }

    /**
     * Decolors a String
     *
     * @param s The string to decolor
     * @return The unformatted string
     */
    public static String decolor(String s) {
        return s == null ? null : STRIP_COLOR_PATTERN.matcher(s).replaceAll("");
    }

    /**
     * Colors a List<String>
     *
     * @param list The list of strings to color
     * @return The colored list
     */
    public static List<String> color(List<String> list) {
        return list.stream().map(ColorAPI::color).collect(Collectors.toList());
    }

    /**
     * Decolors a List<String>
     *
     * @param list The list of strings to decolor
     * @return The unformatted list
     */
    public static List<String> decolor(List<String> list) {
        return list.stream().map(ColorAPI::decolor).collect(Collectors.toList());
    }
}
