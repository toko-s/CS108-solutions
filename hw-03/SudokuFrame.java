import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuFrame extends JFrame {

 	private static JCheckBox autoCeck;
 	private static JButton check;
 	private static JTextArea puzzle;
 	private static JTextArea solution;


	public SudokuFrame() {
		super("Sudoku Solver");
		setLocationByPlatform(true);

		autoCeck = new JCheckBox();
		autoCeck.setSelected(true);
		autoCeck.setText("Auto Check");

		check = new JButton("Check");

		puzzle = new JTextArea(15,20);
		puzzle.setBorder(new TitledBorder("Puzzle"));

		solution = new JTextArea(15,20);
		solution.setBorder(new TitledBorder("Solution"));
		solution.setEditable(false);

		Box b = Box.createHorizontalBox();
		b.add(check);
		b.add(autoCeck);


		setLayout(new BorderLayout(4,4));

		add(puzzle,BorderLayout.CENTER);
		add(solution, BorderLayout.EAST);
		add(b,BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);

		setupActionListeners();
	}

	private void setupActionListeners() {
		puzzle.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(autoCeck.isSelected())
					parsePuzzle();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if(autoCeck.isSelected())
					parsePuzzle();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if(autoCeck.isSelected())
					parsePuzzle();
			}
		});

		check.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parsePuzzle();
			}
		});
	}

	private void parsePuzzle(){
		try{
			String output = "";
			Sudoku s = new Sudoku(puzzle.getText());
			int solves = s.solve();
			if(solves != 0)
				output += s.getSolutionText();
			output += "\nSolutions: " + solves;
			System.out.println(solves);
			output += "\nTime elapsed: " + s.getElapsed();
			solution.setText(output);
		}
		catch (Exception e){
			solution.setText("Parsing problem...");
		}
	}

	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.getLookAndFeelDefaults();
		} catch (Exception ignored) { }
		
		SudokuFrame frame = new SudokuFrame();
	}

}
