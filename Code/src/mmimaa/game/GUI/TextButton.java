package mmimaa.game.GUI;

import org.newdawn.slick.*;

/**
 * User: mmimaa
 * Date: 3/1/13
 * Time: 6:13 PM
 */
public class TextButton extends Component{
    private ButtonState state;
    private String string;
    private UnicodeFont font;


    private enum ButtonState{
        NONE(0), HOVER(1), PRESS(2);

        public int id;

        ButtonState(int id){
            this.id = id;
        }
    }

    public TextButton(String path, UnicodeFont font, ActionEvent actionEvent, String string, int width, int height){
        super();

        textures = new Image[3];

        this.width = width;
        this.height = height;
        this.string = string;
        this.font = font;
        this.event = actionEvent;

        state = ButtonState.NONE;

        try {
            SpriteSheet spriteSheet = new SpriteSheet(path, width, height, 1);
            for (int i=0 ; i<3 ; i++){
                textures[i] = spriteSheet.getSubImage(i, 0);
            }

        } catch (SlickException e) {
            e.printStackTrace();
        }
    }



        @Override
    public void draw(GameContainer gc, Graphics g){
        textures[state.id].draw(x,y);
        font.drawString(x + width / 2 - font.getWidth(string) / 2, y + height/2 - font.getHeight(string) / 2, string);
    }

    @Override
    public void update(GameContainer gc, int delta){
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
