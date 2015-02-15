package mmimaa.game;

import mmimaa.game.System.Controller;
import mmimaa.game.System.SaveData;
import mmimaa.game.System.SoundController;
import org.newdawn.slick.*;

/**
 * User: mmimaa
 * Date: 2/28/13
 * Time: 3:00 PM
 */
public class Jumper extends BasicGame {

    public static final int HEIGHT = 650;
    public static final int WIDTH = 600;
    public static final String NAME = "Jumper";

    public final int updatesPerSecond = 40;
    public final int msPerUpdate = 1000/updatesPerSecond;


    private static AppGameContainer app;
    public static SaveData saveData;
    private Controller controller;
    private SoundController soundController;


    public Jumper() {
        super(NAME);

    }

    public static void main(String[] args){
        try {


            app = new AppGameContainer(new Jumper());
            app.setDisplayMode(WIDTH, HEIGHT, false);
            app.setResizable(false);
            app.setShowFPS(false);
            app.setMaximumLogicUpdateInterval(1000/40);
            app.setMinimumLogicUpdateInterval(1000/40);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void init(GameContainer gc) throws SlickException {
        saveData = new SaveData();
        soundController = new SoundController();
        controller = new Controller(gc, soundController);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        /* ONLY FOR DEBUGGING! TO BE REMOVED WHEN DEPLOYED */
        if(gc.getInput().isKeyDown(Input.KEY_ESCAPE))
            Exit();

        controller.update(gc, delta);

        /* ONLY FOR DEBUGGING! TO BE REMOVED WHEN DEPLOYED */
        app.setTitle(NAME + " | " + app.getFPS() + " FPS");
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        g.setColor(new Color(128, 220, 232));
        g.fillRect(0,0,WIDTH,HEIGHT);

        controller.draw(gc, g);
    }

    public static void Exit(){
        saveData.save();
        app.exit();
    }

}
