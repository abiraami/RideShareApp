package com.example.abiraami.rideshare;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PostRide extends AppCompatActivity {
    private DatePicker datePicker;
    private Calendar calendar;
    private EditText dateSelected;
    private int year, month, day;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText editStartingPt;
    private EditText editEndingPt;
    private EditText editNoSpot;
    private EditText editDate;
    public DbHelper dbObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ride);

        /*Toolbar myToolbar = (Toolbar) findViewById(R.id.uxMyToolbar);
        setSupportActionBar(myToolbar);*/
        //RideDetails detailsCollected=new RideDetails();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        dateSelected = (EditText) findViewById(R.id.uxEditDate);
        dateSelected.setInputType(InputType.TYPE_NULL);
        setDateTimeField();


        Button btnPostRide=(Button)findViewById(R.id.uxBtnPostRide);
        btnPostRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFields();
                insertFields();
                clearFields();
            }
        });





        /*dateSelected.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                *//*calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);

                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                showDate(year, month + 1, day);*//*

            }

        });*/




    }


    private void getFields()
    {
        editStartingPt=(EditText)findViewById(R.id.uxEditStrtPoint);
        editEndingPt=(EditText)findViewById(R.id.uxEditEndPoint);
        editDate=(EditText)findViewById(R.id.uxEditDate);
        editNoSpot=(EditText)findViewById(R.id.uxEditNoSpot);
    }

    private void insertFields()
    {
        //DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        RideDetails details=new RideDetails();
        details.setStartPt(editStartingPt.getText().toString());
        details.setEndPt(editEndingPt.getText().toString());

        try {
            details.setTravelDate(dateFormatter.parse(editDate.getText().toString()));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        details.setNoSpot(Integer.parseInt(editNoSpot.getText().toString()));


        dbOperations(details);

    }

    private long dbOperations(RideDetails details) {
        //boolean isInserted=false;
        /*String dbTableName="RideDetails";
        String createQuery="create table "
                + dbTableName
                + "(ID integer primary key autoincrement, StartPoint text,EndPoint text, NoSpot integer,TravelDate integer); ";
        dbObject=DbHelper.getInstance(this);*/
        //dbObject.onDbInitialize(dbTableName,createQuery);
        //dbObject.deleteSessions();
        long rowId=dbObject.addData(details);
        if(rowId!=-1) {
            Toast.makeText(this, "Ride Posted", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Sorry! Something went wrong. Try again", Toast.LENGTH_LONG).show();
        }


        return rowId;
    }


    private void setDateTimeField() {
        dateSelected.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                fromDatePickerDialog.show();
                                            }
                                        });


                Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateSelected.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }

    private void clearFields()
    {
        editStartingPt.setText(null);
        editEndingPt.setText(null);
        editNoSpot.setText(null);
        editDate.setText(null);
    }




       /* @SuppressWarnings("deprecation")
        public void setDate (View view){
            showDialog(999);
            Toast.makeText(getApplicationContext(), "ca",Toast.LENGTH_SHORT).show();
        }


    @Override
        protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }
        private DatePickerDialog.OnDateSetListener myDateListener = new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0,
                                          int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        // arg1 = year
                        // arg2 = month
                        // arg3 = day
                        showDate(arg1, arg2+1, arg3);
                    }
                };




    private void showDate(int year, int month, int day) {
        dateSelected.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }*/

    public boolean onCreateOptionsMenu(Menu menu) {
        //Toast.makeText(this, "onCreateOptionsMenu", Toast.LENGTH_LONG).show();

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.uxFindRideMenu:
                //Toast.makeText(this, "find rides", Toast.LENGTH_LONG).show();
                Intent searchIntent = new Intent(this, SearchRides.class);
                startActivity(searchIntent);
                return true;
            case R.id.uxAllRideMenu:
                //Toast.makeText(this, "All rides", Toast.LENGTH_LONG).show();
                Intent displayIntent = new Intent(this, DisplayAllRide.class);
                startActivity(displayIntent);
                return true;
            case R.id.uxLogOutMenu:
                Intent logoutIntent = new Intent(this, MainActivity.class);
                startActivity(logoutIntent);
                //Toast.makeText(this, "Logout", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

