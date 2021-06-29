package manager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class ProductManager {

    Collection<Item> data;
    private static ProductManager instance;

    public static ProductManager getInstance(){
        if(instance == null)
            synchronized (ProductManager.class) {
                if (instance == null)
                    instance = new ProductManager();
            }
        return instance;
    }

    private ProductManager(){
        data = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/hw05?user=root&password=2412");
            fillData(conn);
            conn.close();
        } catch (Exception ignored) {}
    }

    private void fillData(Connection conn) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT * FROM products");
        ResultSet rs = st.executeQuery();
        while(rs.next()){
            data.add(new Item(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
        }
    }

    public Collection<Item> getData(){
        return data;
    }

    public Item getByID(String id){
        for(Item i : data){
            if(i.id.equals(id))
                return i;
        }
        return null;
    }

}
