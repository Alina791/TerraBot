package main;

import fileio.AnimalInput;
import fileio.WaterInput;

public class Water extends Entity {
    private String type;
    private double purity;
    private double salinity;
    private double turbidity;
    private double contaminantIndex;
    private double pH;
    private boolean isFrozen;

    public Water(String name, double mass, String type) {
        super(name, mass);
        this.type = type;
    }

    public Water(WaterInput input) {
        this.type = input.getType();
        setMass(input.getMass());
        setName(input.getName());
        purity = input.getPurity();
        salinity = input.getSalinity();
        turbidity = input.getTurbidity();
        contaminantIndex = input.getContaminantIndex();
        pH = input.getPH();
        isFrozen = input.isFrozen();
    }
    public String getType() {
        return type;
    }
}
