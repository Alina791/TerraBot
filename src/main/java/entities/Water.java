package entities;

import fileio.WaterInput;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Water extends Entity {
    private String type;
    private double purity;
    private double salinity;
    private double turbidity;
    private double contaminantIndex;
    private double pH;
    private boolean isFrozen;

    public Water(final String name, final double mass, final String type) {
        super(name, mass);
        this.type = type;
    }

    public Water(final WaterInput input) {
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

    /** Checks whether the water was scanned. */
    public boolean getScanned() {
        return scanned;
    }
    private boolean scanned = false;
}
