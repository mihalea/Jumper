package mmimaa.game.GameScreen;

import mmimaa.game.GUI.ActionEvent;
import mmimaa.game.System.Controller;

/**
 * User: mmimaa
 * Date: 3/16/13
 * Time: 4:35 PM
 */
public class SurvivalGame extends BaseGame {

    public SurvivalGame(Controller controller, int difficulty) {
        super(controller, difficulty);
    }

    @Override
    public void reset() {
        alertListeners(new ActionEvent("replaceLast", new SurvivalGame(parent, difficulty)));
    }


    @Override
    public void alert(ActionEvent e) {
        if(e.getEvent().contentEquals("isDead")){
            alertListeners(new ActionEvent("add", new GameOver(parent, Math.round(score), timePlayed, 0, difficulty)));
        }
    }
}
