package itp341.gonzalez.edwin.feelwellapp.Food;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import itp341.gonzalez.edwin.feelwellapp.ListActivity;
import itp341.gonzalez.edwin.feelwellapp.Model.Food;
import itp341.gonzalez.edwin.feelwellapp.R;

/**
 * Created by edwingon on 11/24/17.
 */

public class FoodListFragment extends Fragment {

    public static final String CURRENT_CAL="itp341.gonzalez.edwin.feelwellapp.CURRENT_CAL";

    ImageButton add;
    ListView list;
    TextView numCal;

    FirebaseUser user;
    DatabaseReference dref;
    DatabaseReference cRef;
    FirebaseListAdapter mAdapter;

    public FoodListFragment(){
        //default constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get current user and nodes of food from such user
        user= FirebaseAuth.getInstance().getCurrentUser();
        dref= FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid())
                .child("foods");
        cRef= FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid())
                .child("totalCal");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //inflate view and reference UI components
        View v= inflater.inflate(R.layout.food_list,container,false);

        list =(ListView) v.findViewById(R.id.foods_list);

        numCal =(TextView) v.findViewById(R.id.num_calories);

        cRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String label = dataSnapshot.getValue(String.class);
                 numCal.setText(label);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        add = (ImageButton) v.findViewById(R.id.add_food);

        mAdapter = new FoodsAdapter(getActivity(),Food.class,R.layout.custom_row_food,dref);
        list.setAdapter(mAdapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getActivity(), FoodAdd.class);
                String pass= numCal.getText().toString();
                i.putExtra(CURRENT_CAL,pass);
                startActivity(i);
            }
        });

        return v;

    }

    private class FoodsAdapter extends FirebaseListAdapter<Food>{

        @Override
        protected void populateView(View v, Food model, int position) {
            TextView name = v.findViewById(R.id.text_food_name);
            TextView calories= v.findViewById(R.id.amount_calories);

            //get the properties of the food item

            name.setText(model.getName());
            //String textCal =model.getCalories();
            calories.setText(model.getCalories());
        }

        //default constuctor
        public FoodsAdapter (Activity activity, Class<Food> foodClass, int modelLayout, DatabaseReference ref){
            super(activity,foodClass,modelLayout,ref);
        }

    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode==1 && resultCode==Activity.RESULT_OK){
//
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}
