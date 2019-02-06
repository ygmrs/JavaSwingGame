package solidSnake;

import java.awt.Rectangle;
import java.awt.Graphics2D;

public class Entity {

    private int x, y, size;

    public Entity(int size) {
        this.size = size;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void move(int dx, int dy){
        x += dx;
        y += dy;
    }

    public Rectangle getBound(){
        return new Rectangle(x, y, size, size);
    }

    public boolean isCollsion(Entity entity){
        if(entity == this){ return false;}
        return getBound().intersects(entity.getBound());
    }

    public void render(Graphics2D graphics2D){
        graphics2D.fillRect(x+1, y+1, size-2, size-2);
    }
}
