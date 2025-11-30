package map;

import air.Desert;
import air.Mountain;
import air.Polar;
import air.Temperate;
import air.Tropical;
import animals.Carnivore;
import animals.Detritivore;
import animals.Herbivore;
import animals.Omnivore;
import animals.Parasite;
import entities.Air;
import entities.Animal;
import entities.Plant;
import entities.Soil;
import entities.Water;
import plants.Algae;
import plants.Ferns;
import plants.FloweringPlants;
import plants.GymnospermsPlants;
import plants.Mosses;
import fileio.SimulationInput;
import fileio.TerritorySectionParamsInput;
import fileio.SoilInput;
import fileio.PairInput;
import fileio.PlantInput;
import fileio.AnimalInput;
import fileio.WaterInput;
import fileio.AirInput;
import lombok.Getter;
import lombok.Setter;
import soils.DesertSoil;
import soils.ForestSoil;
import soils.GrasslandSoil;
import soils.SwampSoil;
import soils.TundraSoil;

@Setter
@Getter

public class TerraMap {
    private final int width;
    private final int height;
    private Cell[][] grid;

    private static final double PLANT_SOIL_GROWTH = 0.2;
    private static final double HUMIDITY_ADDED = 0.1;

    /** Initializes the TerraMap grid based on the dimensions provided in the simulation input
    * and populates the cells with Soil, Plants, Animals, Water, and Air objects
    * according to the section parameters.
    */
    public TerraMap(final SimulationInput sim) {
        String[] tokens = sim.getTerritoryDim().split("x");

        int nx = Integer.parseInt(tokens[0].trim());
        int ny = Integer.parseInt(tokens[1].trim());
        this.width = ny;
        this.height = nx;

        grid = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = new Cell();
            }
        }

        TerritorySectionParamsInput params = sim.getTerritorySectionParams();
        if (params == null) {
            return;
        }

        if (params.getSoil() != null) {
            for (SoilInput sIn : params.getSoil()) {
                Soil soil = switch (sIn.getType()) {
                    case "ForestSoil" -> new ForestSoil(sIn);
                    case "DesertSoil" -> new DesertSoil(sIn);
                    case "SwampSoil" -> new SwampSoil(sIn);
                    case "GrasslandSoil" -> new GrasslandSoil(sIn);
                    case "TundraSoil" -> new TundraSoil(sIn);
                    default -> null;
                };

                for (PairInput pos : sIn.getSections()) {
                    int x = pos.getX(), y = pos.getY();
                    if (grid[x][y].getSoil() == null) {
                        grid[x][y].setSoil(soil);
                    }
                }
            }
        }

        if (params.getPlants() != null) {
            for (PlantInput pIn : params.getPlants()) {
                for (PairInput pos : pIn.getSections()) {
                    int x = pos.getX();
                    int y = pos.getY();
                    Cell cell = grid[x][y];

                    if (cell.getPlant() == null) {
                        Plant newPlant = switch (pIn.getType()) {
                            case "FloweringPlants" -> new FloweringPlants(pIn);
                            case "GymnospermsPlants" -> new GymnospermsPlants(pIn);
                            case "Ferns" -> new Ferns(pIn);
                            case "Mosses" -> new Mosses(pIn);
                            case "Algae" -> new Algae(pIn);
                            default -> null;
                        };
                        if (newPlant != null) {
                            cell.setPlant(newPlant);
                        }
                    }
                }
            }
        }


        if (params.getAnimals() != null) {
            for (AnimalInput aIn : params.getAnimals()) {
                Animal animal = switch (aIn.getType()) {
                    case "Herbivores" -> new Herbivore(aIn);
                    case "Carnivores" -> new Carnivore(aIn);
                    case "Omnivores" -> new Omnivore(aIn);
                    case "Detritivores" -> new Detritivore(aIn);
                    case "Parasites" -> new Parasite(aIn);
                    default -> null;
                };

                for (PairInput pos : aIn.getSections()) {
                    int x = pos.getX(), y = pos.getY();
                    if (grid[x][y].getAnimal() == null) {
                        grid[x][y].setAnimal(animal);
                    }
                }
            }
        }


        if (params.getWater() != null) {
            for (WaterInput wIn : params.getWater()) {
                Water water = new Water(wIn);
                for (PairInput pos : wIn.getSections()) {
                    int x = pos.getX();
                    int y = pos.getY();

                    Cell cell = grid[x][y];
                    if (cell.getWater() == null) {
                        cell.setWater(water);
                    }
                }
            }
        }


        for (AirInput aIn : params.getAir()) {
            for (PairInput pos : aIn.getSections()) {
                int x = pos.getX();
                int y = pos.getY();
                if (grid[x][y].getAir() == null) {
                    grid[x][y].setAir(
                            switch (aIn.getType()) {
                                case "MountainAir" -> new Mountain(aIn);
                                case "TemperateAir" -> new Temperate(aIn);
                                case "PolarAir" -> new Polar(aIn);
                                case "TropicalAir" -> new Tropical(aIn);
                                case "DesertAir" -> new Desert(aIn);
                                default -> null;
                            }
                    );
                }
            }
        }
    }

    /**
     * Iterates through all cells on the map and applies effects resulting from previous
     * robot scanning actions.
     */
    public void applyScannedEffects() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell cell = grid[x][y];
                Plant plant = cell.getPlant();
                Air air = cell.getAir();

                if (plant != null && plant.getScanned() && !plant.isDead() && air != null) {
                    plant.grow(PLANT_SOIL_GROWTH);
                    double oxygenFromPlant = plant.getOxygen();
                    air.addOxygen(oxygenFromPlant);
                }

                Water water = cell.getWater();
                if (water != null && water.getScanned()) {
                    cell.incrementWaterCounter();
                    if (cell.getWaterEffectCounter() > 0 && cell.getWaterEffectCounter() % 2 == 0) {
                        Soil soil = cell.getSoil();
                        if (soil != null) {
                            soil.addWaterRetention(HUMIDITY_ADDED);
                        }
                        if (air != null) {
                            air.addHumidity(HUMIDITY_ADDED);
                        }
                    }
                    if (plant != null && !plant.isDead()) {
                        plant.grow(PLANT_SOIL_GROWTH);
                    }
                }
            }
        }
    }
}
