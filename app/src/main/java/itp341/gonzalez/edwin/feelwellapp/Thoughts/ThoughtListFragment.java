package itp341.gonzalez.edwin.feelwellapp.Thoughts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import itp341.gonzalez.edwin.feelwellapp.Model.Thought;
import itp341.gonzalez.edwin.feelwellapp.R;

/**
 * Created by edwingon on 11/23/17.
 */

public class ThoughtListFragment extends Fragment {


    DatabaseReference dref;
    FirebaseUser user;

    TextView thoughtCount;
    ImageButton addButton;
    ListView thoughtList;

    FirebaseListAdapter madapter;

    public ThoughtListFragment() {
        //default constructor
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();

        //reference to the thoughts nodes
        dref= database.getReference().child("Users").child(user.getUid()).child("thoughts");


        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.thought_list,container, false);

        thoughtList = (ListView) v.findViewById(R.id.thoughts_list);

        addButton = (ImageButton) v.findViewById(R.id.add_thought);

        thoughtCount =(TextView) v.findViewById(R.id.number_of_notes);

        //instaniate the adapter
        madapter= new ThoughtAdapter(getActivity(),Thought.class,R.layout.custom_row_thoughts,dref);
        thoughtList.setAdapter(madapter);

        //Display the number of notes and set the text view tho that number
        int count =madapter.getCount();
        thoughtCount.setText(Integer.toString(count));

        //To enable the user to edit the past thoughts

//        thoughtList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                DatabaseReference drefNode= madapter.getRef(i);
//
//
//            }
//        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getContext(), ThoughtsAdd.class);
                startActivityForResult(i,1);
            }
        });

        return v;
    }

    private class ThoughtAdapter extends FirebaseListAdapter<Thought>{

        //default constructor
        public ThoughtAdapter (Activity activity, Class<Thought> thoughtClass, int modelLayout, DatabaseReference ref){
            super(activity,thoughtClass,modelLayout,ref);
        }

        @Override
        protected void populateView(View v, Thought model, int position) {

//            Reference the UI elements to hold data from thought
               TextView title = (TextView) v.findViewById(R.id.title_thought);
                TextView content = (TextView) v.findViewById(R.id.thought_content);
                TextView time = (TextView) v.findViewById(R.id.time_stamp);

                //Set UI elements with Thought data
                title.setText(model.getTitle());
                content.setText(model.getContent());
                time.setText(model.getTimeStamp());

        }


    }

        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode==1 && resultCode== Activity.RESULT_OK){

            //When the user resumes back to this page then we update the count of notes
            int count =madapter.getCount();
            thoughtCount.setText(Integer.toString(count));

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

