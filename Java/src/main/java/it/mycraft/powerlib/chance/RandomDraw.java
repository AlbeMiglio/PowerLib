package it.mycraft.powerlib.chance;

import java.util.*;

@Deprecated
public class RandomDraw {
    private HashMap<Object, Integer> intMap;
    private HashMap<Object, Double> doubleMap;

    public RandomDraw() {
        this.intMap = new HashMap<>();
        this.doubleMap = new HashMap<>();
    }

    /**
     * @param map             A HashMap with the drawing objects as keys and their chances as values
     * @param useDoubleValues If the map is using decimal numbers (false for integers e.g. 1.0)
     */
    public RandomDraw(HashMap<Object, Double> map, boolean useDoubleValues) {
        if (useDoubleValues) {
            this.doubleMap = map;
            this.intMap = new HashMap<>();
        } else {
            this.intMap = new HashMap<>();
            this.doubleMap = new HashMap<>();
            map.keySet().forEach((key) -> this.intMap.put(key, map.get(key).intValue()));
        }
    }

    /**
     * Adds an Item with an integer chance to be extracted
     *
     * @param obj         The object being added to the draw
     * @param probability The object's INTEGER chance of being drawn
     */
    public void addItem(Object obj, Integer probability) {
        this.intMap.put(obj, probability);
    }

    /**
     * Adds an Item with a decimal chance to be extracted
     *
     * @param obj         The object being added to the draw
     * @param probability The object's DECIMAL chance of being drawn
     */
    public void addItem(Object obj, Double probability) {
        this.doubleMap.put(obj, probability);
    }

    /**
     * Removes an Item, if present, from the extraction
     *
     * @param obj           The object being removed from the draw
     * @param isDoubleValue If the map is using decimal numbers (false for integers e.g. 1.0)
     */
    public void removeItem(Object obj, boolean isDoubleValue) {
        if (isDoubleValue) {
            this.doubleMap.remove(obj);
        } else this.intMap.remove(obj);
    }

    /**
     * Sums the chances of all the items of the extraction
     *
     * @param useDoubleValues If the map is using decimal numbers (false for integers e.g. 1.0)
     * @return The total probability of the draw
     */
    public Double getTotalChance(boolean useDoubleValues) {
        double d;
        if (useDoubleValues) {
            d = this.doubleMap.values().stream().mapToDouble(n -> n).sum();
        } else {
            d = this.intMap.values().stream().mapToInt(n -> n).sum();
        }
        return d;
    }

    /**
     * Divides the object's partial probability by the total probability to get the effective chance
     *
     * @param obj             The object whose chance we don't know about
     * @param useDoubleValues If the map is using decimal numbers (false for integers e.g. 1.0)
     * @return The related item's chance to be extracted
     */
    public Double getProbability(Object obj, boolean useDoubleValues) {
        double d;
        double total = this.getTotalChance(useDoubleValues);
        if (!contains(obj, useDoubleValues)) {
            return 0.0;
        }
        if (useDoubleValues) {
            d = this.doubleMap.get(obj) / total;
        } else {
            d = this.intMap.get(obj) / total;
        }
        return d;
    }

    /**
     * Adds the Map items to a List, shuffles it and extracts randomly an item
     *
     * @param useDoubleValues If the map is using decimal numbers (false for integers e.g. 1.0)
     * @return The random-extracted item
     */
    public Object shuffle(boolean useDoubleValues) {
        List<Object> list = new ArrayList<>();
        if (useDoubleValues) {
            int multiplier = 1;
            for (double d : this.doubleMap.values()) {
                if (getDoubleDecimals(d) > multiplier) {
                    multiplier = getDoubleDecimals(d);
                }
            }
            for (Object obj : this.doubleMap.keySet()) {
                for (int i = 1; i <= this.doubleMap.get(obj) * (Math.pow(10, multiplier * 1.0) * 1.0); i++) {
                    list.add(obj);
                }
            }
        } else {
            for (Object obj : this.intMap.keySet()) {
                for (int i = 1; i <= this.intMap.get(obj); i++) {
                    list.add(obj);
                }
            }
        }
        int totalChance = this.getTotalChance(useDoubleValues).intValue();
        int rand = random(1, totalChance);
        Collections.shuffle(list);
        return list.get(rand - 1);
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

    private boolean contains(Object obj, boolean useDoubleValues) {
        return (useDoubleValues && this.doubleMap.containsKey(obj)) || ((!useDoubleValues) && this.intMap.containsKey(obj));
    }
}

