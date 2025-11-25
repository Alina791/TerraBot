package main;

import fileio.AnimalInput;

public class Detritivore extends Animal {
    public Detritivore(String name, double mass) {
        super(name, mass, "Detritivores");
        setAttackProbability((100 - 90) / 10.0);
    }

    public Detritivore(AnimalInput input) {
        super(input);
    }

}
