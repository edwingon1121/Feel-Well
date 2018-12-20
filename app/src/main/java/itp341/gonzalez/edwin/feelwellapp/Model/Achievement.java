package itp341.gonzalez.edwin.feelwellapp.Model;

/**
 * Created by edwingon on 12/4/17.
 */

public class Achievement {

    private String require;
    private Boolean locked;
    private int progress;
    private int goal;

    public Achievement(){

    }

    public Achievement(String require, Boolean locked, int progress, int goal) {
        this.require = require;
        this.locked = locked;
        this.progress = progress;
        this.goal = goal;
    }

    public String getRequire() {
        return require;
    }

    public void setRequire(String require) {
        this.require = require;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }
}
