package itp341.gonzalez.edwin.feelwellapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by edwingon on 11/23/17.
 */

public class AchievementsFragment extends Fragment  {
    public static int ARG_KEY = 0;
    public static int ARG_LOCKED =1;
    public static String PAGE_ARG_CODE = "PAGE_ARG_CODE";

    public AchievementsFragment(){

        //default empty constructor

    }

    //Set fragment up according to whether locked or unlocked achievements
    public static Fragment newInstance (int code){
        Fragment f =new AchievementsFragment();

        Bundle args = new Bundle();
        args.putInt(PAGE_ARG_CODE, code);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int code =getArguments().getInt(PAGE_ARG_CODE);

        View v;

        if (code== ARG_KEY) {
           v = inflater.inflate(R.layout.achievements_fragment, container, false);

        }
        else {
            v= inflater.inflate(R.layout.locked_fragment,container,false);
        }

        return v;


    }
}
