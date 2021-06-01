public class Filter {
    private String metropolis;
    private String continent;
    private String population;
    private boolean largerThen;
    private boolean exact;


    public Filter(String metropolis, String continent, String population, boolean largerThen, boolean exact){
        this.metropolis = metropolis;
        this.continent = continent;
        this.population = population;
        this.largerThen =largerThen;
        this.exact = exact;
    }

    public String getMetropolis(){
        return metropolis;
    }

    public String getContinent() {
        return continent;
    }

    public int getPopulation() {
        try {
            return Integer.parseInt(population);
        }catch (Exception ignored){
            return 0;
        }
    }

    public String metropolisFilter(){
        if(metropolis.length() == 0){
            metropolis = "%";
            return " like ";
        }
        if(exact)
            return " = ";
        metropolis += "%";
        return " like ";
    }

    public String continentFilter(){
        if(continent.length() == 0){
            continent = "%";
            return " like ";
        }
        if(exact)
            return " = ";
        continent += "%";
        return " like ";
    }

    public String populationFilter(){
        if(largerThen)
            return " >= ";
        return " <= ";
    }
}
