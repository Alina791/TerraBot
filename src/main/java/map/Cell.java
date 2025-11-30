package map;

import entities.Air;
import entities.Animal;
import entities.Plant;
import entities.Soil;
import entities.Water;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Cell {
    private Soil soil;
    private Plant plant;
    private Animal animal;
    private Water water;
    private Air air;

    private int waterEffectCounter = -1;

    /** Resets the water effect counter to 0. */
    public void resetWaterCounter() {
        waterEffectCounter = 0;
    }

    /**
     * Increments the water effect counter by 1
     * if it is non-negative.
     */
    public void incrementWaterCounter() {
        if (waterEffectCounter >= 0) {
            waterEffectCounter++;
        }
    }
}
