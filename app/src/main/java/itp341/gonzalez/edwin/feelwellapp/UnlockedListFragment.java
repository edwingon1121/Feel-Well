package itp341.gonzalez.edwin.feelwellapp;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import itp341.gonzalez.edwin.feelwellapp.Model.Achievement;

/**
 * Created by edwingon on 12/4/17.
 */

public class UnlockedListFragment extends Fragment{

    FirebaseUser user;
    DatabaseReference dref;

    ImageView image;
    TextView descript;
    ListView list;

    FirebaseListAdapter madapter;


    public UnlockedListFragment(){
        //default constructor

    }

    @Override
    public void onCreate( Bundle savedInstanceState) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();

        dref= database.getReference().child("Users").child(user.getUid()).child("achievements");


        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.achievements_fragment,container,false);
        list = (ListView) v.findViewById(R.id.unlocked_list);
        //instaniate the adapter

        madapter= new unlockAdapter(getActivity(),Achievement.class,R.layout.custom_achievements,dref);
        list.setAdapter(madapter);

        return v;
    }

    private class unlockAdapter extends FirebaseListAdapter <Achievement>{

        //default constructor
        public unlockAdapter (Activity activity, Class<Achievement> aClass, int modelLayout, DatabaseReference ref){
            super(activity,aClass,modelLayout,ref);
        }

        @Override
        protected void populateView(View v, Achievement model, int position) {

//            Reference the UI elements to hold data from thought
            descript = (TextView) v.findViewById(R.id.require_achieve);
            image = (ImageView) v.findViewById(R.id.image_achieve);

            //Set UI elements with only if unlocked
            if (!model.getLocked()) {
                descript.setText(model.getRequire());
                image.setImageResource(R.drawable.lock_icon);
            }


        }


    }

}
