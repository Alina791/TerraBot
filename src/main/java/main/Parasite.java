package main;

import fileio.AnimalInput;

public class Parasite extends Animal {
    public Parasite(String name, double mass) {
        super(name, mass, "Parasites");
    }

    public Parasite(AnimalInput input) {
        super(input);
    }

    public void eat(Animal prey) {
        if (prey != null) {
            double preyMass = prey.getMass();
            setMass(getMass() + preyMass);
            prey.setState("dead");
            setState("well-fed");
        }
    }
    public double calculateAttackProbability() {
        return (100.0 - 10.0) / 10.0;
    }
}
