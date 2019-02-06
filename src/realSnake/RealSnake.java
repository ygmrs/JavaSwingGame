package realSnake;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class RealSnake extends JFrame {

    public RealSnake() {

        initUI();
    }

    private void initUI() {

        add(new Board());

        setResizable(false);
        pack();

        setTitle("RealSnake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame ex = new RealSnake();
            ex.setVisible(true);
        });
    }
}