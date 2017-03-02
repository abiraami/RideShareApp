package com.example.abiraami.rideshare;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NavigationPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
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
        setContentView(R.layout.activity_navigation_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getNavigationContent();
    }

    private void getNavigationContent() {
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
                + "(ID integer primary key autoincrement, StartPoint text,EndPoint text, NoSpot integer,TravelDate integer); ";*/
        dbObject=DbHelper.getInstance(this);
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
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_post_ride) {
            // Handle the camera action
        } else if (id == R.id.nav_list_all_rides) {
            Intent displayIntent = new Intent(this, DisplayAllRide.class);
            startActivity(displayIntent);

        } else if (id == R.id.nav_search_rides) {
            Intent searchIntent = new Intent(this, SearchRides.class);
            startActivity(searchIntent);

        }  else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_logout) {
            Intent logoutIntent = new Intent(this, MainActivity.class);
            startActivity(logoutIntent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
