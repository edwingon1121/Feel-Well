package itp341.gonzalez.edwin.feelwellapp.Model;

import java.util.ArrayList;

/**
 * Created by edwingon on 11/20/17.
 */

public class User {
    String name;
    ArrayList <Thought> thoughts;
    ArrayList <Food> foods;
    ArrayList <PhysicalActivity> activities;
    ArrayList <Achievement> achievements;
    String totalCal;
    String city;

    public User(){
        //Default Constructor
    }

    public User(String mName, ArrayList<Thought> mThoughts, ArrayList<Food> mFoods,
                ArrayList<PhysicalActivity> mActivity, String totalCal, String city,
                ArrayList<Achievement> achievements) {
        this.name = mName;
        this.thoughts = mThoughts;
        this.foods = mFoods;
        this.activities = mActivity;
        this.totalCal = totalCal;
        this.city =city;
        this.achievements=achievements;
    }


    public ArrayList<Thought> getThoughts() {
        return thoughts;
    }

    public void setThoughts(ArrayList<Thought> thoughts) {
        this.thoughts = thoughts;
    }


    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public ArrayList<PhysicalActivity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<PhysicalActivity> activities) {
        this.activities = activities;
    }

    public String getTotalCal() {
        return totalCal;
    }

    public void setTotalCal(String totalCal) {
        this.totalCal = totalCal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ArrayList<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(ArrayList<Achievement> achievements) {
        this.achievements = achievements;
    }
}
