package main;

import java.util.ArrayList;
import java.util.List;

public class Section {
    public List<Soil> soils = new ArrayList<>();
    public List<Air> air = new ArrayList<>();
    public List<Water> waters = new ArrayList<>();
    public List<Plant> plants = new ArrayList<>();
    public List<Animal> animals = new ArrayList<>();

    public int getTotalObjects() {
        return soils.size() + air.size() + waters.size()
                + plants.size() + animals.size();
    }
}
