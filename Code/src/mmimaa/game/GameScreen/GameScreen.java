package mmimaa.game.GameScreen;

import mmimaa.game.GUI.ActionEvent;
import mmimaa.game.System.Controller;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * User: mmimaa
 * Date: 2/28/13
 * Time: 3:44 PM
 */

public abstract class GameScreen{
    protected final Controller parent;

    public GameScreen(Controller parent){
        this.parent = parent;
    }
    public abstract void init();
    public abstract void draw(GameContainer gc, Graphics g);
    public abstract void update(GameContainer gc, int delta);
    public abstract void alertListeners(ActionEvent e);
    public abstract void reset();

}
