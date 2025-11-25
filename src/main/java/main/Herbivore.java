package main;

import fileio.AnimalInput;

public class Herbivore extends Animal {
    public Herbivore(String name, double mass) {
        super(name, mass, "Herbivores");
        setAttackProbability((100 - 85) / 10.0);
    }

    public Herbivore(AnimalInput input) {
        super(input);
    }

}
