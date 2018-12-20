package itp341.gonzalez.edwin.feelwellapp.Fitness;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import itp341.gonzalez.edwin.feelwellapp.Model.PhysicalActivity;
import itp341.gonzalez.edwin.feelwellapp.R;

/**
 * Created by edwingon on 11/25/17.
 */

public class FitnessAdd extends AppCompatActivity {

    //Ui components to be referenced
    Toolbar toolbar;
    Button add;
    EditText duration;
    EditText actEdit;

    String activityName;
    String length;

    //Firebase variables
    DatabaseReference dRef;
    FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fitness_add);


        //Refernce to the user and to the database to push physical activity object
        user=FirebaseAuth.getInstance().getCurrentUser();
        dRef= FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid())
                .child("fitness");

        //Reference the toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_back_fitness);
        setSupportActionBar(toolbar);

        add = (Button) findViewById(R.id.add_activity);

        duration = (EditText) findViewById(R.id.duration_edit);
        actEdit= (EditText) findViewById(R.id.edit_title_fitness);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setIcon(R.drawable.weight_icon_back);
            getSupportActionBar().setTitle("   " + "Fitness");
        }

        actEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                activityName=actEdit.getText().toString();

                if (!activityName.isEmpty()){
                    activityName=actEdit.getText().toString();

                }

                return false;
            }
        });

        duration.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                length=duration.getText().toString();

                if (!length.isEmpty()){
                    length=duration.getText().toString();
                }
                return false;
            }
        });


        //Add physcial activity to Firebase
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Getting the value of the strings from EditText
                    activityName = actEdit.getText().toString();
                    length = duration.getText().toString();
                    PhysicalActivity p = new PhysicalActivity();
                    p = setData(activityName, length);

                    //Send information to firebase
                    dRef.push().setValue(p);

                    setResult(RESULT_OK);
                    finish();





            }
        });

    }

    //When the back button is selected to go back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return true;
    }

    //Set Data to a new Physcial Activity and to later be added to the firebase
    public PhysicalActivity setData(String type, String length){

        PhysicalActivity pa = new PhysicalActivity();
        //Set the Data
        pa.setName(activityName);
        pa.setTime(length);

        actEdit.setText("");
        duration.setText("");

        return pa;

    }
}
