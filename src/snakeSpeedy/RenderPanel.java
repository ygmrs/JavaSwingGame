package snakeSpeedy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RenderPanel extends JPanel {

    public static Color GREEN = new Color(1666073);

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        SnakeSpeedy snakeSpeedy = SnakeSpeedy.snakeSpeedy;
        g.setColor(GREEN);
        g.fillRect(0, 0, 800, 700);
        g.setColor(Color.YELLOW);

        for (Point point: snakeSpeedy.snakeParts) {
            g.fillRect(point.x * SnakeSpeedy.SCALE, point.y * SnakeSpeedy.SCALE, SnakeSpeedy.SCALE, SnakeSpeedy.SCALE);
        }

        g.fillRect(snakeSpeedy.head.x  * SnakeSpeedy.SCALE, snakeSpeedy.head.y * SnakeSpeedy.SCALE, SnakeSpeedy.SCALE, SnakeSpeedy.SCALE);

        g.setColor(Color.RED);

        g.fillRect(snakeSpeedy.cherry.x * SnakeSpeedy.SCALE, snakeSpeedy.cherry.y * SnakeSpeedy.SCALE, SnakeSpeedy.SCALE, SnakeSpeedy.SCALE);

        String string = "Score: " + snakeSpeedy.score + ", Length: " + snakeSpeedy.tailLength + ", Time: " + snakeSpeedy.time / 20;

        g.setColor(Color.white);

        g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), 10);

        string = "Game Over!";

        if(snakeSpeedy.over){
            g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) (snakeSpeedy.dim.getHeight() / 4));
        }

        string = "Paused!";

        if(snakeSpeedy.paused && !snakeSpeedy.over){
            g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) (snakeSpeedy.dim.getHeight() / 4));
        }
    }
}
