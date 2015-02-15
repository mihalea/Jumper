package mmimaa.game.System;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.*;

/**
 * User: mmimaa
 * Date: 3/3/13
 * Time: 7:00 PM
 */
public class SaveData implements Serializable{

    public final transient String SAVES_PATH = "saves";
    public final transient String ICONS_PATH = "icons";
    public final transient String SCREENSHOTS_PATH = "screenshots";
    private transient Image icon;
    private transient String rootPath;

    private int[][] highScore;
    private Timer timePlayed;
    private String name;


    public SaveData(){
        timePlayed = new Timer();

        highScore = new int[2][3];

        name = "dummy";

        setDefaultDirectories();
    }

    public void newSave(String name){
        this.name = name;
        timePlayed.reset();

        for (int i=0 ; i<2 ; i++)
            for (int j=0 ; j<3 ; j++)
                highScore[i][j] = 0;

        save();
    }

    public void assign(SaveData sd){
        highScore = sd.highScore;
        timePlayed = sd.timePlayed;
        name = sd.name;
    }

    public void load(String saveName){
        File f = new File(rootPath + File.separator + SAVES_PATH + File.separator + saveName + ".sav");



        if(f == null || f.isFile()==false){
            return;
        }

        try {
            FileInputStream fileIn = new FileInputStream(f.getAbsolutePath());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.assign((SaveData) in.readObject());
            in.close();
            fileIn.close();

            File iconPath = new File(rootPath + File.separator + ICONS_PATH + File.separator + name + ".png");
            if(iconPath.isFile())
                icon = new Image(iconPath.getAbsolutePath());
            else
                icon = new Image("res/gui/defaultIcon.png");

        } catch(IOException i) {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c) {
            c.printStackTrace();
            return;
        } catch (SlickException e) {
            e.printStackTrace();
            return;
        }
    }

    public void save(){
        File saveFile = new File(rootPath + File.separator + SAVES_PATH + File.separator + name + ".sav");
        if(saveFile.isFile() == false){
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileOutputStream fileOut = new FileOutputStream(saveFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getHighscore(int mode, int difficulty) {
        return highScore[mode][difficulty];
    }

    public void newHighscore(int score, int mode, int difficulty) {
        highScore[mode][difficulty] = score;
    }

    private void setDefaultDirectories()
    {
        File root;

        String OS = System.getProperty("os.name").toUpperCase();

        if (OS.contains("WIN")){
            root = new File(System.getProperty("user.home") + File.separator + "Application Data"
                             + File.separator + ".jumper");
        }
        else if (OS.contains("MAC"))
            root = new File(System.getProperty("user.home") + File.separator + "Library" + File.separator
                             + "Application Support" + File.separator + ".jumper");
        else if (OS.contains("NUX"))
            root = new File(System.getProperty("user.home") + File.separator + ".jumper");
        else
            root = new File(System.getProperty("user.home") + File.separator + ".jumper");

        rootPath = root.getAbsolutePath();

        if(root.isDirectory() == false){
            root.mkdir();
        }

        File saveFolder = new File(root.getAbsolutePath() + File.separator + SAVES_PATH);
        if(saveFolder.isDirectory() == false)
            saveFolder.mkdir();

        File iconFolder = new File(root.getAbsolutePath() + File.separator + ICONS_PATH);
        if(iconFolder.isDirectory() == false)
            iconFolder.mkdir();

        File ssFolder = new File(root.getAbsolutePath() + File.separator + SCREENSHOTS_PATH);
        if(ssFolder.isDirectory() == false)
            ssFolder.mkdir();

    }

    public String getRootPath() {
        return rootPath;
    }

    public String getName() {
        return name;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }
}
