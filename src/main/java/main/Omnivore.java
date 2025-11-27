package main;

import fileio.AnimalInput;

public class Omnivore extends Animal {
    public Omnivore(String name, double mass) {
        super(name, mass, "Omnivores");
    }
    public Omnivore(AnimalInput input) {
        super(input);
    }
    public double calculateAttackProbability() {
        return (100.0 - 60.0) / 10.0;
    }
}
