package solidSnake;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Special Snake");
        jFrame.setContentPane(new Snake());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.pack();
        jFrame.setPreferredSize(new Dimension(Snake.WIDTH, Snake.HEIGHT));
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
}
