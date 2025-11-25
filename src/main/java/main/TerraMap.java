package main;

import fileio.*;

public class TerraMap {
    private final int width;
    private final int height;
    private final Cell[][] grid;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public TerraMap(SimulationInput sim) {
        String[] tokens = sim.getTerritoryDim().split("x");

        int nx = Integer.parseInt(tokens[0].trim());
        int ny = Integer.parseInt(tokens[1].trim());
        this.width = nx;
        this.height = ny;

        // create grid
        grid = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = new Cell();
            }
        }

        TerritorySectionParamsInput params = sim.getTerritorySectionParams();
        if (params == null) return;

        // ---- SOILS ----
        if (params.getSoil() != null) {
            for (SoilInput sIn : params.getSoil()) {

                Soil soil = null;

                switch (sIn.getType()) {
                    case "ForestSoil":
                        soil = new ForestSoil(sIn);
                        break;
                    case "DesertSoil":
                        soil = new DesertSoil(sIn);
                        break;
                    case "SwampSoil":
                        soil = new SwampSoil(sIn);
                        break;
                    case "GrasslandSoil":
                        soil = new GrasslandSoil(sIn);
                        break;
                    case "TundraSoil":
                        soil = new TundraSoil(sIn);
                        break;
                    default:
                        System.err.println("Unknown soil type: " + sIn.getType());
                        continue;
                }

                for (PairInput pos : sIn.getSections()) {
                    int x = pos.getX(), y = pos.getY();
                    if (grid[x][y].getSoil() == null)
                        grid[x][y].setSoil(soil);
                }
            }
        }

        // ---- PLANTS ----
        if (params.getPlants() != null) {
            for (PlantInput pIn : params.getPlants()) {

                Plant plant = null;

                // Alegi tipul în funcție de pIn.getType()
                switch (pIn.getType()) {
                    case "FloweringPlants":
                        plant = new FloweringPlants(pIn);
                        break;

                    case "GymnospermsPlants":
                        plant = new GymnospermsPlants(pIn);
                        break;

                    case "Ferns":
                        plant = new Ferns(pIn);
                        break;

                    case "Mosses":
                        plant = new Mosses(pIn);
                        break;

                    case "Algae":
                        plant = new Algae(pIn);
                        break;

                    default:
                        System.err.println("Unknown plant type: " + pIn.getType());
                        continue; // nu punem planta dacă tipul e invalid
                }

                // Parcurgem toate coordonatele la care trebuie plasată planta
                for (PairInput pos : pIn.getSections()) {
                    int x = pos.getX();
                    int y = pos.getY();


                    Cell cell = grid[x][y];

                    if (cell.getPlant() == null) {
                        cell.setPlant(plant);
                    }
                }
            }
        }


        if (params.getAnimals() != null) {
            for (AnimalInput aIn : params.getAnimals()) {

                Animal animal = null;

                switch (aIn.getType()) {
                    case "Herbivores":
                        animal = new Herbivore(aIn);
                        break;
                    case "Carnivores":
                        animal = new Carnivore(aIn);
                        break;
                    case "Omnivores":
                        animal = new Omnivore(aIn);
                        break;
                    case "Detritivores":
                        animal = new Detritivore(aIn);
                        break;
                    case "Parasites":
                        animal = new Parasite(aIn);
                        break;
                    default:
                        System.err.println("Unknown animal type: " + aIn.getType());
                        continue;
                }

                for (PairInput pos : aIn.getSections()) {
                    int x = pos.getX(), y = pos.getY();
                    if (grid[x][y].getAnimal() == null)
                        grid[x][y].setAnimal(animal);
                }
            }
        }


        if (params.getWater() != null) {
            for (WaterInput wIn : params.getWater()) {
                // creezi un obiect Water direct din input
                Water water = new Water(wIn);

                // parcurgi toate coordonatele
                for (PairInput pos : wIn.getSections()) {
                    int x = pos.getX();
                    int y = pos.getY();

                    Cell cell = grid[x][y];
                    // adaugi doar dacă nu există deja apă
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

                Air cellAir = switch (aIn.getType()) {
                    case "MountainAir" -> new Mountain(aIn);
                    case "TemperateAir" -> new Temperate(aIn);
                    case "PolarAir" -> new Polar(aIn);
                    case "TropicalAir" -> new Tropical(aIn);
                    case "DesertAir" -> new Desert(aIn);
                    default -> {
                        System.err.println("Unknown air type: " + aIn.getType());
                        yield null;
                    }
                };

                grid[x][y].setAir(cellAir);  // fără if
            }
        }



    }
}
