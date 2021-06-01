import java.util.ArrayList;
import java.util.List;

public class MetropolisArray {
    List<Metropolis> data;

    public MetropolisArray(){
        data = new ArrayList<>();
    }

    public void add(Metropolis m){
        data.add(m);
    }

    public int size(){
        return data.size();
    }

    public Metropolis get(int index){
        return data.get(index);
    }
}
