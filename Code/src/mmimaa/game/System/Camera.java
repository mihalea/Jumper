package mmimaa.game.System;

import mmimaa.game.Jumper;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

/**
 * User: mmimaa
 * Date: 3/9/13
 * Time: 5:33 PM
 */
public class Camera {
    private float x,y;

    public Camera(){
        x = 0f;
        y = 0f;
    }

    public Point worldToScreen(float x, float y){
        Point p = new Point(0f,0f);

        p.setX(x - this.x);
        p.setY(y - this.y);

        return p;
    }

    public void moveRelative(float x, float y){
        this.x += x;
        this.y += y;
    }

    public boolean isViewable(float x, float y){
        return  x >= this.x && x <= this.x + Jumper.WIDTH &&
                y >= this.y && y <= this.y + Jumper.HEIGHT;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
