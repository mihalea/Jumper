package mmimaa.game.Entity;

import org.newdawn.slick.Image;

/**
 * User: mmimaa
 * Date: 2/28/13
 * Time: 7:38 PM
 */
public abstract class Collidable extends Entity {
    protected Image texture;
    protected int width, height;

    public void init(){
       super.init();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
