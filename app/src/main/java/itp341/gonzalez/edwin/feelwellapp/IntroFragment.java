package itp341.gonzalez.edwin.feelwellapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import itp341.gonzalez.edwin.feelwellapp.Model.User;

/**
 * Created by edwingon on 11/24/17.
 */



public class IntroFragment extends Fragment {


    //UI elements that are going to be initalized
    TextView userName;
    Button key;
    Button lock;

    //Firebase References to Authorize section and database refering to
    private FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();;

    //Current user that is authorized and reference to name child node
    FirebaseUser user =firebaseAuth.getCurrentUser();
    DatabaseReference mNameRef= databaseReference.child("Users").child(user.getUid()).child("name");

    public IntroFragment(){
        //default constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //inflate layout from this fragment
        View v = inflater.inflate(R.layout.intro_fragment,container,false);

        key = (Button) v.findViewById(R.id.achievements_button);
        lock = (Button) v.findViewById(R.id.locked_button);
        userName = (TextView) v.findViewById(R.id.enter_name);

        firebaseAuth =FirebaseAuth.getInstance();

        //Called when first loaded and to set the text field to the User's name
        mNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                userName.setText(name +",");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //User clicks either the lock or key icon loads either the locked achievements and complete
        //achievements
        key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment f = AchievementsFragment.newInstance(AchievementsFragment.ARG_KEY);
                FragmentTransaction ft =getFragmentManager().beginTransaction();

                ft.replace(R.id.frame_container,f);
                ft.commit();
            }
        });

        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment f = AchievementsFragment.newInstance(AchievementsFragment.ARG_LOCKED);
                FragmentTransaction ft =getFragmentManager().beginTransaction();

                ft.replace(R.id.frame_container,f);
                ft.commit();

            }
        });

        return v;
    }

}
