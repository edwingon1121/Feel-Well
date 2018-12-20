package itp341.gonzalez.edwin.feelwellapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by edwingon on 11/23/17.
 */

public class splash extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Launch to the MainActivity
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
