package mmimaa.game.Entity;

import mmimaa.game.System.Camera;
import mmimaa.game.Jumper;
import mmimaa.game.World.Sensor;
import mmimaa.game.World.World;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Point;

import java.util.*;

/**
 * User: mmimaa
 * Date: 2/28/13
 * Time: 7:41 PM
 */
public class Block extends Collidable {

    private Sensor[] sensors;
    private Color color;
    private float momentumY;
    private World world;
    private Point cameraPosition;
    private boolean viewable;

    public Block(String path, World world, float y){
        viewable = true;
        this.world = world;
        this.y = y;


        try {
            texture = new Image(path);
        } catch (SlickException e) {
            e.printStackTrace();
        }

        init();
    }

    public void init(){
        //super.init();

        Random rand = new Random();
        color = new Color(rand.nextInt(245)+10, rand.nextInt(245)+10, rand.nextInt(245)+10);

        width = height = rand.nextInt(50)+50;

        x = rand.nextInt(Jumper.WIDTH-width);
        y-=height;

        //System.out.println(x + ", " + y);

        sensors = new Sensor[3];
        sensors[0] = new Sensor(1, getHeight(), this);
        sensors[1] = new Sensor(getWidth()/2 , getHeight() , this);
        sensors[2] = new Sensor(getWidth()-1, getHeight(), this);

        momentumY = 0f;
    }

    @Override
    public void update(GameContainer gc, int delta) {
        if(cameraPosition != null)
        {
            //if(cameraPosition.getY() > Jumper.HEIGHT)
            if(viewable == false)
                return;
        }

        momentumY += World.GRAVITY;

        /* Limits fall speed */
        momentumY = Math.min(momentumY, world.getBlockFallSpeed());

        List<Collidable> collidableList = new ArrayList<Collidable>();
        collidableList.addAll(world.getCollidables());
        collidableList.remove(this);

        for(Collidable c : collidableList){
            for(int i=0 ; i<3 ; i++){
                sensors[i].Intersects(c, 0f, momentumY);
            }
        }

        if(sensors[0].isColliding() || sensors[1].isColliding() ||
                sensors[2].isColliding()){
            momentumY = 0f;
        }

        moveRelative(0f, momentumY);

        for (int i=0 ; i<3 ; i++)
            sensors[i].Reset();

    }

    @Override
    public void draw(GameContainer gc, Graphics g, Camera c) {
        if(viewable = c.isViewable(x,y)){
            cameraPosition =  c.worldToScreen(x, y);
            texture.draw(cameraPosition.getX(), cameraPosition.getY(),width, height, color);
        }
    }
}
