package itp341.gonzalez.edwin.feelwellapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by edwingon on 11/25/17.
 */

public class FormsFragment extends Fragment {

    ListView resources;

    public FormsFragment(){
        //default constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.forms,container,false);

        //TODO Set up listener for listView

        return v;
    }

}
