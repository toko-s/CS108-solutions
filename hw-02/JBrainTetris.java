import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class JBrainTetris extends JTetris{

    private Brain brain;
    private JCheckBox brainMode;
    private Brain.Move move;
    private JCheckBox falling;
    private int count;
    private JPanel little;
    private JSlider adversary;
    private JLabel status;

    public JBrainTetris(int pixels){
        super(pixels);
        brain = new DefaultBrain();
        move = new Brain.Move();
        count = 0;
    }


    @Override
    public JComponent createControlPanel() {
        JComponent panel = super.createControlPanel();
        panel.add(new JLabel("Brain: "));
        brainMode = new JCheckBox("Brain active");
        falling = new JCheckBox("Aimate falling");
        falling.setSelected(true);
        little = new JPanel();
        little.add(new JLabel("Adversary:"));
        adversary = new JSlider(0, 100, 0);
        adversary.setPreferredSize(new Dimension(100,15));
        status = new JLabel("ok");
        little.add(adversary);
        panel.add(brainMode);
        panel.add(falling);
        panel.add(little);
        panel.add(status);
        panel.add(Box.createVerticalStrut(20));
        return panel;
    }

    @Override
    public void tick(int verb) {
        if(brainMode.isSelected()) {
            if (verb == DOWN) {
                if(count != super.count) {
                    count = super.count;
                    board.undo();
                    brain.bestMove(board, currentPiece, board.getHeight() - TOP_SPACE, move);
                }
                if(move != null) {
                    if (!currentPiece.equals(move.piece))
                        tick(ROTATE);
                    if (currentX > move.x)
                        tick(LEFT);
                    else if (currentX < move.x)
                        tick(RIGHT);
                    else if(!falling.isSelected() && currentY > move.y) {
                        tick(DROP);
                        board.commit();
                    }
                }
            }
        }
        super.tick(verb);
    }

    @Override
    public Piece pickNextPiece() {
        if(random.nextInt(98) < adversary.getValue() - 1){
            status.setText("*ok*");
            Piece[] allPieces = Piece.getPieces();
            double worstScore = 0;
            int worstIndex = -1;
            int limit = board.getHeight() - TOP_SPACE;
            for(int i = 0; i < allPieces.length; i++){
                brain.bestMove(board,allPieces[i],limit,move);
                if(move.score > worstScore) {
                    worstScore = move.score;
                    worstIndex = i;
                }
            }
            return allPieces[worstIndex];
        }
        status.setText("ok");
        return super.pickNextPiece();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        JBrainTetris tetris = new JBrainTetris(16);
        JFrame frame = JBrainTetris.createFrame(tetris);
        frame.setVisible(true);
    }
}
