package main;

import fileio.PlantInput;

public class Mosses extends Plant{
    public Mosses(String name, double mass) {
        super(name, mass, "Mosses");
    }
    public Mosses(PlantInput input) {
        super(input);
    }
    public double getOxygen() {
        switch((getAge())) {
            case "young": return 1.0;
            case "mature": return 1.5;
            case "old": return 1.2;
            default: return 0.0;
        }
    }
    public double calculatePlantPossibility() {
        return 40.0 / 100.0;
    }
}
