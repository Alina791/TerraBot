package main;

import fileio.AnimalInput;

public class Parasite extends Animal {
    public Parasite(String name, double mass) {
        super(name, mass, "Parasites");
        setAttackProbability((100 - 10) / 10.0);
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
}
