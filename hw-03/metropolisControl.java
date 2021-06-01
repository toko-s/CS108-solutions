import javax.swing.*;
import java.sql.SQLException;

public class metropolisControl {
    public static void main(String[] args) throws SQLException {
        UIManager.getLookAndFeelDefaults();
        metropoliseDB db = new metropoliseDB();
        metropoliseTable table = new metropoliseTable(db);
        metropoliseView m = new metropoliseView(table);
    }
}
