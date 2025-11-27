package main;

import fileio.PlantInput;

public class Ferns extends Plant{
    public Ferns(String name, double mass) {
        super(name, mass, "Ferns");
    }
    public Ferns(PlantInput input) {
        super(input);
    }
    public double getOxygen() {
        switch((getAge())) {
            case "young": return 0.2;
            case "mature": return 0.7;
            case "old": return 0.4;
            default: return 0.0;
        }
    }
    public double calculatePlantPossibility() {
        return 30.0 / 100.0;
    }
}
