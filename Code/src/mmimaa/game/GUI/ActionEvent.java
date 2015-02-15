package mmimaa.game.GUI;

/**
 * User: mmimaa
 * Date: 3/1/13
 * Time: 7:20 PM
 */
public class ActionEvent {
    private String event;
    private Object object;

    public ActionEvent(String e){
        event = e;
    }

    public ActionEvent(String e, Object o){
        event = e;
        this.object = o;
    }

    public String getEvent(){
        return event;
    }

    public Object getObject(){
        if(object != null)
            return object;

        return null;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
