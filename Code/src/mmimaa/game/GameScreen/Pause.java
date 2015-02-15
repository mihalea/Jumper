package mmimaa.game.GameScreen;

import mmimaa.game.GUI.ActionEvent;
import mmimaa.game.System.Controller;
import org.newdawn.slick.*;

/**
 * User: mmimaa
 * Date: 4/23/13
 * Time: 9:37 PM
 */
public class Pause extends GameScreen {

    Image overlay, background;

    public Pause(Controller parent) {
        super(parent);

        try {
            overlay = new Image("res/gui/background/overlay.png");
            background = new Image("res/gui/background/paused.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void init() {
    }

    @Override
    public void draw(GameContainer gc, Graphics g) {
        overlay.draw();
        background.draw();
    }

    @Override
    public void update(GameContainer gc, int delta) {

        Input input = gc.getInput();
        if(input.isKeyPressed(Input.KEY_P)){
            alertListeners(new ActionEvent("deleteLast"));
            input.clearKeyPressedRecord();
        }
    }

    @Override
    public void alertListeners(ActionEvent e) {
        parent.alert(e);
    }

    @Override
    public void reset() {
    }
}
