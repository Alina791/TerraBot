package entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public abstract class Entity {
    private String name;
    private double mass;

    public Entity() {
        this.name = null;
        this.mass = 0;
    }

    public Entity(final String name, final double mass) {
        this.name = name;
        this.mass = mass;
    }
}
