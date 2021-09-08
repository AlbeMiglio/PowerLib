package it.mycraft.powerlib.common.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ColorAPI {

    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + '§' + "[0-9A-FK-OR]");
    private static char COLOR_CHAR = '§';

    /**
     * Colors a String
     *
     * @param s The string to color
     * @return  The colored string
     */
    public static String color(String s) {
        return s.replace("&", "§");
    }

    /**
     * Colors a String with RGB Hex colors (1.16+ clients and servers only)
     * Usage: &# + hex code
     *
     * @author  Elementeral
     * @param s The string to color
     * @return  The colored string
     */
    public String hex(String s) {
        final Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(s);
        StringBuffer buffer = new StringBuffer(s.length() + 4 * 8);
        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        return matcher.appendTail(buffer).toString();
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
