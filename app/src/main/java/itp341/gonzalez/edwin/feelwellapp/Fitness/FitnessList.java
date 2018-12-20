package itp341.gonzalez.edwin.feelwellapp.Fitness;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import itp341.gonzalez.edwin.feelwellapp.Model.PhysicalActivity;
import itp341.gonzalez.edwin.feelwellapp.R;

/**
 * Created by edwingon on 11/25/17.
 */

public class FitnessList extends Fragment{

    public FitnessList(){
        //Default Constructor
    }

    ImageButton add;
    ListView list;

    DatabaseReference dref;
    FirebaseUser user;

    FirebaseListAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user= FirebaseAuth.getInstance().getCurrentUser();
        dref= FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("fitness");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflte the view and reference UI components
        View v =inflater.inflate(R.layout.fitness_list,container,false);

        list =(ListView)v.findViewById(R.id.list_fitness);
        add =(ImageButton) v.findViewById(R.id.add_fitness_activity);

        mAdapter= new FitnessAdapter(getActivity(),PhysicalActivity.class,R.layout.custom_fitness_row,dref);
        list.setAdapter(mAdapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),FitnessAdd.class);
                startActivity(i);
            }
        });

        return v;
    }

    private class FitnessAdapter extends FirebaseListAdapter<PhysicalActivity>{

        //default constructor
        public FitnessAdapter (Activity activity, Class<PhysicalActivity> paClass, int modelLayout, DatabaseReference ref){
            super(activity,paClass,modelLayout,ref);
        }

        @Override
        protected void populateView(View v, PhysicalActivity model, int position) {

//            Reference the UI elements to hold data from physcial activity
            TextView aName = (TextView) v.findViewById(R.id.activity_name);
            TextView aDuration = (TextView) v.findViewById(R.id.duration_activty);


            //Set UI elements with Physical Activity  data
            aName.setText(model.getName());
            aDuration.setText(model.getTime());

        }


    }


}

