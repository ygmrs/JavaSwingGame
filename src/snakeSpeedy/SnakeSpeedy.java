package snakeSpeedy;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

public class SnakeSpeedy implements ActionListener, KeyListener{

    public JFrame jFrame;
    public RenderPanel renderPanel;
    public static SnakeSpeedy snakeSpeedy;
    public Timer timer = new Timer(20, this);
    public ArrayList<Point> snakeParts = new ArrayList<>();
    public static final int UP=0, DOWN=1, LEFT=2, RIGHT=3, SCALE=10;
    public int ticks=0, direction=DOWN, score, tailLength = 10, time;
    public Point head, cherry;
    public Random random;
    public boolean over = false, paused;
    public Dimension dim;

    public SnakeSpeedy(){
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame = new JFrame("RealSnake");
        jFrame.setVisible(true);
        jFrame.setSize(800, 700);
//        jFrame.setResizable(false);
        jFrame.setLocation(dim.width / 2 - jFrame.getWidth() / 2, dim.height / 2 - jFrame.getHeight() / 2);
        jFrame.add(renderPanel = new RenderPanel());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.addKeyListener(this);
        startGame();
    }

    public void startGame(){
        over = false;
        paused = false;
        time = 0;
        score = 0;
        tailLength = 5;
        direction = DOWN;
        head = new Point(0, -1);
        random = new Random();
        snakeParts.clear();
        cherry = new Point(random.nextInt(79), random.nextInt(66));
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        renderPanel.repaint();
        ticks++;
        if(ticks % 2 == 0 && head != null && !over && !paused){
            time++;
            snakeParts.add(new Point(head.x, head.y));
            if(direction == UP)
                if(head.y - 1 >= 0 && noTailAt(head.x, head.y - 1))
                    head = new Point(head.x, head.y - 1);
                else
                    over = true;
            if(direction == DOWN)
                if(head.y + 1 < 67  && noTailAt(head.x, head.y + 1))
                    head = new Point(head.x, head.y + 1);
                else
                    over = true;
            if(direction == LEFT)
                if(head.x - 1 >= 0 && noTailAt(head.x - 1, head.y))
                    head = new Point(head.x - 1, head.y);
                else
                    over = true;
            if(direction == RIGHT && noTailAt(head.x + 1, head.y))
                if(head.x + 1 < dim.width / SCALE)
                    head = new Point(head.x + 1, head.y);
                else
                    over = true;
            if (snakeParts.size() > tailLength){
                snakeParts.remove(0);
            }
            if(cherry != null){
                if(head.equals(cherry)){
                    score += 10;
                    tailLength++;
                    cherry.setLocation(random.nextInt(79), random.nextInt(66));
                }
            }
        }
    }


    public boolean noTailAt(int x, int y){
        for(Point point: snakeParts){
            if(point.equals(new Point(x, y)))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        snakeSpeedy = new SnakeSpeedy();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();
        if((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && direction != RIGHT)
            direction = LEFT;
        if((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && direction != LEFT)
            direction = RIGHT;
        if((i == KeyEvent.VK_W || i == KeyEvent.VK_UP) && direction != DOWN)
            direction = UP;
        if((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && direction != UP)
            direction = DOWN;
        if(i == KeyEvent.VK_SPACE)
            if(over)
                startGame();
            else
                paused = !paused;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
