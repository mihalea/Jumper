package mmimaa.game.System;

import mmimaa.game.GUI.ActionEvent;
import mmimaa.game.GUI.Listener;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.util.HashMap;

/**
 * User: mmimaa
 * Date: 3/20/13
 * Time: 7:09 PM
 */
public class SoundController implements Listener {
    private HashMap<String, Sound> effectList, musicList;
    private int effectVolume, musicVolume;
    private boolean effectMute, musicMute;

    public SoundController(){
        effectList = new HashMap<String, Sound>();
        musicList = new HashMap<String, Sound>();

        effectVolume = 100;
        musicVolume = 100;

        effectMute = false;
        musicMute = false;

        init();
    }

    private void init(){
        try {
            effectList.put("screenshot",new Sound("res/sounds/effects/screenshot.ogg"));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void playEffect(String name){
        if(effectMute == false)
            effectList.get(name).play(1.0f, effectVolume/100f);
    }

    public void update(GameContainer gc, int delta){
        if(gc.getInput().isKeyPressed(Input.KEY_M))
            effectMute = musicMute = !musicMute;
    }

    @Override
    public void alert(ActionEvent e) {
        String event = e.getEvent();

        if(event.charAt(1) == 'e')
            playEffect(event.substring(3));
    }
}
