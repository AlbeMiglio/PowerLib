package it.mycraft.powerlib.common.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ColorAPI {

    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + '§' + "[0-9A-FK-OR]");

    /**
     * Colors a String
     *
     * @param s The string to color
     * @return  The colored string
     */
    public static String color(String s) {
        return s.replace("&", "§");
    }

    public static Component color(TextComponent c) {
        return Component.text(color(c.content()));
    }

    /**
     * Colors a String with RGB Hex colors (1.16+ clients and servers only)
     * Usage: pre + hex code + post
     *
     * @author  Elementeral
     * @param string The string to color
     * @return  The colored string
     */
    public static String hex(@Nullable String string, String pre, String post) {
        if(string == null || string.isEmpty()) return string;
        final Pattern hexPattern = Pattern.compile(pre+"([A-Fa-f0-9]{6})"+post);
        Matcher matcher = hexPattern.matcher(string);
        StringBuilder buffer = new StringBuilder(string.length() + 4 * 8);
        while (matcher.find()) {
            String group = matcher.group(1);
            char COLOR_CHAR = '§';
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        return matcher.appendTail(buffer).toString();
    }

    public static Component hex(TextComponent component, String pre, String post) {
        return Component.text(hex(component.content(), pre, post));
    }

    /**
     * Colors a String with RGB Hex colors and default hex syntax
     * Usage: &# + hex code
     *
     * @param string The string to color
     * @return  The colored string
     */
    public static String hex(String string) {
        return hex(string, "&#", "");
    }

    public static Component hex(TextComponent component) {
        return hex(component, "&#", "");
    }

    /**
     * Colors a StringList with RGB Hex colors (1.16+ clients and servers only)
     *
     * @param l The list to color
     * @return  The colored stringlist
     */
    public static List<String> hex(List<String> l, String pre, String post) {
        return l.stream().map(line -> hex(line, pre, post)).collect(Collectors.toList());
    }

    public static List<String> hex(List<String> l) {
        return hex(l, "&#", "");
    }

    /**
     * Decolors a String
     *
     * @param s The string to decolor
     * @return The unformatted string
     */
    public static String decolor(String s) {
        return s == null ? null :
                STRIP_COLOR_PATTERN.matcher(s).replaceAll("");
    }

    public static Component decolor(TextComponent c) {
        return Component.text(decolor(c.content()));
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
