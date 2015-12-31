package com.example.chathuranga_pamba.sonitcabs_passenger;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment implements View.OnClickListener{


    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_blank, container,
                false);

        Button b = (Button) v.findViewById(R.id.bttwo);
        b.setOnClickListener(this);



        return inflater.inflate(R.layout.fragment_blank, container, false);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.bttwo:
                System.out.println("11111111dfsfsf111111111111111111111111111111");
                break;
        }
    }
}
