package main;

import fileio.AnimalInput;

public class Carnivore extends Animal {
    public Carnivore(String name, double mass) {
        super(name, mass, "Carnivores");
    }
    public Carnivore(AnimalInput input) {
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
        return (100.0 - 30.0) / 10.0;
    }
}
