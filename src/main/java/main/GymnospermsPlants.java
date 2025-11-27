package main;

import fileio.PlantInput;

public class GymnospermsPlants extends Plant{
    public GymnospermsPlants(String name, double mass) {
        super(name, mass, "GymnospermsPlants");
    }
    public GymnospermsPlants(PlantInput input) {
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
        return 60.0 / 100.0;
    }
}
