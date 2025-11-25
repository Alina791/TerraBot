package main;

import fileio.PlantInput;

public class FloweringPlants extends Plant{
    public FloweringPlants(String name, double mass) {
        super(name, mass, "FloweringPlants");
    }
    public FloweringPlants(PlantInput input) {
        super(input);
    }
    public double getOxygen() {
        switch((getAge())) {
            case "young": return 6.2;
            case "mature": return 6.7;
            case "old": return 6.4;
            default: return 0.0;
        }
    }
}
