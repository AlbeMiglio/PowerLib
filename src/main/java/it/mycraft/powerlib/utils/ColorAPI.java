package it.mycraft.powerlib.utils;

import org.bukkit.ChatColor;
import java.util.ArrayList;
import java.util.List;

public class ColorAPI {

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> color(List<String> l) {
        List<String> list = new ArrayList<>();
        l.forEach((line -> list.add(color(line))));
        return list;
    }
}
