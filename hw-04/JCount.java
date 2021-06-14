// JCount.java

/*
 Basic GUI/Threading exercise.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JCount extends JPanel {

	private Thread t;

	public JCount() {
		// Set the JCount to use Box layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JTextField field = new JTextField(15);
		JLabel label = new JLabel("0");
		JButton start = new JButton("Start");
		JButton stop = new JButton("Stop");
		int count = 0;
		this.add(field);
		this.add(label);
		this.add(start);
		this.add(stop);
		this.add(Box.createRigidArea(new Dimension(0,40)));
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(t!=null && t.isAlive())
					t.interrupt();
				t = new WorkerThread(label,Integer.parseInt(field.getText()));
				t.start();
			}
		});
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				t.interrupt();
			}
		});
	}

	
	static public void main(String[] args)  {
		// Creates a frame with 4 JCounts in it.
		// (provided)
		JFrame frame = new JFrame("The Count");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

