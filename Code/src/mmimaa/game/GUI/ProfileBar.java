package mmimaa.game.GUI;

import mmimaa.game.Jumper;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.imageout.ImageOut;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import java.io.*;

/**
 * User: mmimaa
 * Date: 4/23/13
 * Time: 7:55 PM
 */
public class ProfileBar extends Component {

    private UnicodeFont font;
    private String name;
    private Image icon;
    private JFileChooser fileUI;

    public ProfileBar() {
        super();

        name = Jumper.saveData.getName();
        icon = Jumper.saveData.getIcon();

        fileUI = new JFileChooser();
        fileUI.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() ||
                        f.getAbsolutePath().endsWith(".png");
            }

            @Override
            public String getDescription() {
                return null;
            }
        });

        fileUI.setDialogTitle("Choose an icon");

        textures = new Image[1];
        try {
            textures[0] = new Image("res/gui/profileBar.png");
            font = new UnicodeFont("res/fonts/pixelmix.ttf",18,false,false);
            font.addAsciiGlyphs();
            font.getEffects().add(new ColorEffect(java.awt.Color.white));
            font.loadGlyphs();
        } catch (SlickException e) {
            e.printStackTrace();
        }

        moveAbsolute(0,0);
    }

    @Override
    public void draw(GameContainer gc, Graphics g) {
        textures[0].draw(x,y);
        font.drawString(x+65, y+20, name, Color.white);
        if(icon != null)
            icon.draw(x+5,y+5,50,50);
    }

    @Override
    public void update(GameContainer gc, int delta) {
        Input input = gc.getInput();

        if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
            if(input.getAbsoluteMouseX() > x && input.getAbsoluteMouseX() < x + 205 &&
                    input.getAbsoluteMouseY() > y && input.getAbsoluteMouseY() < y + 60){
                fileUI.showOpenDialog(null);

                try {
                    icon = new Image(fileUI.getSelectedFile().getAbsolutePath());
                    Jumper.saveData.setIcon(icon);

                    File fileOut = new File(Jumper.saveData.getRootPath() + File.separator +
                                                 Jumper.saveData.ICONS_PATH + File.separator + name + ".png");
                    ImageOut.write(icon, fileOut.getAbsolutePath());


                } catch (SlickException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
