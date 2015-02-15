package mmimaa.game.Entity;

import mmimaa.game.System.Camera;
import mmimaa.game.GUI.ActionEvent;
import mmimaa.game.GUI.Listener;
import mmimaa.game.Jumper;
import mmimaa.game.World.Sensor;
import mmimaa.game.World.World;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mmimaa
 * Date: 2/28/13
 * Time: 6:19 PM
 */
public class Player extends Entity{

    private final int FRAMES = 4;
    private final float ACCELERATION = 5f;
    private final float FRICTION = 0.8f;
    private final float VELOCITY_MAX = 30f;
    private final float FALL_SPEED_MAX = 20f;
    private final float JUMP_FORCE  = 40f;
    private final float BLOCK_GRIP = 0.0095f;
    private final float RELATIVE_MAX_HEIGHT = Jumper.HEIGHT - 400f;

    private Animation[] anim;
    private Sensor[] sensors;
    private PlayerState state;
    private int height, width;
    private World world;
    private boolean dead = false;
    private float momentumX, momentumY;
    private boolean ground;
    private Point cameraPosition;
    private List<Listener> listeners;



    private enum PlayerState{
        IDLE(0), RUN_RIGHT(1), RUN_LEFT(2), HANG_RIGHT(2), HANG_LEFT(1) ;

        public int id;

        PlayerState(int id){
            this.id = id;
        }
    }

    private enum SensorPosition{
        TOP_LEFT(0), TOP_RIGHT(1), RIGHT_TOP(2), RIGHT_BOTTOM(3),
        BOTTOM_RIGHT(4), BOTTOM_LEFT(5), LEFT_BOTTOM(6), LEFT_TOP(7);

        public int id;

        SensorPosition(int id){
            this.id = id;
        }
    }

    public Player(int width, int height, World world){
        listeners = new ArrayList<Listener>();

        momentumX = momentumY = 0.0f;

        this.width = width;
        this.height = height;
        this.world = world;

        anim = new Animation[5];
        for (int i=0 ; i<5 ; i++)
            anim[i] = new Animation();

        sensors = new Sensor[8];
        sensors[0] = new Sensor(5, 0, this);
        sensors[1] = new Sensor(width - 5, 0, this);
        sensors[2] = new Sensor(width, 5, this);
        sensors[3] = new Sensor(width, height - 5, this);
        sensors[4] = new Sensor(width - 5, height, this);
        sensors[5] = new Sensor(5, height, this);
        sensors[6] = new Sensor(0, height - 5, this);
        sensors[7] = new Sensor(0, 5, this);

        init();
    }

    @Override
    public void init() {
        super.init();

        x = Jumper.WIDTH / 2 - width / 2;
        y = Jumper.HEIGHT - 40 - height; /* 40 is the ground height */

        state = PlayerState.IDLE;
        ground = false;

        try {
            SpriteSheet ss = new SpriteSheet("res/entities/player.png", width, height, 1);

            for (int i=0 ; i<2 ; i++ ){
                for (int j=0 ; j<FRAMES ; j++){
                    anim[i].addFrame(ss.getSprite(j, i), 200);
                }
            }

            for (int j=0 ; j<FRAMES ; j++){
                anim[2].addFrame(ss.getSprite(j,1).getFlippedCopy(true, false), 200);
            }


            anim[0].setDuration(0,3500);

        } catch (SlickException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void update(GameContainer gc, int delta) {
        if(dead){
            alertListeners();
        }

        float warp = 0f;
        boolean canWarp = true;
        //momentumX = momentumY = 0f;

        /* Camera movement */
        if(cameraPosition != null){
            if(cameraPosition.getY() < RELATIVE_MAX_HEIGHT)
                world.moveCamera(RELATIVE_MAX_HEIGHT - cameraPosition.getY());

            if(cameraPosition.getY() > Jumper.HEIGHT)
                dead = true;
        }

        /* Player movement*/
        Input in = gc.getInput();


        if(in.isKeyDown(Input.KEY_LEFT)){
            state = PlayerState.RUN_LEFT;
            momentumX -= ACCELERATION;

        }

        if(in.isKeyDown(Input.KEY_RIGHT)){
            state = PlayerState.RUN_RIGHT;
            momentumX += ACCELERATION;
        }

        if(in.isKeyDown(Input.KEY_UP) && ground)
            //System.out.println(ground);
            momentumY -= JUMP_FORCE;

        /* No horizontal movement keys pressed */
        if(in.isKeyDown(Input.KEY_RIGHT) == false && in.isKeyDown(Input.KEY_LEFT) == false)
        {
            state = PlayerState.IDLE;
            momentumX *= FRICTION;
        }

        /* Both horizontal movement keys pressed */
        else if(in.isKeyDown(Input.KEY_RIGHT) && in.isKeyDown(Input.KEY_LEFT))  {
            state = PlayerState.IDLE;
            momentumX = 0;
        }


        momentumY += World.GRAVITY;

        if(momentumX>0f){
            momentumX = Math.min(momentumX, +VELOCITY_MAX);
        } else {
            momentumX = Math.max(momentumX, -VELOCITY_MAX);
        }

        /* Limits fall speed */
        momentumY = Math.min(momentumY, FALL_SPEED_MAX);



         /* Player collision */



        if(x + width/2 < 0)
        {
            warp = (float)Jumper.WIDTH;
            momentumX = 0f;
        }
            //x = Jumper.WIDTH - width/2;

        if(x + width/2> Jumper.WIDTH ){
            warp = (float)-Jumper.WIDTH;
            //momentumX = 0f;
        }
            //x = -width/2;

        List<Collidable> collidableList = new ArrayList<Collidable>();
        collidableList.addAll(world.getCollidables());

        for(Collidable c : collidableList){
            for(int i=0 ; i<8 ; i++){
                sensors[i].Intersects(c, momentumX + warp, momentumY);
            }
        }

        if(sensors[SensorPosition.RIGHT_TOP.id].isColliding() ||
                sensors[SensorPosition.RIGHT_BOTTOM.id].isColliding()){
            momentumX=0f;
            canWarp = false;


            if(ground == false && momentumY>0f){
                momentumY -= BLOCK_GRIP;
                state =  PlayerState.HANG_RIGHT;
            }
        }


        if(sensors[SensorPosition.LEFT_TOP.id].isColliding() ||
                sensors[SensorPosition.LEFT_BOTTOM.id].isColliding()){
            momentumX=0f;
            canWarp = false;

            if(ground == false && momentumY>0f){
                momentumY -= BLOCK_GRIP;
                state =  PlayerState.HANG_LEFT;
            }
        }


        if((sensors[SensorPosition.BOTTOM_LEFT.id].isColliding() ||
                sensors[SensorPosition.BOTTOM_RIGHT.id].isColliding()) &&
            (sensors[SensorPosition.TOP_LEFT.id].isColliding() ||
                    sensors[SensorPosition.TOP_RIGHT.id].isColliding()) &&
                warp==0){
            dead = true;
            System.out.println(x);
        }

        if(sensors[SensorPosition.TOP_LEFT.id].isColliding() ||
                sensors[SensorPosition.TOP_RIGHT.id].isColliding()){
            momentumY = 2f;
            //momentumY = 0f;
        }

        if(sensors[SensorPosition.BOTTOM_LEFT.id].isColliding() ||
                sensors[SensorPosition.BOTTOM_RIGHT.id].isColliding()) {
            ground = true;
            momentumY = 0f;
        }
        else
            ground = false;



        for (int i=0 ; i<8 ; i++)
            sensors[i].Reset();

        if(canWarp == false){
            if(x + width/2 > Jumper.WIDTH){
                x = Jumper.WIDTH - width/2;
                momentumY = 0f;
            }
            else if (x + width/2 < 0){
                x = -width/2;
                momentumY = 0f;
            }

        }

        x += momentumX + (canWarp ? warp : 0);
        y += momentumY;


        world.addDebuggingLine("g: " + ground + "  s: " + state.toString());
        world.addDebuggingLine("d: " + dead);
        world.addDebuggingLine("p: " + x + ", " + y);
        world.addDebuggingLine("m: " + momentumX + ", " + momentumY);
    }

    @Override
    public void draw(GameContainer gc, Graphics g, Camera c) {
        cameraPosition = c.worldToScreen(x,y);
        anim[state.id].draw(cameraPosition.getX(),cameraPosition.getY());
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addListener(Listener l){
        if(listeners.contains(l) == false){
            listeners.add(l);
        }
    }

    private void alertListeners(){
        for(Listener l : listeners){
            l.alert(new ActionEvent("isDead"));
        }
    }
}
