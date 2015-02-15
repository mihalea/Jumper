package mmimaa.game.World;

import mmimaa.game.System.Camera;
import mmimaa.game.Entity.Collidable;
import mmimaa.game.Entity.Ground;
import mmimaa.game.Entity.Player;
import mmimaa.game.GameScreen.BaseGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mmimaa
 * Date: 2/28/13
 * Time: 8:01 PM
 */
public class World {
    public static final float GRAVITY = 3f;
    private float blockFallSpeed;

    private List<Collidable> collidables;
    private Player player;

    private BaseGame parentScreen;

    private int difficulty;


    public World(BaseGame baseGame, int difficulty){
        collidables = new ArrayList<Collidable>();
        parentScreen = baseGame;
        player = new Player(40, 79, this);
        player.addListener(parentScreen);
        this.difficulty = difficulty;

        init();
    }

    public void init(){
        Ground ground = new Ground("res/terrain/ground.png");
        addCollidable(ground);

        switch (difficulty){
            case 0:
                blockFallSpeed = 5f;
                break;
            case 1:
                blockFallSpeed = 7.5f;
                break;
            case 2:
                blockFallSpeed = 10f;
                break;
            default:
                System.out.println("Wrong difficulty");
                break;
        }
    }

    public void draw(GameContainer gc, Graphics g,Camera c){
        for(Collidable cl : collidables)
            cl.draw(gc, g, c);
        player.draw(gc, g, c);
    }

    public void update(GameContainer gc, int delta){
        for(Collidable c : collidables)
            c.update(gc, delta);

        player.update(gc, delta);

        addDebuggingLine("e: " + collidables.size());
    }

    public void addCollidable(Collidable c){
        collidables.add(c);
    }

    public List<Collidable> getCollidables(){
        return collidables;
    }

    public void addDebuggingLine(String s){
        parentScreen.addDebugginLine(s);
    }

    public void moveCamera(float offset){
        parentScreen.moveCamera(offset);
    }

    public float getPlayerY(){
        return player.getY() + player.getHeight() + 40; /* 40 is the ground height */
    }

    public float getBlockFallSpeed() {
        return blockFallSpeed;
    }
}
