package itp341.gonzalez.edwin.feelwellapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class LockedListFragment extends Fragment{


   private FirebaseUser user;
   private DatabaseReference dref;


    private ListView mlist;
    FirebaseListAdapter mAdapter;


    public LockedListFragment(){
        //default constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();

        //Reference to the database
        dref= database.getReference().child("Users").child(user.getUid()).child("achievements");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.locked_fragment,container,false);

        mlist = (ListView) v.findViewById(R.id.locked_list);
        //instaniate the adapter

        mAdapter= new LockAdapter(getActivity(), Achievement.class, R.layout.custom_achievements, dref);
        mlist.setAdapter(mAdapter);

        return v;
    }

    private class LockAdapter extends FirebaseListAdapter<Achievement>{

        @Override
        protected void populateView(View v, Achievement model, int position) {

//            Reference the UI elements to hold data from thought
         TextView  descript = (TextView) v.findViewById(R.id.require_achieve);
         ImageView  image = (ImageView) v.findViewById(R.id.image_achieve);

            //Set UI elements with only if unlocked
            image.setImageResource(R.drawable.lock_icon);
            descript.setText(model.getRequire());
        }

        //default constructor
        public LockAdapter (Activity activity, Class<Achievement> modelClass, int modelLayout, DatabaseReference ref){
            super(activity,modelClass,modelLayout,ref);
        }

    }
}
