

import java.util.List;

public class Metropolis {
    private final String metropolis;
    private final String continent;
    private final int population;

    public Metropolis(String metropolis, String continent, int population) {
        this.metropolis = metropolis;
        this.continent = continent;
        this.population = population;
    }

    public String getMetropolis() {
        return metropolis;
    }

    public int getPopulation() {
        return population;
    }

    public String getContinent() {
        return continent;
    }

}
