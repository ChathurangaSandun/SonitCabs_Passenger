package com.example.chathuranga_pamba.sonitcabs_passenger;

/**
 * Created by Chathuranga-Pamba on 15/12/31.
 */
import java.util.Calendar;
import android.annotation.SuppressLint;
import android. app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

@SuppressLint("ValidFragment")
public class TimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    EditText txtdate;
    public TimeDialog(View view){
        txtdate=(EditText)view;
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minutes = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),this,hour,minutes,true);


    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //show to the selected date in the text box
        String date=day+"-"+(month+1)+"-"+year;
        txtdate.setText(date);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time =  hourOfDay +":"+minute;
        txtdate.setText(time);
    }
}