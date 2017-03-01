package com.example.abiraami.rideshare;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class DisplayAllRide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_ride);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.uxMyToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView=(ListView)findViewById(R.id.uxLVDisplayRides);
        String dbTableName="RideDetails";
        String createQuery=null;
        DbHelper dbObject=DbHelper.getInstance(this);
        dbObject.onDbInitialize(dbTableName,createQuery);
        RideDetails[] details = dbObject.getData();

        ListViewAdapter adapter=new ListViewAdapter(this, details,getApplicationContext());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                int pos=position+1;
                Toast.makeText(getApplicationContext(), Integer.toString(pos)+" Clicked", Toast.LENGTH_SHORT).show();
            }

        });

    }
    //used along with custom tool bar-menu
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
            case R.id.uxFindRideMenu:
                //Toast.makeText(this, "find rides", Toast.LENGTH_LONG).show();
                Intent searchIntent = new Intent(this, SearchRides.class);
                startActivity(searchIntent);
                return true;
            case R.id.uxPostRideMenu:
                //Toast.makeText(this, "All rides", Toast.LENGTH_LONG).show();
                Intent postIntent = new Intent(this, PostRide.class);
                startActivity(postIntent);
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


