package mmimaa.game.GameScreen;

import mmimaa.game.System.Camera;
import mmimaa.game.GUI.ActionEvent;
import mmimaa.game.GUI.Listener;
import mmimaa.game.Jumper;
import mmimaa.game.System.Controller;
import mmimaa.game.System.Timer;
import mmimaa.game.Entity.Block;
import mmimaa.game.World.World;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mmimaa
 * Date: 2/28/13
 * Time: 4:51 PM
 */
public abstract class BaseGame extends GameScreen implements Listener {

    protected World world;
    protected boolean debugging;
    protected Camera camera;
    protected Timer timePlayed;
    protected UnicodeFont smallFont;
    protected List<String> debuggingText;
    protected boolean deploying;
    protected int currentTiming;
    protected float score;
    protected int difficulty;
    protected float blockTiming;

    public BaseGame(Controller controller, int difficulty){
        super(controller);
        camera = new Camera();
        timePlayed = new Timer();
        this.difficulty = difficulty;


        init();
    }

    @Override
    public void init() {
        deploying = false;
        debugging = false;
        currentTiming = 0;
        score = 0;

        debuggingText = new ArrayList<String>();
        world = new World(this, difficulty);

        try {
            smallFont = new UnicodeFont("res/fonts/pixelmix.ttf", 12, false, false);
            smallFont.addAsciiGlyphs();
            smallFont.getEffects().add(new ColorEffect(java.awt.Color.black));
            smallFont.loadGlyphs();

        } catch (SlickException e) {
            e.printStackTrace();
        }

        switch (difficulty){
            case 0:
                blockTiming = 3000;
                break;
            case 1:
                blockTiming = 2500;
                break;
            case 2:
                blockTiming = 1500;
                break;
            default:
                break;
        }
    }

    @Override
    public void draw(GameContainer gc, Graphics g) {
        world.draw(gc, g, camera);

        if(!deploying)
            smallFont.drawString(Jumper.WIDTH / 2 - smallFont.getWidth("Press space to start") / 2 , 50,
                    "Press space to start");

        smallFont.drawString(Jumper.WIDTH - 100f, 25, "Score: " + Math.round(score));

        if(debugging){
            int line = 0;
            for(String s : debuggingText){
                smallFont.drawString(10, 15+line*20, s);
                line++;
            }
        }

    }

    @Override
    public void update(GameContainer gc, int delta) {
        /*
        if(Jumper.HEIGHT - world.getPlayerY() > score)
            score =  Jumper.HEIGHT - world.getPlayerY();
            */

        debuggingText = new ArrayList<String>();  /** It's the same as debuggintText.clear() **/
        if(deploying)
            timePlayed.add(delta);
        addDebugginLine(timePlayed.toString());
        addDebugginLine("c: " + camera.getX() + ", " + camera.getY());

        if(deploying){
            currentTiming += delta;

            if(currentTiming > blockTiming){
                world.addCollidable(new Block("res/entities/block.png", world, camera.getY()));
                currentTiming = 0;
            }
        }


        Input in = gc.getInput();

        if(in.isKeyPressed(Input.KEY_F3))
            debugging = !debugging;

        if(in.isKeyPressed(Input.KEY_SPACE))
            deploying = !deploying;

        if(in.isKeyPressed(Input.KEY_R))
            this.reset();

        if(in.isKeyPressed(Input.KEY_P))
            alertListeners(new ActionEvent("add",new Pause(parent)));


        world.update(gc, delta);
    }

    @Override
    public void alertListeners(ActionEvent e) {
        parent.alert(e);
    }

    public void addDebugginLine(String line){
        if(debuggingText.contains(line) == false)
            debuggingText.add(line);
    }

    public void moveCamera(float offset){
        camera.moveRelative(0f, -offset);

        score += offset;
    }
}
