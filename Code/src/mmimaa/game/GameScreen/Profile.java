package mmimaa.game.GameScreen;

import mmimaa.game.GUI.*;
import mmimaa.game.Jumper;
import mmimaa.game.System.Controller;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

/**
 * User: mmimaa
 * Date: 3/2/13
 * Time: 6:36 PM
 */
public class Profile extends GameScreen implements Listener {

    private final int SPACING = 30;
    private final float startX = 125f, startY = 100f;

    private List<String> saves;
    private UnicodeFont bigFont;
    private List<ProfileEntry> profiles;
    private Menu buttons;
    private boolean toSkip = false;

    public Profile(Controller controller){
        super(controller);
        buttons = new Menu(false, this);
        saves = new ArrayList<String>();
        profiles = new ArrayList<ProfileEntry>();
        init();
    }

    @Override
    public void init() {

        try {
            bigFont = new UnicodeFont("res/fonts/pixelmix.ttf", 18, false, false);
            bigFont.addAsciiGlyphs();
            bigFont.getEffects().add(new ColorEffect(Color.black));
            bigFont.loadGlyphs();
        } catch (SlickException e) {
            e.printStackTrace();
        }


        Button addButton = new Button("res/gui/button/profile/addButton.png",new ActionEvent("profileAdd"), 50, 50);
        addButton.moveAbsolute(125,45);
        buttons.addComponent(addButton);

        Button removeButton = new Button("res/gui/button/profile/removeButton.png", new ActionEvent("profileRemove"), 50, 50);
        removeButton.moveAbsolute(180, 45);
        buttons.addComponent(removeButton);

        File folder = new File(Jumper.saveData.getRootPath() + File.separator + Jumper.saveData.SAVES_PATH);

        if(folder != null && folder.isDirectory())
        {
            for(final File fileEntry : folder.listFiles()){
                if(fileEntry.isFile()){
                    String name = fileEntry.getName();

                    if(name.endsWith(".sav")){
                        saves.add(name);

                        if(saves.size() == 3)
                            break;
                    }
                }
            }
        }

        /* Debugging feature . To be removed when deployed */
        if(saves.size() == 0){
            toSkip = true;
            System.out.println("Empty saves folder");
            return;
        }

        int index = 0;
        for(String s : saves){
            ProfileEntry pe = new ProfileEntry(s, bigFont, startX, startY + index * ProfileEntry.HEIGHT + index * SPACING);
            pe.addListener(this);

            profiles.add(pe);

            index++;
        }
    }


    @Override
    public void draw(GameContainer gc, Graphics g) {
        g.setColor(new org.newdawn.slick.Color(36, 4, 5));
        g.fillRect(0,0,Jumper.WIDTH,Jumper.HEIGHT);

        buttons.draw(gc, g);

        for(ProfileEntry pe : profiles){
            pe.draw(gc, g);
        }
    }

    @Override
    public void update(GameContainer gc, int delta) {
        if(toSkip){
            alertListeners(new ActionEvent("replaceLast",new AddProfile(parent)));
        }

        for(ProfileEntry pe : profiles){
            pe.update(gc, delta);
        }

        buttons.update(gc, delta);
    }

    @Override
    public void alertListeners(ActionEvent e) {
        parent.alert(e);
    }

    @Override
    public void alert(ActionEvent e) {
        String event = e.getEvent();

        if(event.startsWith("load"))
        {
            Jumper.saveData.load(event.substring(4));
            alertListeners(new ActionEvent("replaceLast", new MainMenu(parent)));
        }
        if(event.startsWith("profile")){
            if(event.contains("Add")){
                if(saves.size()<3){
                    alertListeners(new ActionEvent("replaceLast", new AddProfile(parent)));
                }
            }
        }

    }

    private enum ButtonState{
        NONE(0), HOVER(1), PRESS(2);

        int id;

        ButtonState(int id){
            this.id = id;
        }
    }

    private class ProfileEntry extends Component {
        public static final int HEIGHT = 120;
        private Image icon;
        private String name;
        private UnicodeFont font;
        private ButtonState state;




        public ProfileEntry(String savePath, UnicodeFont font, float x, float y){
            super();

            name = savePath.substring(0, savePath.length() - 4);
            event = new ActionEvent("load" + name);
            this.x = x;
            this.y = y;
            this.font = font;
            state = ButtonState.NONE;


            File folder = new File(Jumper.saveData.getRootPath() + File.separator + Jumper.saveData.ICONS_PATH);

            if(folder != null && folder.isDirectory())
            {
                for(final File fileEntry : folder.listFiles()){
                    if(fileEntry.isFile()){
                        String iName = fileEntry.getName();

                        if(iName.endsWith(".png") && iName.substring(0, iName.length() - 4).contentEquals(name)){
                            try {
                                icon = new Image(folder.getAbsolutePath() + File.separator + iName);
                            } catch (SlickException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            if(icon == null){
                try {
                    icon = new Image("res/gui/defaultIcon.png");
                } catch (SlickException e) {
                    e.printStackTrace();
                }
            }

            textures = new Image[3];

            try {
                SpriteSheet spriteSheet = new SpriteSheet("res/gui/profileEntry.png", 350, 130, 1);
                for (int i=0 ; i<3 ; i++){
                    textures[i] = spriteSheet.getSubImage(i, 0);
                }

            } catch (SlickException e) {
                e.printStackTrace();
            }

            width = textures[0].getWidth();
            height = textures[0].getHeight();
        }

        public void draw(GameContainer gc, Graphics g){
            textures[state.id].draw(x,y);
            icon.draw(x + 15f, y + 15, 100, 100);
            font.drawString(x + 194, y + (state == ButtonState.PRESS ? 36 : 35), name);
        }

        @Override
        public void update(GameContainer gc, int delta) {
            Input in = gc.getInput();

            float mx = in.getAbsoluteMouseX();
            float my = in.getAbsoluteMouseY();

            if(in.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) == false &&
                    state == ButtonState.PRESS &&
                    mx > x && mx < x + width &&
                    my > y && my < y + height)
                alertListeners();

            if(mx > x && mx < x + width &&
                    my > y && my < y + height)
            {
                if(in.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
                    state = ButtonState.PRESS;
                } else {
                    state = ButtonState.HOVER;
                }
            }
            else
                state = ButtonState.NONE;
        }
    }

    @Override
    public void reset() {
    }
}
