package mmimaa.game.GUI;

import mmimaa.game.Jumper;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mmimaa
 * Date: 3/1/13
 * Time: 7:49 PM
 */
public class Menu{
    private List<Component> componentList;
    private int spacing;    /* Spacing between components in px */
    private float x,y;
    private boolean autoArrange;
    private Listener parent;

    public Menu(boolean autoArrange, Listener parent){
        componentList = new ArrayList<Component>();

        x = Jumper.WIDTH / 2;
        y = 150f;
        spacing = 15;
        this.autoArrange = autoArrange;
        this.parent = parent;
    }

    public void addComponent(Component c){
        if(autoArrange){
            c.moveAbsolute(x - c.getWidth() / 2, y);
            y = y + spacing + c.getHeight();    /* updates the next y */
        }
        c.addListener(parent);
        componentList.add(c);
    }

    public void draw(GameContainer gc, Graphics g){
        for(Component c : componentList)
            c.draw(gc, g);
    }

    public void update(GameContainer gc, int delta){
        for(Component c : componentList)
            c.update(gc, delta);
    }

}
