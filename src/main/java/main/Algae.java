package main;

import fileio.PlantInput;

public class Algae extends Plant{
    public Algae(String name, double mass) {
        super(name, mass, "Algae");
    }
    public Algae(PlantInput input) {
        super(input);
    }
    public double getOxygen() {
        switch((getAge())) {
            case "young": return 0.7;
            case "mature": return 1.2;
            case "old": return 0.9;
            default: return 0.0;
        }
    }
}
