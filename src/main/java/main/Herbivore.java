package main;

import fileio.AnimalInput;

public class Herbivore extends Animal {
    public Herbivore(String name, double mass) {
        super(name, mass, "Herbivores");
    }

    public Herbivore(AnimalInput input) {
        super(input);
    }
    public double calculateAttackProbability() {
        return (100.0 - 85.0) / 10.0;
    }
}
