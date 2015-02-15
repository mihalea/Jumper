package mmimaa.game.World;

import mmimaa.game.Entity.Collidable;
import mmimaa.game.Entity.Entity;

/**
 * User: mmimaa
 * Date: 3/1/13
 * Time: 9:37 AM
 */
public class Sensor{
    float x,y;
    Entity entity;
    boolean collides;

    public Sensor(float x, float y, Entity e){
        this.x = x;
        this.y = y;
        entity = e;
    }

    public void Intersects(Collidable c, float momentumX, float momentumY){
        if( x + entity.getX() + momentumX > c.getX() && x + entity.getX() + momentumX < c.getX() + c.getWidth() &&
                y + entity.getY() + momentumY > c.getY() && y + entity.getY() + momentumY < c.getY() + c.getHeight())
            collides = true;
    }

    public void Reset(){
        collides = false;
    }

    public boolean isColliding(){
        return collides;
    }

    @Override
    public String toString(){
        return collides ? "1" : "0";
    }
}