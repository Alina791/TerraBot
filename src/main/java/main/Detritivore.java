package main;

import fileio.AnimalInput;

public class Detritivore extends Animal {
    public Detritivore(String name, double mass) {
        super(name, mass, "Detritivores");
    }

    public Detritivore(AnimalInput input) {
        super(input);
    }
    public double calculateAttackProbability() {
        return (100.0 - 90.0) / 10.0;
    }
}
