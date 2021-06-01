import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class metropoliseView extends JFrame {

    private static JButton add;
    private static JButton search;
    private static JTextField metropolise;
    private static JTextField continent;
    private static JTextField population;
    private metropoliseTable table;
    private static JComboBox<String> popSearch;
    private static JComboBox<String> nameSearch;

    public metropoliseView(metropoliseTable table) throws SQLException {
        super("Metropolise Viewer");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        add = new JButton("Add");
        search = new JButton("Search");
        metropolise = new JTextField(13);
        continent = new JTextField(13);
        population = new JTextField(13);
        this.table = table;
        JTable jTable = new JTable(table);

        popSearch = new JComboBox<>();
        popSearch.addItem("Population Larger Then");
        popSearch.addItem("Population Smaller Then");
        nameSearch = new JComboBox<>();
        nameSearch.addItem("Exact match");
        nameSearch.addItem("Partial match");

        setLayout(new BorderLayout(5, 5));
        JPanel top = new JPanel();
        top.add(new JLabel("Metropolis:"));
        top.add(metropolise);
        top.add(new JLabel("Continent:"));
        top.add(continent);
        top.add(new JLabel("Population:"));
        top.add(population);

        Box left = Box.createVerticalBox();
        left.add(add);
        left.add(search);
        Box drops = Box.createVerticalBox();
        drops.add(popSearch);
        drops.add(nameSearch);
        drops.setBorder(new TitledBorder("Search Options"));
        left.add(drops);
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(left, BorderLayout.NORTH);
        Box tableBox = Box.createVerticalBox();
        tableBox.add(jTable.getTableHeader());
        JScrollPane scroll = new JScrollPane(jTable);
        tableBox.add(scroll);

        add(top, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.EAST);
        add(tableBox, BorderLayout.CENTER);


        setSize(700, 500);
        table.show();

        addListeners();
    }

    private void addListeners() {
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String metro = metropolise.getText();
                    String cont = continent.getText();
                    int pop = Integer.parseInt(population.getText());
                    if (metro.length() > 0 && cont.length() > 0)
                        table.add(metro, cont, pop);
                } catch (Exception ignored) {
                }
            }
        });
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean largerThen = popSearch.getSelectedIndex() == 0;
                boolean exactMatch = nameSearch.getSelectedIndex() == 0;
                try {
                    table.search(metropolise.getText(), continent.getText(), population.getText(), largerThen, exactMatch);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }


}
