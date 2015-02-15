package mmimaa.game.Entity;

import mmimaa.game.System.Camera;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * User: mmimaa
 * Date: 2/28/13
 * Time: 6:16 PM
 */
public abstract class Entity {
    protected float x,y;

    public void init(){
        x = y = 0f;
    }

    public void moveAbsolute(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void moveRelative(float x, float y){
        this.x += x;
        this.y += y;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public abstract void update(GameContainer gc, int delta);
    public abstract void draw(GameContainer gc, Graphics g, Camera c);
}
