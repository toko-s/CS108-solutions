
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.List;

public class metropoliseDB {
    private static final String username = "";
    private static final String password = "";
    private static final String databaseName = "";

    private static Connection conn;

    public metropoliseDB() throws SQLException {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:mysql://localhost:3306/" + databaseName);
        ds.setUsername(username);
        ds.setPassword(password);
        conn = ds.getConnection();
    }

    public MetropolisArray getAll() throws SQLException {
        Statement st = conn.createStatement();
        ResultSet set = st.executeQuery("select * from metropolises;");
        MetropolisArray res = new MetropolisArray();
        while (set.next())
            res.add(new Metropolis(set.getString(1), set.getString(2), set.getInt(3)));

        return res;
    }

    public void add(Metropolis m) throws SQLException {
        PreparedStatement st = conn.prepareStatement("INSERT INTO metropolises VALUES (?,?,?)");
        st.setString(1, m.getMetropolis());
        st.setString(2, m.getContinent());
        st.setInt(3, m.getPopulation());
        st.execute();
    }

    public MetropolisArray filter(Filter filter) throws SQLException {
        MetropolisArray res = new MetropolisArray();
        String query = "SELECT * FROM metropolises " +
                "WHERE metropolis " + filter.metropolisFilter() + " ? && continent " + filter.continentFilter()
                + " ? && population " + filter.populationFilter() + " ? " + " ;";
        PreparedStatement st = conn.prepareStatement(query);
        st.setString(1, filter.getMetropolis());
        st.setString(2, filter.getContinent());
        st.setInt(3,filter.getPopulation());
        ResultSet set = st.executeQuery();
        while (set.next())
            res.add(new Metropolis(set.getString(1), set.getString(2), set.getInt(3)));
        return res;
    }
}
