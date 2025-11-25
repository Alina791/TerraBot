package main;

import fileio.AnimalInput;

public class Omnivore extends Animal {
    public Omnivore(String name, double mass) {
        super(name, mass, "Omnivores");
        setAttackProbability((100 - 60) / 10.0);
    }
    public Omnivore(AnimalInput input) {
        super(input);
    }
}
