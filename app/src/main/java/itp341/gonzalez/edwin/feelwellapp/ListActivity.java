package itp341.gonzalez.edwin.feelwellapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import itp341.gonzalez.edwin.feelwellapp.Fitness.FitnessList;
import itp341.gonzalez.edwin.feelwellapp.Food.FoodListFragment;
import itp341.gonzalez.edwin.feelwellapp.Thoughts.ThoughtListFragment;

/**
 * Created by edwingon on 11/25/17.
 */

public class ListActivity extends AppCompatActivity{



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activty);

        Intent i = getIntent();

      int destination=i.getIntExtra(Navigation.FRAG_DESTIN, -1);

        FragmentManager fm =getSupportFragmentManager();
        Fragment f =fm.findFragmentById(R.id.frame_container_list);

        //Determine which list is to be created
        if (destination==0){

            f= new ThoughtListFragment();

            FragmentTransaction ft =fm.beginTransaction();
            ft.add(R.id.frame_container_list,f);
            ft.commit();
        }

        else if (destination==1){

            f =new FoodListFragment();

            FragmentTransaction ft =fm.beginTransaction();
            ft.replace(R.id.frame_container_list,f);
            ft.commit();


        }

        else if (destination==2){

            f =new FitnessList();

            FragmentTransaction ft =fm.beginTransaction();
            ft.replace(R.id.frame_container_list,f);
            ft.commit();


        }

        else if (destination==3){
            f = new FormsFragment();

            FragmentTransaction ft =fm.beginTransaction();
            ft.replace(R.id.frame_container_list,f);
            ft.commit();

        }

        else{
            f =new AchievementsFragment();

            FragmentTransaction ft =fm.beginTransaction();
            ft.add(R.id.frame_container_list,f);
            ft.commit();
        }





    }

}
