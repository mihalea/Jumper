package mmimaa.game.Entity;

import mmimaa.game.System.Camera;
import mmimaa.game.Jumper;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Point;

import java.util.Random;

/**
 * User: mmimaa
 * Date: 2/28/13
 * Time: 7:41 PM
 */
public class Ground extends Collidable {

    private Color color;

    public Ground(String path){

        try {
            texture = new Image(path);
        } catch (SlickException e) {
            e.printStackTrace();
        }

        init();
    }

    public void init(){
        super.init();

        width = texture.getWidth();
        height = texture.getHeight();

        x = 0;
        y = Jumper.HEIGHT - getHeight();

        Random rand = new Random();
        color = new Color(rand.nextInt(245)+10, rand.nextInt(245)+10, rand.nextInt(245)+10);
    }

    @Override
    public void update(GameContainer gc, int delta) {
    }

    @Override
    public void draw(GameContainer gc, Graphics g, Camera c) {
        if(c.isViewable(x,y)){
            Point screen =  c.worldToScreen(x, y);
            texture.draw(screen.getX(), screen.getY(),width, height, color);
        }
    }
}
