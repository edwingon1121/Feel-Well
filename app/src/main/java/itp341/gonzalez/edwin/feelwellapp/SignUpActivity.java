package itp341.gonzalez.edwin.feelwellapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import itp341.gonzalez.edwin.feelwellapp.Model.Achievement;
import itp341.gonzalez.edwin.feelwellapp.Model.Food;
import itp341.gonzalez.edwin.feelwellapp.Model.PhysicalActivity;
import itp341.gonzalez.edwin.feelwellapp.Model.Thought;
import itp341.gonzalez.edwin.feelwellapp.Model.User;

/**
 * Created by edwingon on 11/29/17.
 */

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG="SignUp";

    //Values to input from the EditText fields
    String email;
    String password;
    String name;
    String city;



    //All the UI components that are to be referenced in the layout
    EditText nameEdit;
    EditText emailEdit;
    EditText passwordEdit;
    EditText cityEdit;
    Button confirm;
    ProgressBar progressBar;

    int progress=0;

    //To schedule the thread for when the loading icon appears
    private Handler handler =new Handler();


    //Firebase variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        // Referencing UI component
        nameEdit = (EditText) findViewById(R.id.name_field);
        emailEdit = (EditText) findViewById(R.id.email_field);
        passwordEdit = (EditText) findViewById(R.id.password_field);
        cityEdit = (EditText) findViewById(R.id.city_field);
        confirm = (Button) findViewById(R.id.button_signUp);
        progressBar =(ProgressBar) findViewById(R.id.prog_sign_up);



        //Setting the progressBar to be invisible then visible when the user submits
        progressBar.setVisibility(View.INVISIBLE);

        //Creating thread to update the progress bar
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress<100){
                    progress+=1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progress);
                        }
                    });
                    try {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        mAuth = FirebaseAuth.getInstance();

        //Reference to the current FirebaseDatabase
        databaseReference = FirebaseDatabase.getInstance().getReference();

        nameEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                name = nameEdit.getText().toString();

                if (!name.isEmpty()){
                    // Check if email inputed
                    name = nameEdit.getText().toString();
                }
                else {
                    //Display toast telling user is missing name
                    Toast.makeText(SignUpActivity.this,R.string.miss_name,Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        emailEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                email = emailEdit.getText().toString();

                if (!email.isEmpty()){
                    // Check if email inputed
                    email = emailEdit.getText().toString();
                }
                else {
                    //Display toast telling user is missing email
                    Toast.makeText(SignUpActivity.this,R.string.miss_email,Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        passwordEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                password = passwordEdit.getText().toString();

                if (!password.isEmpty()){
                    // Check if email inputed
                    password = passwordEdit.getText().toString();
                }
                else {
                    //Display toast telling user is missing email or invalid password
                    Toast.makeText(SignUpActivity.this,R.string.miss_pass,Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        cityEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                city = cityEdit.getText().toString();

                if (!city.isEmpty()){
                    // Check if email inputed
                    city = cityEdit.getText().toString();
                }
                else {
                    //Display toast telling user is missing city
                    Toast.makeText(SignUpActivity.this,R.string.miss_city,Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check if information is not replicated by other users

                name =nameEdit.getText().toString();
                createAccount(email,password);
            }
        });


    }

    public void createAccount(String e, String pw){

        //Set the progress bar to visible
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(e, pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, R.string.auth_missing,
                                    Toast.LENGTH_SHORT).show();
                        }

                        else {
                            //Present toast that was successful and then start intent to MainActivity
                            Toast.makeText(SignUpActivity.this, R.string.login_success,
                                    Toast.LENGTH_SHORT).show();

                            //Create thoughts,Food, physical activity, lists
                            //Creating new User
                            //User newUser = new User(name, newThought(), newFood(),
                                    //newActivity(),0.0, city, newAchievements());

                            User newUser= new User();

                            //Set user values from the fields
                            newUser.setName(name);
                            newUser.setCity(city);
                            newUser.setTotalCal("0.0");
                            newUser.setAchievements(newAchievements());

                            FirebaseUser user = mAuth.getCurrentUser();
                            //Adding to the specifc user node
                            databaseReference.child("Users").child(user.getUid()).setValue(newUser);

                            //Going back to Main Activity
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);

                        }


                    }
                });


    }

    //Create Thought arraylist to format lists

    //Used to test if Firebase was storing when first intializing User

//    public ArrayList<Thought> newThought(){
//
//        ArrayList<Thought> thoughts= new ArrayList<Thought>();
//
//        Thought t = new Thought();
//        t.setTitle("New Title");
//        t.setContent("Content");
//        t. setTimeStamp("yyyy.MM.dd");
//
//        thoughts.add(t);
//
//        return thoughts;
//    }
//
//    public ArrayList<Food> newFood(){
//        ArrayList<Food> foods = new ArrayList<Food>();
//
//        Food f= new Food();
//        f.setName("Name Food");
//        f.setCalories(0.0);
//        f.setServingSize("1 serving");
//
//        foods.add(f);
//
//        return foods;
//    }
//
//    public ArrayList<PhysicalActivity> newActivity(){
//        ArrayList<PhysicalActivity> activities = new ArrayList<PhysicalActivity>();
//
//        PhysicalActivity pa= new PhysicalActivity();
//
//        pa.setName("Activity Tile");
//        pa.setIntensity("Intensity");
//        pa.setTime("30 mins");
//
//        activities.add(pa);
//
//        return activities;
//    }

    public ArrayList<Achievement> newAchievements(){

        ArrayList<Achievement> accomplish=new ArrayList<Achievement>();
        Achievement achieve = new Achievement();

        String[] achievements;

        achievements= getResources().getStringArray(R.array.achievements);

        String require;

        for (int i=0; i< achievements.length; i++){
            require=achievements[i];
            achieve.setRequire(require);
            achieve.setLocked(true);
            achieve.setProgress(0);
            achieve.setGoal(5);
            accomplish.add(achieve);

            achieve=new Achievement();
        }

        return accomplish;
    }



}
