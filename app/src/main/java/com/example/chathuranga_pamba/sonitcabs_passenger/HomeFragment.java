package com.example.chathuranga_pamba.sonitcabs_passenger;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {




    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.activity_home,container, false);

        TextView t = (TextView) view.findViewById(R.id.tvSession);





       // t.setText(new Session(getContext()).getusename());





        return inflater.inflate(R.layout.fragment_home, container, false);
    }


}
