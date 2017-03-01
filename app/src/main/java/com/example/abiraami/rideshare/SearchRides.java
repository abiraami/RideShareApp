package com.example.abiraami.rideshare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SearchRides extends AppCompatActivity {
    private String startPoint;
    private String endPoint;
    //private Button btnSearch;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_rides);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.uxMyToolbar);
        setSupportActionBar(myToolbar);
        //to get the up button on the custom tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }
    private void getSearchCondition()
    {
        startPoint =((EditText)findViewById(R.id.uxEditStartPoint)).getText().toString();
        endPoint=((EditText)findViewById(R.id.uxEditEndPointSearch)).getText().toString();

    }

    public void onSearchClick(View v)
    {
        getSearchCondition();
        listView = (ListView) findViewById(R.id.uxLVDisplayRides);
        TextView resultDisplay=(TextView)findViewById(R.id.uxTVResult);
        listView.invalidateViews();
        resultDisplay.setText(null);
        String dbTableName="RideDetails";
        String createQuery=null;
        DbHelper dbObject=DbHelper.getInstance(this);
        dbObject.onDbInitialize(dbTableName,createQuery);
        RideDetails[] searchResult=dbObject.searchData(startPoint,endPoint);

        if(searchResult.length==0)
        {
            listView.invalidateViews();
            resultDisplay.setText("No items match your search condition");
        }
        else
        {
            ListViewAdapter adapter = new ListViewAdapter(this, searchResult,v.getContext());
            listView.setAdapter(adapter);
        }

    }

    //to load the menu in the app bar. commented this because nav bar is used for navigation
    /*public boolean onCreateOptionsMenu(Menu menu) {
        //Toast.makeText(this, "onCreateOptionsMenu", Toast.LENGTH_LONG).show();

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.uxPostRideMenu:
                //Toast.makeText(this, "find rides", Toast.LENGTH_LONG).show();
                Intent postIntent = new Intent(this, PostRide.class);
                startActivity(postIntent);
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
    }*/

}




