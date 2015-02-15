package mmimaa.game.System;

import mmimaa.game.GUI.ActionEvent;
import mmimaa.game.GUI.Listener;
import mmimaa.game.GameScreen.GameScreen;
import mmimaa.game.GameScreen.Logo;
import mmimaa.game.Jumper;
import org.newdawn.slick.*;
import org.newdawn.slick.imageout.ImageOut;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

/**
 * User: mmimaa
 * Date: 2/28/13
 * Time: 4:02 PM
 */
public class Controller implements Listener {

    private Stack<GameScreen> gameStack;
    private GameContainer gc;
    private Listener soundListener;

    public Controller(GameContainer gc, Listener soundListener){
        this.gc = gc;
        this.soundListener = soundListener;

        gameStack = new Stack<GameScreen>();
        gameStack.add(new Logo(this));
    }

    public void init(){
    }

    public void update(GameContainer gc, int delta){
        gameStack.peek().update(gc,delta);
    }

    public void draw(GameContainer gc, Graphics g){
        for(GameScreen gs : gameStack)
            gs.draw(gc,g);

        if(gc.getInput().isKeyPressed(Input.KEY_F2))
            saveScreenshot(g);
    }

    private void add(GameScreen gScreen){
        gameStack.add(gScreen);
    }

    private void replaceLast(GameScreen gScreen){
        gameStack.pop();
        gameStack.add(gScreen);
    }

    @Override
    public void alert(ActionEvent e) {
        String event = e.getEvent();

        if(event.contentEquals("replaceLast")){
            replaceLast((GameScreen) e.getObject());
        }
        else if(event.contentEquals("deleteLast")){
            gameStack.pop();
        }
        else if(event.contentEquals("resetLast")){
            gameStack.peek().reset();
        }
        else if(event.contentEquals("add")){
            add((GameScreen) e.getObject());
        }
        else if(event.charAt(0) == 's')
            alertSoundController(e);

    }

    public GameContainer getGc() {
        return gc;
    }

    private void alertSoundController(ActionEvent e){
        soundListener.alert(e);
    }

    private void saveScreenshot(Graphics g) {
        alertSoundController(new ActionEvent("se_screenshot"));

        try {
            Image target = new Image(Jumper.WIDTH, Jumper.HEIGHT);
            g.copyArea(target, 0, 0);
            String name = generateScreenshotName();

            File checkForExistance = new File(name);
            if(checkForExistance.isFile()){
                System.out.println("Screenshot already exists with the same name;");
                return;
            }
            ImageOut.write(target.getFlippedCopy(false, false), name, false);
            target.destroy();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    private String generateScreenshotName(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
        Date date = new Date();
        return Jumper.saveData.getRootPath() + File.separator + Jumper.saveData.SCREENSHOTS_PATH +
                File.separator + Jumper.saveData.getName() + "_" + dateFormat.format(date) + ".png";
    }
}
