package itp341.gonzalez.edwin.feelwellapp.Model;

/**
 * Created by edwingon on 11/29/17.
 */

public class PhysicalActivity {

    String name;
    String intensity;
    String time;

    public PhysicalActivity(){

    }

    public PhysicalActivity(String name, String intensity, String time) {
        this.name = name;
        this.intensity = intensity;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
