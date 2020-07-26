package it.mycraft.powerlib.chance;

import it.mycraft.powerlib.PowerLib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RandomDraw {

    private static PowerLib main = PowerLib.getInstance();
    private HashMap<Object, Integer> intMap;
    private HashMap<Object, Double> doubleMap;

    public RandomDraw() {
        this.intMap = new HashMap<>();
        this.doubleMap = new HashMap<>();
    }

    public RandomDraw(HashMap<Object, Double> map, boolean useDoubleValues) {
        if(useDoubleValues) {
            this.doubleMap = map;
            this.intMap = new HashMap<>();
        }
        else {
            this.intMap = new HashMap<>();
            this.doubleMap = new HashMap<>();
            map.keySet().forEach((key) -> this.intMap.put(key, map.get(key).intValue()));
        }
    }

    public void addItem(Object obj, Integer probability) {
        this.intMap.put(obj, probability);
    }

    public void addItem(Object obj, Double probability) {
        this.doubleMap.put(obj, probability);
    }

    public void removeItem(Object obj, boolean isDoubleValue) {
        if(isDoubleValue) {
            this.doubleMap.remove(obj);
        }
        else this.intMap.remove(obj);
    }

    public Double getTotalChance(boolean useDoubleValues) {
        double d;
        if(useDoubleValues) {
            d = this.doubleMap.values().stream().mapToDouble(n -> n).sum();
        }
        else {
            d = this.intMap.values().stream().mapToInt(n -> n).sum();
        }
        return d;
    }

    public Double getProbability(Object obj, boolean useDoubleValues) {
        double d;
        double total = this.getTotalChance(useDoubleValues);
        if(useDoubleValues) {
            d = this.doubleMap.get(obj) / total;
        }
        else {
            d = this.intMap.get(obj) / total;
        }
        return d;
    }

    public Object shuffle(boolean useDoubleValues) {
        List<Object> list = new ArrayList<>();
        if(useDoubleValues) {
            int multiplier = 1;
            for(double d : this.doubleMap.values()) {
                if(getDoubleDecimals(d) > multiplier) {
                    multiplier = getDoubleDecimals(d);
                }
            }
            for(Object obj : this.doubleMap.keySet()) {
                for(int i = 1; i <= this.doubleMap.get(obj)*(Math.pow(10, multiplier * 1.0)*1.0); i++) {
                    list.add(obj);
                }
            }
        }
        else {
            for(Object obj : this.intMap.keySet()) {
                for(int i = 1; i <= this.intMap.get(obj); i++) {
                    list.add(obj);
                }
            }
        }
        int rand = random(1, this.getTotalChance(useDoubleValues).intValue());
        return list.indexOf(rand-1);
    }

    private int getDoubleDecimals(double d) {
        String text = Double.toString(Math.abs(d));
        int integerPlaces = text.indexOf('.');
        return text.length() - integerPlaces - 1;
    }

    private int random(int min, int max) {
        Random r = new Random();
        return r.nextInt(max + 1 - min) + min;
    }
}
