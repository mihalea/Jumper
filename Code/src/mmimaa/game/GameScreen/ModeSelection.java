package mmimaa.game.GameScreen;

import mmimaa.game.GUI.ActionEvent;
import mmimaa.game.GUI.Button;
import mmimaa.game.GUI.Listener;
import mmimaa.game.System.Controller;
import org.newdawn.slick.*;

/**
 * User: mmimaa
 * Date: 3/15/13
 * Time: 5:15 PM
 */
public class ModeSelection extends GameScreen implements Listener {
    Image background;
    Image arrow;

    Button survival, timeAttack;
    Button easy, normal, hard;
    Button back,start;

    int mode, difficulty;

    public ModeSelection(Controller parent) {
        super(parent);

        mode = 0;
        difficulty = 0;

        survival = new Button("res/gui/button/modeSelection/survival.png",new ActionEvent("setSurvival"), 140, 180);
        survival.moveAbsolute(106, 100);
        survival.addListener(this);
        timeAttack = new Button("res/gui/button/modeSelection/timeAttack.png",new ActionEvent("setTimeAttack"), 140, 180);
        timeAttack.moveAbsolute(352, 100);
        timeAttack.addListener(this);

        easy = new Button("res/gui/button/modeSelection/easy.png",new ActionEvent("setEasy"), 100, 110);
        easy.moveAbsolute(106, 325);
        easy.addListener(this);

        normal = new Button("res/gui/button/modeSelection/normal.png",new ActionEvent("setNormal"), 100, 110);
        normal.moveAbsolute(246, 325);
        normal.addListener(this);

        hard = new Button("res/gui/button/modeSelection/hard.png",new ActionEvent("setHard"), 100, 110);
        hard.moveAbsolute(386, 325);
        hard.addListener(this);

        back = new Button("res/gui/button/modeSelection/back.png",new ActionEvent("replaceLast", new MainMenu(parent)), 100, 50);
        back.moveAbsolute(106, 550);
        back.addListener(this);

        start = new Button("res/gui/button/modeSelection/startGame.png",new ActionEvent("startGame"), 240, 50);
        start.moveAbsolute(246, 550);
        start.addListener(this);

        try {
            background = new Image("res/gui/background/dots.png");
            arrow = new Image("res/gui/button/modeSelection/arrow.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
    }

    @Override
    public void draw(GameContainer gc, Graphics g) {
        background.draw(0f,0f);

        arrow.getFlippedCopy(false, true).draw(106 + 140 / 2 - 25 + (mode == 0 ? 0 : 140 + 106), 100 - 50 - 10);
        arrow.draw(106 + 50 - 25 + difficulty * 140,450);

        /* Buttons */
        survival.draw(gc, g);
        timeAttack.draw(gc, g);
        easy.draw(gc, g);

        normal.draw(gc, g);
        hard.draw(gc, g);

        back.draw(gc, g);
        start.draw(gc, g);
    }

    @Override
    public void update(GameContainer gc, int delta) {
        start.getEvent().getObject();

        survival.update(gc,delta);
        timeAttack.update(gc, delta);

        easy.update(gc, delta);
        normal.update(gc, delta);
        hard.update(gc, delta);

        back.update(gc, delta);
        start.update(gc, delta);
    }

    @Override
    public void alertListeners(ActionEvent e) {
        parent.alert(e);
    }

    @Override
    public void alert(ActionEvent e) {
        String event = e.getEvent();
        //System.out.println(event);

        if(event.contentEquals("replaceLast"))
            alertListeners(e);
        else if(event.contentEquals("setSurvival"))
            mode = 0;
        else if(event.contentEquals("setTimeAttack"))
            mode = 1;
        else if(event.contentEquals("setEasy"))
            difficulty = 0;
        else if(event.contentEquals("setNormal"))
            difficulty = 1;
        else if(event.contentEquals("setHard"))
            difficulty = 2;
        else if(event.contentEquals("startGame")){
            if(mode == 0)
                alertListeners(new ActionEvent("replaceLast", new SurvivalGame(parent, difficulty)));
            else if(mode == 1)
                alertListeners(new ActionEvent("replaceLast", new TimedGame(parent, difficulty)));
        }
    }

    @Override
    public void reset() {
    }
}
