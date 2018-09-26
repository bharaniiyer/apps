package happy.life.mantras;

/**
 * Created by ankit on 27/10/17.
 */

public class Fest {

    public String title;
    public String start;
    public String end;


    public Fest() {

    }

    public Fest(String title, String start, String end) {
        this.title = title;
        this.start = start;
        this.end = end;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
