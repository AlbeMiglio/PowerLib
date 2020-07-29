package it.mycraft.powerlib.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ColorAPI {

    /**
     * Colors a String
     * @param s The string to color
     * @return The colored string
     */
    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    /**
     * Decolors a String
     * @param s The string to decolor
     * @return The unformatted string
     */
    public static String decolor(String s) {
        return ChatColor.stripColor(ChatColor.RESET + s);
    }

    /**
     * Colors a List<String>
     * @param l The list of strings to color
     * @return The colored list
     */
    public static List<String> color(List<String> l) {
        List<String> list = new ArrayList<>();
        l.forEach((line -> list.add(color(line))));
        return list;
    }

    /**
     * Decolors a List<String>
     * @param l The list of strings to decolor
     * @return The unformatted list
     */
    public static List<String> decolor(List<String> l) {
        List<String> list = new ArrayList<>();
        l.forEach((line -> list.add(decolor(line))));
        return list;
    }
}
