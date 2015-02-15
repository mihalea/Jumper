package mmimaa.game.GameScreen;

import mmimaa.game.GUI.*;
import mmimaa.game.GUI.Menu;
import mmimaa.game.Jumper;
import mmimaa.game.System.Controller;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.Color;

/**
 * User: mmimaa
 * Date: 3/1/13
 * Time: 6:08 PM
 */
public class MainMenu extends  GameScreen implements Listener {

    Image background;
    Menu menu;
    UnicodeFont buttonFont;
    ProfileBar profileBar;

    public MainMenu(Controller controller){
        super(controller);
        menu = new Menu(true, this);
        profileBar = new ProfileBar();
        profileBar.addListener(this);

        init();
    }

    @Override
    public void init() {
        try {
            buttonFont = new UnicodeFont("res/fonts/pixelmix.ttf", 18, false, false);
            buttonFont.addAsciiGlyphs();
            buttonFont.getEffects().add(new ColorEffect(Color.black));
            buttonFont.loadGlyphs();
        } catch (SlickException e) {
            e.printStackTrace();
        }

        try {
            background = new Image("res/gui/background/dots.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }

        menu.addComponent(new TextButton("res/gui/button/menu/roundButton.png", buttonFont, new ActionEvent("startGame"), "New Game", 350, 75 ));
        menu.addComponent(new TextButton("res/gui/button/menu/roundButton.png", buttonFont, new ActionEvent("help"), "Help", 350, 75 ));
        //menu.addComponent(new TextButton("res/gui/button/menu/roundButton.png", buttonFont, new ActionEvent("null"), "Options", 350, 75 ));
        menu.addComponent(new TextButton("res/gui/button/menu/roundButton.png", buttonFont, new ActionEvent("null"), "Stats", 350, 75 ));
        menu.addComponent(new TextButton("res/gui/button/menu/roundButton.png", buttonFont, new ActionEvent("exit"), "Exit", 350, 75 ));
    }

    @Override
    public void draw(GameContainer gc, Graphics g) {
        background.draw(0,0);
        menu.draw(gc, g);
        profileBar.draw(gc, g);
    }

    @Override
    public void update(GameContainer gc, int delta) {
        menu.update(gc, delta);
        profileBar.update(gc, delta);
    }

    @Override
    public void alertListeners(ActionEvent e) {
        parent.alert(e);
    }


    @Override
    public void alert(ActionEvent e) {
        String event = e.getEvent();

        if(event.contentEquals("startGame"))
            alertListeners(new ActionEvent("replaceLast", new ModeSelection(parent)));
        else if(event.contentEquals("exit"))
            Jumper.Exit();
    }

    @Override
    public void reset() {
    }
}
