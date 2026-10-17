
# TerraBot Game

An object-oriented simulation engine that models interactions between an autonomous robot and various ecological entities across a dynamic environment. The system utilizes object-oriented principles such as abstraction, inheritance, and polymorphism to represent environmental factors, flora, fauna, and map dynamics.

---

## Core Architecture & Domain Model

The system establishes a polymorphic interface through a base `Entity` class and typed inputs, dividing environmental factors into specialized sub-domains:

*   **Air Domain**: Represents atmospheric conditions. Classes compute air quality and toxicity based on input data. They implement methods such as `addOxygen`, `addHumidity`, and weather condition triggers that adjust oxygen, humidity, and toxicity levels dynamically during interactions (e.g., plant scanning).
*   **Soil Domain**: Represents ground conditions. Computes soil quality metrics based on specific soil properties and updates characteristics upon environmental shifts (e.g., moisture absorption).
*   **Flora (Plants)**: Tracks plant growth and oxygen generation. Plants calculate oxygen output based on age, update growth stages dynamically using environmental oxygen levels, and compute attack/obstacle probabilities against the robot.
*   **Fauna (Animals)**: Categorizes animals based on diet types to calculate attack probabilities and path obstruction risks for the navigating robot.
*   **Water**: Tracks scanned states and directly influences surrounding soil moisture and air humidity.

---

## Environment & Map Mechanics (`TerraMap`)

*   **Grid System (`TerraMap` & `Cell`)**: Represents the environment as a grid of coordinates. Each cell holds instances of active entities (Air, Soil, Water, Plants, Animals).
*   **Dynamic Weather System (`Weather`)**: Applies temporal environmental modifications. External weather changes adjust air quality for a fixed duration of two seconds before restoring initial baseline values.
*   **Environmental Feedback Loops**: Scanning entities triggers immediate environmental updates. Plants release oxygen to the air, while water sources modify surrounding air humidity and soil moisture.

---

## Simulation & Navigation Engine

The simulation lifecycle is managed through three primary components:

### 1. `Robot` (Autonomous Navigation)
*   **Pathfinding Logic (`moveRobot`)**: Evaluates neighboring cells to determine the lowest movement cost. Cost is computed as the arithmetic mean of risk factors within a target cell (e.g., air toxicity, soil movement risk, fauna attack probability).
*   **Tie-Breaking**: Analyzes cells in a predefined directional order to resolve equal-cost scenarios.
*   **Composition**: Delegates environment interactions to an internal `Scanner` instance, maintaining a clear separation of concerns between navigation and environment scanning.
*   **Power Management**: Includes a `recharge` mechanism. While actively charging, the robot is locked out of performing other actions.

### 2. `Scanner`
*   Identifies target objects within the robot's current cell based on command parameters.
*   Executes scanning procedures and propagates environmental modifications back to the map.

### 3. `Simulation` & `SimulationManager`
*   **`Simulation`**: Processes sequential input commands, state transitions, cell status inspections, and simplified map displays. Handles validation errors (e.g., commands issued before initialization or while the robot is charging) and collects execution logs.
*   **`SimulationManager`**: Coordinates multiple consecutive simulation runs. Isolates global orchestrations from individual simulation instances and compiles all log outputs into a structured JSON array.
