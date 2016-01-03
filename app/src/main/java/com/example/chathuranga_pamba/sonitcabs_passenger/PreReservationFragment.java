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
public class PreReservationFragment extends Fragment {


    View v;

    public PreReservationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pre_reservation, container, false);


        double distance = getArguments().getDouble("DISTANCE");

        int beginfare = ((int)distance-1)*30 + 40;

        TextView begin = (TextView) v.findViewById(R.id.tvBiginFire);
        begin.setText(String.valueOf(beginfare)+".00");


        int endnfare = (((int)distance)+3-1)*30 + 40;
        TextView end = (TextView) v.findViewById(R.id.tvEndFire);
        end.setText(String.valueOf(endnfare)+".00");







        return v;
    }


}
