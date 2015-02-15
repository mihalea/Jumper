package mmimaa.game.GameScreen;

import mmimaa.game.GUI.ActionEvent;
import mmimaa.game.Jumper;
import mmimaa.game.System.Controller;
import mmimaa.game.System.Timer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * User: mmimaa
 * Date: 3/16/13
 * Time: 4:40 PM
 */
public class TimedGame extends BaseGame {

    private Timer timeLimit, delta;

    public TimedGame(Controller controller, int difficulty) {
        super(controller, difficulty);

        timeLimit = new Timer();
        delta = new Timer();

        switch (difficulty){
            case 0:
                timeLimit.set(0,0,3,0,0);
                break;
            case 1:
                timeLimit.set(0,0,2,0,0);
                break;
            case 2:
                timeLimit.set(0,0,1,0,0);
                break;
            default:
                break;
        }
    }

    @Override
    public void draw(GameContainer gc, Graphics g) {
        super.draw(gc, g);

        delta.reset();
        delta = timeLimit.subtract(timePlayed);

        smallFont.drawString(Jumper.WIDTH - 85, 50, delta.toShortString() );
    }

    @Override
    public void update(GameContainer gc, int delta) {
        super.update(gc, delta);

        if(timeLimit.compare(timePlayed) == +1){
            alertListeners(new ActionEvent("replaceLast",new GameOver(parent, Math.round(score), timePlayed, 1, difficulty)));
        }
    }

    @Override
    public void reset() {
        alertListeners(new ActionEvent("replaceLast", new TimedGame(parent, difficulty)));
    }

    @Override
    public void alert(ActionEvent e) {
        if(e.getEvent().contentEquals("isDead")){
            alertListeners(new ActionEvent("add", new GameOver(parent, Math.round(score), timePlayed, 1, difficulty)));
        }
    }
}
