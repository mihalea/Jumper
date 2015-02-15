package mmimaa.game.System;

import java.io.Serializable;

/**
 * User: mmimaa
 * Date: 3/3/13
 * Time: 7:53 PM
 */
public class Timer implements Serializable{
    private long days, hours, minutes, seconds, miliseconds;

    public Timer(){
        days = hours = minutes = seconds = miliseconds = 0;
    }

    public void add(long ms){
        miliseconds += ms;

        update();
    }

    private void update(){
        long r = miliseconds / 1000;
        miliseconds %= 1000;

        seconds += r;
        r = seconds / 60;
        seconds %= 60;

        minutes += r;
        r = minutes / 60;
        minutes %= 60;

        hours += r;
        r = hours / 24;
        hours %= 24;

        days += r;
    }

    public void reset(){
        days = hours = minutes = seconds = miliseconds = 0;
    }

    @Override
    public String toString(){
        return days + "d " + hours + "h " + minutes + "m " + seconds + "s " + miliseconds + "ms ";
    }

    public void set(long d, long h, long m, long s, long ms){
        days = d;
        hours = h;
        minutes = m;
        seconds = s;
        miliseconds = ms;
    }

    public long compare(Timer time){
        if(this.days > time.days)
            return -1;
        else if(this.days < time.days)
            return +1;
        else if(this.hours > time.hours)
            return -1;
        else if(this.hours < time.hours)
            return +1;
        else if(this.minutes > time.minutes)
            return -1;
        else if(this.minutes < time.minutes)
            return +1;
        else if(this.seconds > time.seconds)
            return  -1;
        else if(this.seconds < time.seconds)
            return  +1;
        else if(this.miliseconds > time.miliseconds)
            return -1;
        else if(this.miliseconds < time.miliseconds)
            return +1;
        else
            return 0;
    }

    public String toShortString() {
        StringBuilder sb = new StringBuilder();

        if(minutes>=10)
            sb.append(minutes);
        else if(minutes<10 && minutes>0)
            sb.append("0" + minutes);
        else
            sb.append("00");

        sb.append(":");

        if(seconds>=10)
            sb.append(seconds);
        else if(seconds<10 && seconds>0)
            sb.append("0" + seconds);
        else
            sb.append("00");


        return sb.toString();
    }

    public long toMiliseconds(){
        return days * 86400000 + hours * 3600000 + minutes * 60000 + seconds * 1000 + miliseconds;
    }

    public Timer subtract(Timer t){
        Timer result = new Timer();
        
        result.add(this.toMiliseconds() - t.toMiliseconds());

        return result;
    }
}
