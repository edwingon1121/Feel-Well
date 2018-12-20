package itp341.gonzalez.edwin.feelwellapp.Thoughts;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

import itp341.gonzalez.edwin.feelwellapp.Model.Thought;
import itp341.gonzalez.edwin.feelwellapp.R;

/**
 * Created by edwingon on 11/23/17.
 */

public class ThoughtsAdd extends AppCompatActivity {

    DatabaseReference dref;
    FirebaseUser user;

    Toolbar toolbar;
    EditText titleEdit;
    EditText thoughtContent;
    Button add;

    String title;
    String content;
    String timestamp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thoughts_add);

        toolbar = (Toolbar) findViewById(R.id.toolbar_back_thought);
        setSupportActionBar(toolbar);

        // in case the actionBar is not null we set the icon
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.drawable.thought_bubble_back);
            getSupportActionBar().setTitle("   "+ "Thought");

        }

        titleEdit =(EditText) findViewById(R.id.edit_Title);
        thoughtContent= (EditText) findViewById(R.id.edit_thought);
        add = (Button) findViewById(R.id.add_button);

        titleEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                title =titleEdit.getText().toString();

                if (!title.isEmpty()){
                    title=titleEdit.getText().toString();
                }

                return false;
            }
        });

        thoughtContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                content=thoughtContent.getText().toString();

                if (!content.isEmpty()){
                    content=thoughtContent.getText().toString();
                }

                return false;
            }
        });

        timestamp= new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Set Date for when the thought is created

                title=titleEdit.getText().toString();
                content=thoughtContent.getText().toString();

                if (title==null){
                    title="New Thought";
                }
                if (content==null){
                    content="";
                }
                Thought t = setData(title,content,timestamp);

                user= FirebaseAuth.getInstance().getCurrentUser();
                dref= FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("thoughts");

                //Add a new thought to Firebase
                dref.push().setValue(t);


                setResult(Activity.RESULT_OK);
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

    public Thought setData(String title, String content, String timestamp){

        Thought t = new Thought();
        //Set the Data
        t.setTitle(title);
        t.setContent(content);
        t.setTimeStamp(timestamp);

        titleEdit.setText("");
        thoughtContent.setText("");

        return t;

    }

}

