package com.example.chathuranga_pamba.sonitcabs_passenger;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.DialogFragment.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationFragment extends Fragment implements View.OnClickListener ,View.OnFocusChangeListener{

    View view;

    EditText etDate;
    EditText etTime;
    Button b;


    public ReservationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_reservation,container, false);

         b = (Button) view.findViewById(R.id.btBook);
        etDate = (EditText) view.findViewById(R.id.etDate);
        etDate.setOnFocusChangeListener(this);

        etTime = (EditText) view.findViewById(R.id.etTime);
        etTime.setOnFocusChangeListener(this);

        b.setOnClickListener(this);









        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btBook:
                System.out.println("11111111111111111111111111111111111111");
                break;
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.etDate:
                if (hasFocus) {
                    DateDialog dialog = new DateDialog(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "Reservation Date");

                }
                break;
            case R.id.etTime:
                if (hasFocus) {
                    TimeDialog dialog = new TimeDialog(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "Reservation Time");

                }
                break;



        }
    }
}
