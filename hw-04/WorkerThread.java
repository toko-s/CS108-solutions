import javax.swing.*;

public class WorkerThread extends Thread {
    private int count;
    private JLabel label;
    private int currCount;


    public WorkerThread(JLabel label, int count) {
        this.label = label;
        this.count = count;
        currCount = 0;
        if (count < 0 || count > 100000000)
            throw new IllegalArgumentException("total count should be more than 0 and less then 100000000");
    }

    @Override
    public void run() {
        System.out.println(count / 10000);
        for (currCount = 0; currCount <= count; currCount++) {
            if(isInterrupted())
                return;
            if (currCount % 10000 == 0) {
                SwingUtilities.invokeLater(() -> {
                    label.setText(String.valueOf(currCount));
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }
}
