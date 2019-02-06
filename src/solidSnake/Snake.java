package solidSnake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Snake extends JPanel implements Runnable, KeyListener {

	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;

	//key input
	private boolean UP, DOWN, RIGHT, LEFT, START, GAMEOVER, PAUSED;

	//game loop
	private Thread thread;
	private boolean running;
	private long targetTime;

	//render
	private Graphics2D graphics2D;
	private BufferedImage bufferedImage;

	//game stuff
	private int SIZE = 10;
	private Entity head, apple;
	private ArrayList<Entity> snake;

	private int score, level;

	//movement
	private int dx, dy;

	public Snake(){
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
	}

	@Override
	public void addNotify(){
		super.addNotify();
		thread = new Thread(this);
		thread.start();
	}

	//framesPerSecond
	private void setFps(int fps){
		targetTime = 1000 / fps;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) UP = true;
		if(keyCode == KeyEvent.VK_DOWN  || keyCode == KeyEvent.VK_S) DOWN = true;
		if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) RIGHT = true;
		if(keyCode == KeyEvent.VK_LEFT  || keyCode == KeyEvent.VK_A) LEFT = true;
		if(keyCode == KeyEvent.VK_ENTER) START = true;
		if(keyCode == KeyEvent.VK_SPACE) PAUSED = !PAUSED;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_UP) UP = false;
		if(keyCode == KeyEvent.VK_DOWN) DOWN = false;
		if(keyCode == KeyEvent.VK_RIGHT) RIGHT = false;
		if(keyCode == KeyEvent.VK_LEFT) LEFT = false;
		if(keyCode == KeyEvent.VK_ENTER) START = false;
	}

	@Override
	public void run() {
		if(running) return;
		init();
		long startTime;
		long elapsed;
		long wait;
		while (running){
			startTime = System.nanoTime();

			update();
			requestRender();

			elapsed = System.nanoTime() - startTime;
			wait = targetTime - (elapsed / 1000000); //Convert Nanoseconds to Milliseconds
			if(wait > 0){
				try{
					Thread.sleep(wait);
				}catch (Exception exp){
					exp.printStackTrace();
				}
			}
		}
	}

	private void init(){
		bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		graphics2D = bufferedImage.createGraphics();
		running = true;
		setUpLevel();
	}

	private void setUpLevel(){
        snake = new ArrayList<Entity>();
        head = new Entity(SIZE);
        head.setPosition(WIDTH / 2, HEIGHT / 2);
        snake.add(head);

        for (int i = 1; i < 3 ; i++) {
            Entity entity = new Entity(SIZE);
            entity.setPosition(head.getX() + (i * SIZE), head.getY());
            snake.add(entity);
        }

        apple = new Entity(SIZE);
        setApple();
        score = 0;
        GAMEOVER = false;
        PAUSED = false;
        level = 1;
        dx = dy = 0;
        setFps(level * 10);
	}

	public void setApple(){
        int x = (int) (Math.random() * (WIDTH - SIZE));
        int y = (int) (Math.random() * (HEIGHT - SIZE));
        x = x - (x % SIZE);
        y = y - (y % SIZE);
        apple.setPosition(x, y);
	}

	private void requestRender(){
        render(graphics2D);
        Graphics graphics = getGraphics();
        graphics.drawImage(bufferedImage, 0, 0, null);
        graphics.dispose();
	}

	private void update(){
		if(!PAUSED) {
			if (GAMEOVER) {
				if (START) {
					setUpLevel();
				}
				return;
			}
			if (UP && dy == 0) {
				dy = -SIZE;
				dx = 0;
			}
			if (DOWN && dy == 0) {
				dy = SIZE;
				dx = 0;
			}
			if (LEFT && dx == 0) {
				dx = -SIZE;
				dy = 0;
			}
			if (RIGHT && dx == 0 && dy != 0) {
				dx = SIZE;
				dy = 0;
			}
			if (dx != 0 || dy != 0) {
				for (int i = snake.size() - 1; i > 0; i--) {
					snake.get(i).setPosition(
							snake.get(i - 1).getX(),
							snake.get(i - 1).getY()
					);
				}
				head.move(dx, dy);
			}

			for (Entity e : snake) {
				if (e.isCollsion(head)) {
					GAMEOVER = true;
					break;
				}
			}

			if (apple.isCollsion(head)) {
				score++;
				setApple();
				Entity entity = new Entity(SIZE);
				entity.setPosition(-100, -100);
				snake.add(entity);
				if (score % 10 == 0) {
					level++;
					if (level > 10) level = 10;
					setFps(level * 10);
				}
			}

			if (head.getX() < 0) head.setX(WIDTH);
			if (head.getY() < 0) head.setY(HEIGHT);
			if (head.getX() > WIDTH) head.setX(0);
			if (head.getY() > HEIGHT) head.setY(0);
		}
	}

	public void render(Graphics2D graphics2D){
        graphics2D.clearRect(0, 0, WIDTH, HEIGHT);

        graphics2D.setColor(Color.GREEN);
        for (Entity entity: snake){
            entity.render(graphics2D);
        }

        graphics2D.setColor(Color.RED);
        apple.render(graphics2D);

        if(GAMEOVER){
            graphics2D.drawString("GAMEOVER!", 150, 200);
        }

        graphics2D.setColor(Color.white);
        graphics2D.drawString("Score: " + score + " Level: " + level, 10, 10);

        if(dx == 0 && dy == 0){
            graphics2D.drawString("Ready!", 150, 200);
        }


		if(PAUSED && !GAMEOVER){
			graphics2D.drawString("Paused!", 150, 200);
		}
	}


}
