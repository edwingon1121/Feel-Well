package itp341.gonzalez.edwin.feelwellapp.Model;

/**
 * Created by edwingon on 11/29/17.
 */

public class Thought {

    String title;
    String content;
    String timeStamp;

    public Thought(){

    }

    public Thought(String title, String content, String timeStamp) {
        this.title = title;
        this.content = content;
        this.timeStamp = timeStamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    //TODO Add thoughts and remove thoughts

}
