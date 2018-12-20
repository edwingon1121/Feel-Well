package itp341.gonzalez.edwin.feelwellapp.Model;

/**
 * Created by edwingon on 11/29/17.
 */

public class Food {
    String name;
    String calories;


    public Food(){

    }

    public Food(String name, String calories) {
        this.name = name;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
