import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;

public class metropoliseTable extends AbstractTableModel {
    private MetropolisArray data;
    private metropoliseDB db;

    public metropoliseTable(metropoliseDB db) {
        super();
        this.db = db;
        data = new MetropolisArray();
    }

    private void setData(MetropolisArray data) {
        this.data = data;
        fireTableDataChanged();
    }

    public void show() throws SQLException {
        setData(db.getAll());
    }

    public void add(String metropolise, String continent, int population) throws SQLException {
        db.add(new Metropolis(metropolise, continent, population));
        show();
    }

    public void search(String metropolis, String continent, String population, boolean populationLargerThen, boolean exactMatch) throws SQLException {

//        String query = "SELECT * FROM metropolises" +
//                "WHERE metropolis " + Filter.createMtropolisFilter(metropolis,) + "&& continent " + Filter.createPopulationFilter(population)
//                + "&& population " + Filter.createPopulationFilter(population);
        Filter filter = new Filter(metropolis,continent,population,populationLargerThen,exactMatch);
        setData(db.filter(filter));

    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Metropolis";
            case 1 -> "Continent";
            case 2 -> "Population";
            default -> null;
        };
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Metropolis curr = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return curr.getMetropolis();
            case 1:
                return curr.getContinent();
            case 2:
                return curr.getPopulation();
        }
        return null;
    }
}
