package simpleSnake;

import javax.swing.JFrame;
import java.awt.Dimension;

public class SimpleMain {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple RealSnake");
        frame.setContentPane(new GamePanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();

        frame.setPreferredSize(new Dimension(GamePanel.WIDTH, GamePanel.HEIGHT));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
