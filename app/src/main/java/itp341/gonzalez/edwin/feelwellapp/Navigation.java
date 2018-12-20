package itp341.gonzalez.edwin.feelwellapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import itp341.gonzalez.edwin.feelwellapp.Fitness.FitnessList;

/**
 * Created by edwingon on 11/20/17.
 */

public class Navigation extends AppCompatActivity{

    public static final String FRAG_DESTIN="itp341.gonzalez.edwin.feelwellapp.FRAG_DESTIN";

    //Firebase Variables and Toolbar
    FirebaseAuth firebaseAuth;
    private Toolbar toolbar;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.navigation);

        //intialize authetication object
        firebaseAuth =FirebaseAuth.getInstance();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fm =getSupportFragmentManager();
          Fragment f = fm.findFragmentById(R.id.frame_container);

        if (f==null){
            f = new IntroFragment();

            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.frame_container,f);
            ft.commit();
        }


    }

    //inflating custom menu nav
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nav,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent i = new Intent(getApplicationContext(),ListActivity.class);

        //According to which icon selected then the ListActivity is set accordingly
        switch (item.getItemId()){
            case R.id.thought:

                i.putExtra(FRAG_DESTIN, 0);
                startActivity(i);

                break;

            case R.id.food:

                i.putExtra(FRAG_DESTIN,1);
                startActivity(i);

                break;
            case R.id.fitness:

                i.putExtra(FRAG_DESTIN,2);
                startActivity(i);
                break;
            case R.id.forms:
                i.putExtra(FRAG_DESTIN,3);
                startActivity(i);
                break;

            case R.id.Sign_Out:

                //Sign out the User to not allow access
                i = new Intent(getApplicationContext(),MainActivity.class);
                firebaseAuth.getInstance().signOut();
                startActivity(i);
                break;

        }
        return true;

    }
}
