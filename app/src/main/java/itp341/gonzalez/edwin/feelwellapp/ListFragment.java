package itp341.gonzalez.edwin.feelwellapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by edwingon on 11/25/17.
 */

public class ListFragment extends Fragment {
    public static int ARG_THOUGHT = 0;
    public static int ARG_FOOD =1;
    public static int ARG_FITNESS=2;
    public static String FRAG_ARG_CODE = "FRAG_ARG_CODE";

    public ListFragment () {
        //default constructor
    }

    //In case of a specfic new Instance to set the appropriate fragment
    public static Fragment newInstance (int code){
        Fragment f =new AchievementsFragment();

        Bundle args = new Bundle();
        args.putInt(FRAG_ARG_CODE, code);
        f.setArguments(args);

        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //retrieve the code
        int code =  getArguments().getInt(FRAG_ARG_CODE);

        View v;

        //Inflate view according to which icon was pressed in the Navigation activity
        if (code == ARG_THOUGHT){
            v= inflater.inflate(R.layout.thought_list,container,false);
        }

        else if(code==ARG_FOOD){
            v = inflater.inflate(R.layout.food_list,container,false);
        }

        else if(code ==ARG_FITNESS){
            v = inflater.inflate(R.layout.fitness_list,container,false);

        }

        else  {
            v =inflater.inflate(R.layout.forms,container,false);
        }


        return v;

    }
}
