package it.mycraft.powerlib.utils;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class ColorAPI {

    /**
     * Colors a String
     *
     * @param s The string to color
     * @return The colored string
     */
    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    /**
     * Decolors a String
     *
     * @param s The string to decolor
     * @return The unformatted string
     */
    public static String decolor(String s) {
        return ChatColor.stripColor(ChatColor.RESET + s);
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
