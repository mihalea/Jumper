package mmimaa.game.GUI;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mmimaa
 * Date: 3/1/13
 * Time: 7:47 PM
 */
public abstract class Component {
    protected float x,y;
    protected int width, height;
    protected Image[] textures;
    protected List<Listener> listeners;
    protected ActionEvent event;

    public Component(){
        listeners = new ArrayList<Listener>();
        event = new ActionEvent("null");
    }

    public ActionEvent getEvent() {
        return event;
    }

    public void addListener(Listener l){
        if(listeners.contains(l))
            return;

        listeners.add(l);
    }

    public void alertListeners(){
        for( Listener l : listeners){
            l.alert(event);
        }
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void moveAbsolute(float x, float y){
        this.x = x;
        this.y = y;
    }

    public abstract void draw(GameContainer gc, Graphics g);
    public abstract void update(GameContainer gc, int delta);
}
