package main;

import fileio.AnimalInput;

public class Carnivore extends Animal {
    public Carnivore(String name, double mass) {
        super(name, mass, "Carnivores");
        setAttackProbability((100 - 30) / 10.0);
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
}
