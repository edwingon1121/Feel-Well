package itp341.gonzalez.edwin.feelwellapp.Food;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import itp341.gonzalez.edwin.feelwellapp.ListActivity;
import itp341.gonzalez.edwin.feelwellapp.Model.Food;
import itp341.gonzalez.edwin.feelwellapp.Navigation;
import itp341.gonzalez.edwin.feelwellapp.R;

/**
 * Created by edwingon on 11/24/17.
 */

public class FoodAdd extends AppCompatActivity {

    //public static final String RESULT_CAL="itp341.gonzalez.edwin.feelwellapp.RESULT_CAL";

    Toolbar toolbar;
    Button add;

    EditText food;
    EditText calories;

    String nameFood;
    String numCal;

    //Firebase Variables
    DatabaseReference databaseReference;
    DatabaseReference calReference;
    FirebaseUser user;

    String sCurrentCal;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_add);

        Intent i =getIntent();
        sCurrentCal= i.getStringExtra(FoodListFragment.CURRENT_CAL);

        toolbar = (Toolbar) findViewById(R.id.toolbar_back_food);
        setSupportActionBar(toolbar);

        //setting the properties of the actionBar
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setIcon(R.drawable.food_icon_back);
            getSupportActionBar().setTitle("   " + "Food");
        }

        //reference to UI components
        add = (Button) findViewById(R.id.add_button);
        food = (EditText) findViewById(R.id.edit_title_food);
        calories = (EditText) findViewById(R.id.edit_cal);

        //Reference to currentUser and foods nodes
        user= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(user.getUid()).child("foods");
        calReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid())
                .child("totalCal");

        food.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                nameFood=food.getText().toString();

                if (!nameFood.isEmpty()){
                    nameFood=food.getText().toString();
                }

                return false;
            }
        });

        calories.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                numCal=calories.getText().toString();

                if (!numCal.isEmpty()){
                    numCal=calories.getText().toString();
                }
                return false;
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Set the EditText content to strings
                nameFood =food.getText().toString();
                numCal =calories.getText().toString();

                //Create and instance of food
                Food f= setData(nameFood,numCal,sCurrentCal);

                //Setting the string to a double and adding to food object
               Double c = Double.valueOf(numCal);
                databaseReference.push().setValue(f);

                Intent i =new Intent(getApplicationContext(),FoodAdd.class);
                        //i.putExtra(RESULT_CAL,c);

                setResult(RESULT_OK);

                finish();

            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return true;
    }

    public Food setData(String name, String calorie,String currentCal){

        Food f = new Food();
        //Set the Data
        f.setName(name);
        f.setCalories(calorie);

        food.setText("");
        calories.setText("");

        //Get the Calories convert to Double add to current value

        Double dCurrentCal= Double.valueOf(sCurrentCal);

        Double foodCal =Double.valueOf(calorie);

        dCurrentCal+=foodCal;

        sCurrentCal= Double.toString(dCurrentCal);

        //Push to Firebase
        calReference.setValue(sCurrentCal);

        return f;

    }

}
