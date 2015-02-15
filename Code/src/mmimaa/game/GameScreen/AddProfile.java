package mmimaa.game.GameScreen;

import mmimaa.game.GUI.ActionEvent;
import mmimaa.game.Jumper;
import mmimaa.game.System.Controller;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.TextField;

import java.awt.Color;

/**
 * User: mmimaa
 * Date: 3/10/13
 * Time: 7:11 PM
 */
public class AddProfile extends GameScreen{

    private Image background;
    private UnicodeFont font;
    private TextField textField;


    public AddProfile(Controller parent) {
        super(parent);

        try {
            font = new UnicodeFont("res/fonts/pixelmix.ttf",24, false, false);
            font.addAsciiGlyphs();
            font.getEffects().add(new ColorEffect(Color.white));
            font.loadGlyphs();
        } catch (SlickException e) {
            e.printStackTrace();
        }

        try {
            background = new Image("res/gui/background/addProfile.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }

        textField = new TextField(parent.getGc(), font, 150, 100, font.getWidth("AAAAAAAAAA") + 10, font.getLineHeight() + 5);
        textField.setMaxLength(10);
        textField.setCursorVisible(true);
        textField.setFocus(true);
    }

    @Override
    public void init() {
    }

    @Override
    public void draw(GameContainer gc, Graphics g) {
        /* Background */
        background.draw(0,0);
        textField.render(gc, g);

        /* Name: */
        font.drawString(50,100, "Name: ");
    }

    @Override
    public void update(GameContainer gc, int delta) {
        if(gc.getInput().isKeyPressed(Input.KEY_ENTER)){
            if(textField.getText().isEmpty() == false){
                Jumper.saveData.newSave(textField.getText());
                alertListeners(new ActionEvent("replaceLast", new Profile(parent)));
            }
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
