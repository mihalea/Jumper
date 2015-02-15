package mmimaa.game.GameScreen;

import mmimaa.game.GUI.ActionEvent;
import mmimaa.game.Jumper;
import mmimaa.game.System.Controller;
import mmimaa.game.System.Timer;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;


/**
 * User: mmimaa
 * Date: 3/10/13
 * Time: 11:08 AM
 */
public class GameOver extends  GameScreen{
    private Image background;
    private UnicodeFont scoreFont, mediumFont;
    private int score;
    private Timer time;
    private int personalHighscore;
    private int mode, difficulty;

    public GameOver(Controller controller, int score, Timer time, int mode, int difficulty){
        super(controller);
        this.score = score;
        this.time = time;
        this.mode = mode;
        this.difficulty = difficulty;

        personalHighscore = Jumper.saveData.getHighscore(mode, difficulty);

        if(score > personalHighscore)
            Jumper.saveData.newHighscore(score, mode, difficulty);

        try {
            background = new Image("res/gui/background/gameOver.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }

        try {
            scoreFont = new UnicodeFont("res/fonts/pixelmix.ttf", 24, false, false);
            scoreFont.addAsciiGlyphs();
            scoreFont.getEffects().add(new ColorEffect(java.awt.Color.white));
            scoreFont.loadGlyphs();

            mediumFont = new UnicodeFont("res/fonts/pixelmix.ttf", 18, false, false);
            mediumFont.addAsciiGlyphs();
            mediumFont.getEffects().add(new ColorEffect(java.awt.Color.white));
            mediumFont.loadGlyphs();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
    }

    @Override
    public void draw(GameContainer gc, Graphics g) {
        background.draw(0,0);

        scoreFont.drawString(Jumper.WIDTH / 2 - scoreFont.getWidth("Highscore: " + personalHighscore) / 2, 175,
                "Highscore: " + personalHighscore);
        scoreFont.drawString(Jumper.WIDTH / 2 - scoreFont.getWidth("Your score: " + score) / 2, 225,
                "Your score: " + score);
        mediumFont.drawString(Jumper.WIDTH / 2 - mediumFont.getWidth("Press escape to exit the game") / 2, 475,
                "Press escape to exit the game");
        mediumFont.drawString(Jumper.WIDTH / 2 - mediumFont.getWidth("Press enter to restart") / 2, 500,
                "Press enter to restart");
        mediumFont.drawString(Jumper.WIDTH / 2 - mediumFont.getWidth("Press M for menu") / 2, 525,
                "Press M for menu");
    }

    @Override
    public void update(GameContainer gc, int delta) {
        Input input = gc.getInput();
        if(input.isKeyPressed(Input.KEY_ENTER)){
            alertListeners(new ActionEvent("deleteLast"));
            alertListeners(new ActionEvent("resetLast"));
        }

        if(input.isKeyPressed(Input.KEY_M)){
            alertListeners(new ActionEvent("deleteLast"));
            alertListeners(new ActionEvent("replaceLast", new MainMenu(parent)));
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
