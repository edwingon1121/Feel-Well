package itp341.gonzalez.edwin.feelwellapp;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
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
import com.google.firebase.database.Transaction;

public class MainActivity extends AppCompatActivity {

    Button login;
    Button signUp;
    EditText editEmail;
    EditText editPassword;
    ProgressBar progress;
    private int progress_status=0;

    private Handler handler =new Handler();

    String email;
    String passWord;

    private static final String TAG="SignIn";
    //private static final String USER_NAME="itp.341.gonzalez.edwin.feelwellapp.USER_NAME";

    //Firebase Listen for auth state
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        //Firebase variable
        mAuth = FirebaseAuth.getInstance();

        login = (Button) findViewById(R.id.login_button);
        signUp = (Button) findViewById(R.id.signup_button);
        editEmail = (EditText) findViewById(R.id.edit_email);
        editPassword = (EditText) findViewById(R.id.edit_password);
        progress = (ProgressBar) findViewById(R.id.progress_bar);

        //ProgressBar is hidden until the user hits Login
        progress.setVisibility(View.INVISIBLE);

        //Tracks to see if the user signs in or out
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");

                }

            }
        };

        //Get the email from EditText
        editEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                email = editEmail.getText().toString();

                if (!email.isEmpty()){
                    // Check if email inputed
                    email = editEmail.getText().toString();

                    if (!email.contains("@")){
                        Toast.makeText(MainActivity.this,R.string.miss_email,Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    //Display toast telling user is missing email
                    Toast.makeText(MainActivity.this,R.string.miss_email,Toast.LENGTH_SHORT).show();
                }


                return false;
            }
        });

        editPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                passWord=editPassword.getText().toString();

                if (!passWord.isEmpty()){
                    passWord= editPassword.getText().toString();
                }
                else{
                    passWord="";
                    //Display toast telling user is missing email
                    Toast.makeText(MainActivity.this,R.string.miss_pass,Toast.LENGTH_SHORT).show();
                }

                return false;

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //calls method signIn

                mAuth=FirebaseAuth.getInstance();
                progress.setVisibility(View.VISIBLE);

                //Manage the length of the progressbar
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (progress_status<100){
                            progress_status+=1;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progress.setProgress(progress_status);
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

                //Call custom SignIn method
                signIn(email,passWord);



            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Launch the SignUp activity
                Intent i = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(i);
            }
        });

    }

    //Method that checks email and password to see if valid and if not displays message
    public void signIn(String email, String passWord){


        //Check if the user exist and if so then sign them in
        mAuth.signInWithEmailAndPassword(email, passWord)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            progress.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        else {
                            //Display toast to tell user was successful & launch intent
                            Toast.makeText(MainActivity.this, R.string.login_success,
                                    Toast.LENGTH_SHORT).show();


                            //Move onto the navigation activity
                            Intent i = new Intent(getApplicationContext(), Navigation.class);
                            startActivity(i);

                        }
                    }
                });
    }

    // On the onStart method we add a listener for the status of the user
    @Override
    protected void onStart() {
        super.onStart();
        //Check if signed in
        mAuth.addAuthStateListener(mAuthListener);
    }


    //OnStop removing the listner to the user sign status
    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
