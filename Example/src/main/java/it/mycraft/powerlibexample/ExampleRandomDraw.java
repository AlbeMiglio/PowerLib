package it.mycraft.powerlibexample;

import it.mycraft.powerlib.PowerLib;
import it.mycraft.powerlib.chance.RandomDraw;

public class ExampleRandomDraw {

    private PowerLib main = PowerLib.getInstance();
    private RandomDraw r;

    public void init() {
        r = new RandomDraw();
    }

    public String randomBallExtraction() {
        r.addItem("green", 70);
        r.addItem("white", 5);
        r.addItem("red", 25);
        String rand = (String) r.shuffle(false); // outputs We don't know! Try it urself ;)
        return rand;
    }

    public Double getRedBallProbability() {
        return r.getProbability("red", false);   // outputs (25.0 / 100.0)  = 0.25
    }

    public Double getBlackBallProbability() {
        return r.getProbability("black", false); // outputs 0.0! There aren't any black balls in the draw!
    }

    public Double getTotalChance() {
        return r.getTotalChance(false);          // outputs (70.0 + 5.0 + 25.0) = 100.0
    }


}
