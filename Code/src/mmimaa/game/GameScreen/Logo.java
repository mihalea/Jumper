package mmimaa.game.GameScreen;

import mmimaa.game.GUI.ActionEvent;
import mmimaa.game.System.Controller;
import org.newdawn.slick.*;

/**
 * User: mmimaa
 * Date: 2/28/13
 * Time: 3:49 PM
 */
public class Logo extends GameScreen
{
    private Image background;
    private int displayTime; /* time is counted in miliseconds */
    private int currentTime;

    public Logo(Controller controller) {
        super(controller);
        init();
    }

    @Override
    public void init() {
        /* Load assets */
        try {
            background = new Image("res/gui/background/logo.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }

        displayTime = 2000;
        currentTime = 0;
    }

    @Override
    public void draw(GameContainer gc, Graphics g) {
        if(background != null)
            background.draw(0,0);
    }

    @Override
    public void update(GameContainer gc, int delta) {
        Input in = gc.getInput();

        if(in.isKeyPressed(Input.KEY_SPACE) == true)
        {
            alertListeners(new ActionEvent("replaceLast", new Profile(parent)));
        }

        currentTime += delta;

        if(currentTime > displayTime)
            alertListeners(new ActionEvent("replaceLast", new Profile(parent)));

    }

    @Override
    public void alertListeners(ActionEvent e) {
        parent.alert(e);
    }

    @Override
    public void reset() {
    }
}
