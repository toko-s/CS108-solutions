import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WebFrame extends JFrame {
    private static final String links = "links.txt";
    private static int runningThreads;
    private static int completedThreads;
    private static int maxThreads;
    private static double prevTime;
    private static Semaphore threadSem;
    private static Semaphore launcherSem;
    private static List<Thread> threads;
    private JButton stop;
    private JButton single;
    private JButton concurrent;
    private JTextField field;
    private JLabel running;
    private JLabel completed;
    private JLabel elapsed;
    private DefaultTableModel model;
    private JProgressBar bar;

    public WebFrame() {
        super("WebLoader");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        model = new DefaultTableModel(new String[]{"url", "status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setPreferredSize(new Dimension(600, 300));
        stop = new JButton("stop");
        stop.setEnabled(false);
        single = new JButton("Single Thread Fetch");
        concurrent = new JButton("Concurrent Fetch");
        field = new JTextField();
        field.setMaximumSize(new Dimension(40, 5));
        field.setText("4");
        running = new JLabel("Running: 0");
        completed = new JLabel("Completed: 0");
        elapsed = new JLabel("Elapsed: ");
        bar = new JProgressBar();
        panel.add(scrollpane);
        panel.add(single);
        panel.add(concurrent);
        panel.add(field);
        panel.add(running);
        panel.add(completed);
        panel.add(elapsed);
        panel.add(bar);
        panel.add(stop);
        add(panel);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        fillTable();
        addListeners();
        threads = new ArrayList<>();
        runningThreads =0;
        completedThreads = 0;
    }


    private void fillTable() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(links));
            String line = reader.readLine();
            while (line != null) {
                model.addRow(new String[]{line, ""});
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addListeners() {
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Thread t : threads)
                    t.interrupt();
                threads.clear();
                updateFrame();
            }
        });

        single.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                maxThreads = 1;
                try {
                    fetch();
                } catch (InterruptedException ignored) {}
            }
        });

        concurrent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    maxThreads = Integer.parseInt(field.getText());
                } catch (Exception ex) {
                    maxThreads = 1;
                }
                try {
                    fetch();
                } catch (InterruptedException ignored) {
                }
            }
        });
    }

    private void fetch() throws InterruptedException {
        runningThreads = 1;
        completedThreads = 0;
        updateFrame();
        cleanTable();
        prevTime = System.currentTimeMillis();
        threadSem = new Semaphore(maxThreads);
        concurrent.setEnabled(false);
        single.setEnabled(false);
        stop.setEnabled(true);
        bar.setMaximum(model.getRowCount());
        bar.setValue(0);
        Thread t = new Launcher(this);
        threads.add(t);
        t.start();
    }

    private void cleanTable() {
        for(int i =0 ; i < model.getRowCount(); i++){
            updateTable(i,"");
        }
    }

    private class Launcher extends Thread {
        WebFrame frame;
        public Launcher(WebFrame frame) {
            launcherSem = new Semaphore(0);
            this.frame = frame;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < model.getRowCount(); i++) {
                    threadSem.acquire();
                    runningThreads++;
                    Thread t = new WebWorker((String) model.getValueAt(i, 0),frame,i);
                    threads.add(t);
                    t.start();
                }
                for (int i = 0; i < model.getRowCount(); i++) {
                    launcherSem.acquire();
                }
            } catch (InterruptedException ignored) {
            }
            finally {
                runningThreads--;
                stop.setEnabled(false);
                single.setEnabled(true);
                concurrent.setEnabled(true);
                updateFrame();
            }
        }
    }

    public void updateFrame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                bar.setValue(completedThreads);
                elapsed.setText("Elapsed: "+ (System.currentTimeMillis() - prevTime)/1000);
                running.setText("Running: " + runningThreads);
                completed.setText("Completed: " + completedThreads);
            }
        });
    }

    public void updateTable(int index, String status){
        model.setValueAt(status,index,1);
    }

    public synchronized void unlockThread(){
        completedThreads++;
        threadSem.release();
        runningThreads--;
        launcherSem.release();
        updateFrame();
    }

    public static void main(String[] args) {
        new WebFrame();
    }
}
